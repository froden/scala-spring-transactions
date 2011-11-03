package example1

import java.util.Date

object FunctionalExamples extends App {
  //Using scala's collection library
  val gangerTo = List(1, 2, 3).map(_ * 2)
  println(gangerTo)

  //Our own higher order function
  def print(text: String, decorate: (String) => String) {
    println(decorate(text))
  }
  print("Hello World!", "<html>" + _ + "</html>")

  def addTimestamp(text: String) = "%s - %s".format(new Date(), text)
  print("Hello World!", addTimestamp)
}