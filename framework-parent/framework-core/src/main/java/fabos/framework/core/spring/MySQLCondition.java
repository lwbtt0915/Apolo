package fabos.framework.core.spring;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MySQLCondition implements Condition {

	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String MARIADB_DRIVER = "org.mariadb.jdbc.Driver";
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			String driver = drivers.nextElement().getClass().getName();
			if (driver.equals(MYSQL_DRIVER) || driver.equals(MARIADB_DRIVER)) {
				return true;
			}
		}
		return false;
	}

}
