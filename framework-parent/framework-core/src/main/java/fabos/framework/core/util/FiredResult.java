package fabos.framework.core.util;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Sets;

import fabos.framework.core.exception.BizServiceException;

/**
 * Drools规则引擎执行后的返回结果
 */
public class FiredResult {

	private int firedRulesCount = 0;
	
	private Set<String> firedRuleNames = Sets.newHashSet();

	private BizServiceException serviceException;

	public int getFiredRulesCount() {
		return firedRulesCount;
	}

	public void setFiredRulesCount(int firedRulesCount) {
		this.firedRulesCount = firedRulesCount;
	}

	public Set<String> getFiredRuleNames() {
		return firedRuleNames;
	}

	public void addRuleName(String ruleName) {
		this.firedRuleNames.add(ruleName);
	}

	public BizServiceException getServiceException() {
		return serviceException;
	}

	public void setServiceException(BizServiceException serviceException) {
		this.serviceException = serviceException;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
