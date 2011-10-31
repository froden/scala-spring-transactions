package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@ContextConfiguration("/context.xml")
public class JavaTransactionTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private MyJavaService service;

	@Test
	public void shouldTestTransaction() {
		service.printMessageFromDB();
	}

	@Service
	static public class MyJavaService {
		private final TransactionTemplate template;

		@Autowired
		public MyJavaService(final PlatformTransactionManager tm) {
			template = new TransactionTemplate(tm);
		}

		public void printMessageFromDB() {
			doSomethingBefore();
			String message = template.execute(new TransactionCallback<String>() {
				@Override
				public String doInTransaction(final TransactionStatus transactionStatus) {
					return DB.getMessage();
				}
			});
			System.out.println(message);
		}

		void doSomethingBefore() {}
	}
}
