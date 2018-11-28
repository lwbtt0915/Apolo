package fabos.framework.frameworkauth.common;

import java.io.Serializable;


import fabos.framework.frameworkauth.exception.BizServiceException;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ServiceResult<T> implements Serializable {

	private static final long serialVersionUID = -8265499075600054726L;

	private String code = "000";

	private String msg;

	private T data;
	
	public ServiceResult() {

	}
	
	public ServiceResult(BizServiceException.ErrorCode errorCode) {
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
