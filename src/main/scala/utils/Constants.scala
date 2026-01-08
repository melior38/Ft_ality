package utils

object Constants {
  val TIME_BETWEEN_KEYS = 500
  def UwU(seq: List[Int]): Option[String] = {
    seq match
      case List(27, 91, 68) => Some("left")
      case List(27, 91, 67) => Some("right")
      case List(27, 91, 66) => Some("down")
      case List(27, 91, 65) => Some("up")
      case _ => None
  }
}