package automaton

import utils.Constants

case class Automaton(
               rootState: State, // do not change
               currentState: State,
               alphabet: Map[String, String],
               timeSinceLastKey: Long,
               ) {

  def delta(input: List[Int]): State = {
    val fromState = activeState()

    translate(input).flatMap(fromState.nextMoves.get).getOrElse(rootState)
  }

  def activeState(): State =
    val time = System.currentTimeMillis()
    if time - timeSinceLastKey < Constants.TIME_BETWEEN_KEYS then currentState
    else rootState

  private def translate(input: List[Int]): Option[String] = {
    val value: Option[String] = Constants UwU input
    value match {
      case Some(v) =>
        alphabet.get(v)
      case None =>
        if input.length == 1 then
          alphabet.get(input.head.toChar.toString)
        else None
    }
  }
}
