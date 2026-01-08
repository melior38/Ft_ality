package grammar
import logger.Logger

import scala.io.Source

case class Grammar(file: String, logger: Logger) {
  def extractAlphabet(): Map[String, String] = {
    try {
      val source =  Source.fromFile(file)
      val lines = source.getLines().toList.filter(_ contains "->").map((line: String) => {
        val temp = splitCheck(line, "->")
        (temp(0).filterNot(_.isWhitespace), temp(1).filterNot(_.isWhitespace))
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
      val lines = source.getLines().toList.filter(_ contains ":").map((line: String) => {
        val temp = splitCheck(line, ":")
        (temp(1).filterNot(_.isWhitespace), temp(0))
      })
      source.close()
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
