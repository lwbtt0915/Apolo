package fabos.framework.core.spring;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OracleCondition implements Condition {

	private static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			String driver = drivers.nextElement().getClass().getName();
			if (driver.equals(ORACLE_DRIVER)) {
				return true;
			}
		}
		return false;
	}

}
