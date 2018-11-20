package org.framework.server.exception;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import fabos.framework.core.exception.BizServiceException;
import fabos.framework.core.rest.dto.ServiceResult;

@Provider
public class ServiceException implements ExceptionMapper<BizServiceException> {

	private static final Logger logger = LoggerFactory.getLogger(ServiceException.class);

	@Override
	public Response toResponse(BizServiceException exception) {
		ServiceResult<Map> result = new ServiceResult<>(exception.getErrorCode());
		result.setData(exception.getProperties());

		logger.error("fabos mes server error.\n{}", Throwables.getStackTraceAsString(exception));
		return Response.status(Status.OK).entity(result).build();
	}

}
