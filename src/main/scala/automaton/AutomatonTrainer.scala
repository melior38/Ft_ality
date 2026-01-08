package automaton

import scala.annotation.tailrec

case class AutomatonTrainer() {

  @tailrec
  final def generateStates(combos: List[(String, String)], current: State, index: Int): State = {
      combos match {
        case Nil => current
        case (sequence, name) :: tail =>
          val moves = parseSequence(sequence)
          val (updatedRoot, newId) = updateHolderWithSequence(current, name, moves, index)
          generateStates(tail, updatedRoot, newId)
      }
  }

  private final def updateHolderWithSequence(current: State, name: String, sequence: List[String], index: Int): (State, Int) = {
    sequence match {
      case Nil =>
        (current.copy(isFinal = true, comboName = current.comboName :+ name), index)
      case move :: tail => {
        val (nextState, updatedNextId) = {
          current.nextMoves.get(move) match {
            case Some(exist) => (exist, index)
            case None =>
              val created = State(index, Map.empty, false, List.empty, move)
              (created, index+1)
          }
        }
        val (updatedChild, finalNextId) = updateHolderWithSequence(nextState, name, tail, updatedNextId)
        val updatedCurrent = current.copy(nextMoves =  current.nextMoves.updated(move, updatedChild))

        (updatedCurrent, finalNextId)
      }
    }
  }

  private def parseSequence(sequence: String): List[String] = {
    sequence.filterNot(_.isWhitespace).split(",").toList
  }

}
