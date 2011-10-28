package test

import javax.annotation.Resource
import org.junit.{Test, Before}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests
import org.springframework.transaction.support.{TransactionCallback, TransactionTemplate}
import org.springframework.transaction.{TransactionStatus, PlatformTransactionManager}

@ContextConfiguration(Array("/context.xml"))
class TransactionTestScala extends AbstractJUnit4SpringContextTests {
  @Resource
  var service: MyServiceScala = _

  @Test
  def shouldTestTransaction {
    service.printMessageFromDB()
  }
}

@Service
class MyServiceScala @Autowired() (val tm: PlatformTransactionManager) extends Transactional {

  def printMessageFromDB() {
    doSomethingBefore()
    var melding = doInTransaction { _ => "Message from database"}
    println(melding)
  }

  def doSomethingBefore() {}
}

trait Transactional {
  val tm: PlatformTransactionManager
  val template: TransactionTemplate = new TransactionTemplate(tm)

  def doInTransaction[T](action: (TransactionStatus) => T): T = {
    return template.execute(new TransactionCallback[T] {
      override def doInTransaction(transactionStatus: TransactionStatus): T = {
        return action(transactionStatus)
      }
    })
  }
}