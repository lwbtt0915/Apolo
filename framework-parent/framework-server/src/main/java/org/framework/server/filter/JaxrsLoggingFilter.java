package org.framework.server.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Providers;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxrsLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Context
	private Providers providers;
	// 换行
	private static final String LINE_BREAK = System.lineSeparator();

	private static final Logger logger = LoggerFactory.getLogger(JaxrsLoggingFilter.class);

	private static final String TRACKID = "TRACKID";

	static class SimpleIdGenerator {

		private static final AtomicLong ID = new AtomicLong();

		public static String nextId() {
			return Long.toString(ID.incrementAndGet());
		}
	}

	/***
	 * @author lwb
	 * @Description 对请求进行拦截
	 * @params requestContext
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		MDC.put(TRACKID, UUID.randomUUID().toString());
		StringBuilder sb = new StringBuilder();
		requestContext.setProperty("ID", SimpleIdGenerator.nextId());

		sb.append("Inbound Message").append(LINE_BREAK);
		sb.append("----------------------------").append(LINE_BREAK);
		sb.append("Address: ").append(requestContext.getUriInfo().getRequestUri()).append(LINE_BREAK);
		sb.append("Http-Method: ").append(requestContext.getMethod()).append(LINE_BREAK);

		List<String> headers = new ArrayList<>();
		for (Entry<String, List<String>> header : requestContext.getHeaders().entrySet()) {
			headers.add(header.getKey() + "=" + header.getValue());
		}

		sb.append("headers: {").append(StringUtils.join(headers, ',')).append("}" + LINE_BREAK);

		if (requestContext.hasEntity()) {
			sb.append("MediaType: ").append(requestContext.getMediaType()).append(LINE_BREAK);
			String payload = IOUtils.toString(requestContext.getEntityStream(), "UTF-8");
			sb.append("payload: ").append(payload).append(LINE_BREAK);
			requestContext.setEntityStream(IOUtils.toInputStream(payload, "UTF-8"));
		}

		sb.append("----------------------------");
		logger.info(sb.toString());
	}

	
	/***
	 * @author lwb
	 * @Desc  对响应进行拦截
	 * @params requestContext  responseContext
	 */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("OutBound message").append(LINE_BREAK);

		sb.append("----------------------------").append(LINE_BREAK);
		sb.append("ID: ").append(requestContext.getProperty("ID")).append(LINE_BREAK);
		sb.append("Response-Code: ").append(responseContext.getStatus()).append("")
				.append(responseContext.getStatusInfo()).append(LINE_BREAK);

		List<String> headers = new ArrayList<String>();
		for (Entry<String, List<String>> header : responseContext.getStringHeaders().entrySet()) {
			headers.add(header.getKey() + "=" + header.getValue());
		}

		sb.append("Headers: {").append(StringUtils.join(headers, ',')).append("}" + LINE_BREAK);

		if (responseContext.hasEntity()) {
			sb.append("MediaType: ").append(responseContext.getMediaType()).append(LINE_BREAK);
			sb.append("PayLoad: ").append(this.payloadResponse(responseContext)).append(LINE_BREAK);
		}

		sb.append("----------------------------");
		logger.info(sb.toString());
		MDC.clear();
	}

	@SuppressWarnings("unchecked")
	private Object payloadResponse(ContainerResponseContext responseContext) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Class<?> entityClass = responseContext.getEntityClass();
		Type entityType = responseContext.getEntityType();
		MediaType mediaType = responseContext.getMediaType();
		Annotation[] annotations = responseContext.getEntityAnnotations();
		MessageBodyWriter<Object> bodyWriter = (MessageBodyWriter<Object>) providers.getMessageBodyWriter(entityClass,
				entityType, annotations, mediaType);
		try {
			bodyWriter.writeTo(responseContext.getEntity(), entityClass, entityType, annotations, mediaType,
					responseContext.getHeaders(), baos);
		} catch (WebApplicationException e) {
			logger.error("JaxrsLoggingFilter  payloadResponse {} throws WebApplicationException" + e);
		} catch (IOException e) {
			logger.error("JaxrsLoggingFilter  payloadResponse {} throws IOException" + e);
		}

		return new String(baos.toByteArray());
	}

}
