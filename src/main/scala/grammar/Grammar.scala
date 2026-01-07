package grammar
import logger.Logger
import java.io.IOException

import scala.io.Source

case class Grammar(file: String, logger: Logger) {
  def extractAlphabet(): Map[String, String] = {
    try {
      val source =  Source.fromFile(file)
      val lines = source.getLines().toList.filter(_ contains " -> ").map((line: String) => {
        val temp = splitCheck(line, " -> ")
        (temp(0), temp(1))
      }).toMap
      source.close()
      lines
    } catch {
      case e: Exception => logger.logError(e.getMessage)
    }
  }

  def extractCombos(): List[(String, String)] = {
    try {
      val source = Source.fromFile(file)
      val lines = source.getLines().toList.filter(_ contains ": ").map((line: String) => {
        val temp = splitCheck(line, ": ")
        (temp(1), temp(0))
      })
      source.close()
      print(lines.mkString("\r\n"))
      lines
    } catch {
      case e: Exception => logger.logError(e.getMessage)
    }
  }

  private def splitCheck(data: String, separator: String): Array[String] = {
    val splitData = data.split(separator)
    if splitData.length != 2 then {
      logger.logError(s"Invalid Grammar Error: '$data' line is invalid")
    }
    splitData
  }

}
