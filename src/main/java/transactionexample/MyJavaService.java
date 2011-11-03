package transactionexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class MyJavaService {
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

	void doSomethingBefore() {
	}
}
