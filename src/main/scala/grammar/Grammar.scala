package grammar
import logger.Logger

import scala.io.Source
import scala.util.Using

case class Grammar(file: String) {
  def extractGrammar(): Either[String, (Map[String,String], List[(String,String)])] = {
      for {
        lines <- readLines(file)
        alpha <- extractAlphabet(lines)
        combos <- extractCombos(lines)
      } yield (alpha, combos)
  }

  private def readLines(file: String): Either[String, List[String]] = {
    Using(Source.fromFile(file)) { source =>
      source.getLines().toList
    }.toEither.left.map(_ => s"Cannot open grammar file")
  }

  private def extractAlphabet(source: List[String]): Either[String, Map[String, String]] = {
    source.filter(_ contains "->").map(splitCheck(_,"->")).foldLeft(Right(Map.empty): Either[String, Map[String,String]]) {
      case (Right(acc), Right((v1, v2))) => Right(acc.+((v1.filterNot(_.isWhitespace), v2.filterNot(_.isWhitespace))))
      case (Left(err), _) => Left(err)
      case (_, Left(err)) => Left(err)
    }
  }

  private def extractCombos(source: List[String]): Either[String, List[(String, String)]] = {
    source.filter(_ contains ":").map(splitCheck(_, ":")).foldLeft(Right(List.empty): Either[String, List[(String, String)]]) {
      case (Right(acc), Right((v1, v2))) => Right(acc :+ (v1, v2.filterNot(_.isWhitespace)))
      case (Left(err), _) => Left(err)
      case (_, Left(err)) => Left(err)
    }
  }

  private def splitCheck(data: String, separator: String): Either[String, (String, String)] = {
    val splitData = data.split(separator).map(_.trim)
    if splitData.length != 2 || splitData.contains("") then {
      Left(s"Invalid Grammar Error: '$data' line is invalid")
    } else {
      Right((splitData(0), splitData(1)))
    }
  }

}
