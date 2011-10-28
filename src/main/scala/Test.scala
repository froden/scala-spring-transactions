package ex1

import java.util.Date

object FunctionExample extends App {
  val gangerTo = List(1, 2, 3).map(_ * 2)
  println(gangerTo)

  def print(text: String, decorate: (String) => String) {
    println(decorate(text))
  }

  print("Hello World!", "<html>" + _ + "</html>")

  def addTimestamp(text: String) = "%s - %s".format(new Date(), text)
  print("Hello World!", addTimestamp)
}