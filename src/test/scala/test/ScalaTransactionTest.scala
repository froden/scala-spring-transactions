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
class ScalaTransactionTest extends AbstractJUnit4SpringContextTests {
  @Resource
  var service: MyScalaService = _

  @Test
  def shouldTestTransaction {
    service.printMessageFromDB()
  }
}

@Service
class MyScalaService @Autowired() (val tm: PlatformTransactionManager) extends Transactional {

  def printMessageFromDB() {
    doSomethingBefore()
    var melding = rollbackOnExceptionTransaction { () =>
      getMessageFromDB()
    }
    println(melding.getOrElse("No message found"))
  }

  def getMessageFromDB() = throw new RuntimeException
  def doSomethingBefore() {}
}

trait Transactional {
  val tm: PlatformTransactionManager
  val template: TransactionTemplate = new TransactionTemplate(tm)

  def rollbackOnExceptionTransaction[T](action: () => T): Option[T] = {
    return doInTransaction {
      transactionStatus =>
        try {
          return Some(action())
        } catch {
          case e: RuntimeException => {
            transactionStatus.setRollbackOnly
            None
          }
        }
    }
  }

  def doInTransaction[T](action: (TransactionStatus) => Option[T]): Option[T] = {
    return template.execute(new TransactionCallback[Option[T]] {
      override def doInTransaction(transactionStatus: TransactionStatus): Option[T] = {
        return action(transactionStatus)
      }
    })
  }
}