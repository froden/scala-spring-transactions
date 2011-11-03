package transactionexample

import org.junit.Test
import org.springframework.stereotype.Service
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests
import org.springframework.test.context.ContextConfiguration

import javax.annotation.Resource

@ContextConfiguration(Array("/context.xml"))
class ScalaTransactionTest extends AbstractJUnit4SpringContextTests {
  @Resource
  var service: MyScalaService = _

  @Test
  def shouldTestTransaction {
    service.printMessageFromDB()
  }
}

