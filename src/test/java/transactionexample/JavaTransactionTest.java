package transactionexample;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration("/context.xml")
public class JavaTransactionTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private MyJavaService service;

	@Test
	public void shouldTestTransaction() {
		service.printMessageFromDB();
	}
}
