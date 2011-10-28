package test2;

import javax.annotation.Resource;

import org.junit.Before;
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
public class TransactionTest2 extends AbstractJUnit4SpringContextTests {

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
            this.template = new TransactionTemplate(tm);
        }

        public void printMessageFromDB() {
            doSomethingBefore();
            String message = template.execute(new RollbackOnExceptionTransaction<String>() {
			    @Override
			    public String doInTransaction() {
				    return getMessageFromDB();
			    }
		    });
            System.out.println(message != null ? message : "No message found");
        }

        String getMessageFromDB() {
            return "Message from Database";
        }
        void doSomethingBefore() {}

    }

	static public abstract class RollbackOnExceptionTransaction<T> implements TransactionCallback<T> {

		public abstract T doInTransaction();

		@Override
		public T doInTransaction(final TransactionStatus status) {
			try {
				return doInTransaction();
			} catch (RuntimeException e) {
				status.setRollbackOnly();
				return null;
			}
		}

	}

}
