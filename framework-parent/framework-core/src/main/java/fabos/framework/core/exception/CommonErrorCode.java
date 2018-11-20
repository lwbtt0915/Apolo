package fabos.framework.core.exception;

import fabos.framework.core.exception.BizServiceException.ErrorCode;

public enum CommonErrorCode implements ErrorCode {

	UNKNOWN_ERROR("COMN-0000"),
	CONSTRAINTS_VIOLATION("COMN-0001"),
	NO_VALID_ENUM_DEFN_FOUND("COMN-0002"),
	PRE_CONDITIONS_NOT_MATCHED("COMN-0003"),
	VERSION_CHECK_FAILED("COMN-0004");

	private final String code;

	CommonErrorCode(final String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return bundle.getString(code);
	}

	@Override
	public String toString() {
		return "[" + getCode() + "] --- " + getDesc();
	}
}
