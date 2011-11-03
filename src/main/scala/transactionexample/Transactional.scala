package transactionexample
import org.springframework.transaction.support.{ TransactionCallback, TransactionTemplate }
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus

trait Transactional {
  val tm: PlatformTransactionManager
  val template: TransactionTemplate = new TransactionTemplate(tm)

  def doInTransaction[T](action: (TransactionStatus) => T): T = {
    template.execute(new TransactionCallback[T] {
      override def doInTransaction(transactionStatus: TransactionStatus): T = {
        action(transactionStatus)
      }
    })
  }
}