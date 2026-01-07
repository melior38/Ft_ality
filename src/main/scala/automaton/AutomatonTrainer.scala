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
    print(s"Currently working with sequence ${sequence.mkString}\r\n")
    System.out.flush()
    sequence match {
      case Nil =>
        print(s"Updating a value: ${current.id} ${true} ${name} ${current.comboKey}\r\n")
        System.out.flush()
        (current.copy(isFinal = true, comboName = name), index)
      case move :: tail => {
        val (nextState, updatedNextId) = {
          current.nextMoves.get(move) match {
            case Some(exist) => (exist, index)
            case None =>
              val created = State(index, Map.empty, false, "", move)
              print(s"Creating a value for $move: ${index} ${false} ${""}\r\n")
              System.out.flush()
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
    sequence.split(", ").toList
  }

}
