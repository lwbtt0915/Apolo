package fabos.framework.flow.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "FLOW_OP_DEFN")
public class OperationDefinition implements Serializable {

	private static final long serialVersionUID = -970765068823700024L;

	@Id
	private String id;

	@Column(name = "FTY_NAME")
	private String factoryName;

	@Column(name = "OP_NAME")
	private String operationName;

	private String description;

	@Column(name = "OP_VER")
	private String operationVersion;

	@Column(name = "OP_TYP")
	private String operationType;

	@Column(name = "DTL_OP_TYP")
	private String detailOperationType;

	@Column(name = "OP_GRP")
	private String operationGroup;

	@Column(name = "OP_UNIT")
	private String operationUnit;

	@Column(name = "DFLT_AREA_NAME")
	private String defaultAreaName;

	@Column(name = "DFLT_UNIT_GRD")
	private String defaultUnitGrade;

	@Column(name = "TRK_OUT_TYP")
	private String trackOutType;

	@Column(name = "AUTO_HLD")
	private String autoHold;

	@Column(name = "PRT_LBL_TYP")
	private String printLabelType;

	@Column(name = "BANK_TYP")
	private String bankType;

	private String bankName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperationVersion() {
		return operationVersion;
	}

	public void setOperationVersion(String operationVersion) {
		this.operationVersion = operationVersion;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getDetailOperationType() {
		return detailOperationType;
	}

	public void setDetailOperationType(String detailOperationType) {
		this.detailOperationType = detailOperationType;
	}

	public String getOperationGroup() {
		return operationGroup;
	}

	public void setOperationGroup(String operationGroup) {
		this.operationGroup = operationGroup;
	}

	public String getOperationUnit() {
		return operationUnit;
	}

	public void setOperationUnit(String operationUnit) {
		this.operationUnit = operationUnit;
	}

	public String getDefaultAreaName() {
		return defaultAreaName;
	}

	public void setDefaultAreaName(String defaultAreaName) {
		this.defaultAreaName = defaultAreaName;
	}

	public String getDefaultUnitGrade() {
		return defaultUnitGrade;
	}

	public void setDefaultUnitGrade(String defaultUnitGrade) {
		this.defaultUnitGrade = defaultUnitGrade;
	}

	public String getTrackOutType() {
		return trackOutType;
	}

	public void setTrackOutType(String trackOutType) {
		this.trackOutType = trackOutType;
	}

	public String getAutoHold() {
		return autoHold;
	}

	public void setAutoHold(String autoHold) {
		this.autoHold = autoHold;
	}

	public String getPrintLabelType() {
		return printLabelType;
	}

	public void setPrintLabelType(String printLabelType) {
		this.printLabelType = printLabelType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
