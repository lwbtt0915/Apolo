package fabos.framework.flow.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabos.framework.flow.model.OperationDefinition;

@ActiveProfiles("mysql")
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration("classpath:spring-context.xml")
public class OperationDefnServiceTest {

	@Autowired
	private OperationDefnService service;
	
	//@Test
	public void test0Add() {
		OperationDefinition operation = new OperationDefinition();

		operation.setId("S1000");
		operation.setFactoryName("SCDX");
		operation.setOperationName("S1000");
		operation.setDescription("三维视觉");
		operation.setOperationType("Main");

		int resultCount = service.save(operation);
		Assert.assertTrue(resultCount == 1);
	}

	//@Test
	public void test1Get() {
		OperationDefinition operation = service.getById("F1000");
		Assert.assertNotNull(operation);

		List<OperationDefinition> list = service.selectPage(1, 10);
		Assert.assertTrue(!list.isEmpty());
	}
	
	@Test
	public void test2getOpNamesList() {
		String factoryName = "FZ";
		String opType = "F1000";
		List<OperationDefinition> list = service.getOpNamesList(factoryName, opType);
		System.out.println(list);
	}
}
