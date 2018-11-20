package org.framework.server.exception;

import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import fabos.framework.core.exception.CommonErrorCode;
import fabos.framework.core.rest.dto.ServiceResult;
import fabos.framework.core.util.BeanValidators;

/***
 * @Desc 校验异常处理
 * @author lwb
 *
 */
public class ConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		ServiceResult<Map> result = new ServiceResult<>(CommonErrorCode.CONSTRAINTS_VIOLATION);
		result.setData(BeanValidators.extractPropertyAndMessage(exception));
		return Response.status(Status.OK).entity(result).build();
	}

}
