package fabos.framework.core.orm.model;

public interface Flowable {

	String getProcessId();

	void setProcessId(String processId);

	String getProcessName();

	void setProcessName(String processName);

	String getActiveVersion();

	void setActiveVersion(String activeVersion);

	String getProcessOpId();

	void setProcessOpId(String processOpId);

	String getProcessOpName();

	void setProcessOpName(String processOpName);

	String getSubProcessId();

	void setSubProcessId(String subProcessId);

	String getTopProcessOpId();

	void setTopProcessOpId(String topProcessOpId);

	String getReworkState();

	void setReworkState(String reworkState);

	Integer getReworkCount();

	void setReworkCount(Integer reworkCount);
}
