package transactionexample
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.stereotype.Service

@Service
class MyScalaService @Autowired() (val tm: PlatformTransactionManager) extends Transactional {

  def printMessageFromDB() {
    doSomethingBefore()
    val message = doInTransaction { _ => DB.getMessage }
    println(message)
  }

  def doSomethingBefore() {}
}