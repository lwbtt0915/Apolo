package fabos.framework.flow.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ProcessActionPK implements Serializable {

	private static final long serialVersionUID = 9099812501608134859L;

	private String opId;

	private Integer ruleId;

	public ProcessActionPK() {
		super();
	}

	public ProcessActionPK(String opId, Integer ruleId) {
		super();
		this.opId = opId;
		this.ruleId = ruleId;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
