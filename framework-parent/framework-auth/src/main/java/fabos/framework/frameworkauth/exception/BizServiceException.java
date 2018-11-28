package fabos.framework.frameworkauth.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * Service层公用的Exception.
 * 
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * 
 * @author Bruno
 */
public class BizServiceException extends RuntimeException {

	private static final long serialVersionUID = 7837708424622066041L;

	private ErrorCode errorCode;

	private Map<String, Object> properties = new TreeMap<>();
	
	public BizServiceException(ErrorCode errorCode) {
		super(errorCode.toString());
		this.errorCode = errorCode;
	}

	public BizServiceException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BizServiceException(Throwable cause, ErrorCode errorCode) {
		super(errorCode.toString(), cause);
		this.errorCode = errorCode;
	}

	public BizServiceException(String message, Throwable cause, ErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public BizServiceException setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) properties.get(key);
	}

	public BizServiceException set(String key, Object value) {
		properties.put(key, value);
		return this;
	}
	
	public static BizServiceException wrap(Throwable exception, ErrorCode errorCode) {
		if (exception instanceof BizServiceException) {
			BizServiceException se = (BizServiceException) exception;
			if (errorCode != null && errorCode != se.getErrorCode()) {
				return new BizServiceException(exception.getMessage(), exception, errorCode);
			}
			return se;
		} else {
			return new BizServiceException(exception.getMessage(), exception, errorCode);
		}
	}

	public static BizServiceException wrap(Throwable exception) {
		return wrap(exception, null);
	}
	
	@Override
	public void printStackTrace() {
		this.printStackTrace(System.err);
	}
	
	@Override
	public void printStackTrace(PrintStream s) {
		synchronized (s) {
			this.printStackTrace(new PrintWriter(s));
		}
	}
	
	@Override
	public void printStackTrace(PrintWriter w) {
		synchronized (w) {
			w.println(this);
			w.println("\t-------------------------------");
//			if (errorCode != null) {
//				w.println("\t" + errorCode + " - " + errorCode.getClass().getSimpleName());
//			}
			for (String key : properties.keySet()) {
				w.println("\t" + key + " = [" + properties.get(key) + "]");
			}
			w.println("\t-------------------------------");
			StackTraceElement[] trace = getStackTrace();
			for (int i = 0; i < trace.length; i++)
				w.println("\tat " + trace[i]);

			Throwable ourCause = getCause();
			if (ourCause != null) {
				ourCause.printStackTrace(w);
			}
			w.flush();
		}
	}

	public interface ErrorCode {

		ResourceBundle bundle = ResourceBundle.getBundle("error-code");

		String getCode();

		String getDesc();
	}
}
