package test2;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@ContextConfiguration("/context.xml")
public class TransactionTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private PlatformTransactionManager tm;

	private TransactionTemplate template;

	@Before
	public void setUp() {
		template = new TransactionTemplate(tm);
	}

	@Test
	public void shouldTestTransaction() {
		String melding = template.execute(new TransactionCallback<String>() {
			@Override
			public String doInTransaction(final TransactionStatus transactionStatus) {
				try {
					return "Frode i transaksjon fra java";
				} catch (RuntimeException e) {
					transactionStatus.setRollbackOnly();
					return null;
				}
			}
		});
		System.out.println(melding);
	}

}
