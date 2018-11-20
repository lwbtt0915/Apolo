package org.framework.server.entrance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppServer {
	
	public static void main(String[] args) {
		String path="classpath*:spring-context.xml";
		
		ApplicationContext ctx=new ClassPathXmlApplicationContext(path);
		
//		NettyJaxrsServer netty = ctx.getBean(NettyJaxrsServer.class);
	}

}
