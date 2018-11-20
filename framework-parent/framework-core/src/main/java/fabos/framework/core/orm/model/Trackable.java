package fabos.framework.core.orm.model;

import java.util.Date;

public interface Trackable {

	String getLastEventName();

	void setLastEventName(String lastEventName);

	String getLastEventTimeKey();

	void setLastEventTimeKey(String lastEventTimeKey);

	Date getLastEventTime();

	void setLastEventTime(Date lastEventTime);

	String getLastEventUser();

	void setLastEventUser(String lastEventUser);

	String getLastEventComment();

	void setLastEventComment(String lastEventComment);

	String getLastEventFlag();

	void setLastEventFlag(String lastEventFlag);

	Date getCreateTime();

	void setCreateTime(Date createTime);

	String getCreateUser();

	void setCreateUser(String createUser);

	String getReasonCodeType();

	void setReasonCodeType(String reasonCodeType);

	String getReasonCode();

	void setReasonCode(String reasonCode);
	
}
