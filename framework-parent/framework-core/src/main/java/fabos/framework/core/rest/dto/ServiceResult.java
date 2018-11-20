package fabos.framework.core.rest.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import fabos.framework.core.exception.BizServiceException.ErrorCode;

public class ServiceResult<T> implements Serializable {

	private static final long serialVersionUID = -8265499075600054726L;

	private String code = "000";

	private String msg;

	private T data;
	
	public ServiceResult() {

	}
	
	public ServiceResult(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.msg = errorCode.getDesc();
	}

	public String getCode() {
		return code;
	}

	public ServiceResult setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public ServiceResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public ServiceResult setData(T data) {
		this.data = data;
		return this;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
