package fabos.framework.flow.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "FLOW_PROC_DEFN")
public class ProcessDefinition implements Serializable {

	private static final long serialVersionUID = 6437343754229041076L;

	@Id
	@Column(name = "PROC_ID")
	private String processId;

	@Column(name = "PROC_NAME")
	private String processName;

	@Column(name = "PROC_VER")
	private String processVersion;

	@Column(name = "PROC_TYP")
	private String processType;

	@Column(name = "FTY_NAME")
	private String factoryName;

	private String description;

	@Column(name = "ACTV_ST")
	private String activeState;

	private byte[] bpmnXml;

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessVersion() {
		return processVersion;
	}

	public void setProcessVersion(String processVersion) {
		this.processVersion = processVersion;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}

	public byte[] getBpmnXml() {
		return bpmnXml;
	}

	public void setBpmnXml(byte[] bpmnXml) {
		this.bpmnXml = bpmnXml;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
