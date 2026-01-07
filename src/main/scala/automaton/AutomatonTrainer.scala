package automaton

import scala.annotation.tailrec

case class AutomatonTrainer() {

  @tailrec
  final def generateStates(combos: List[(String, String)], holder: Map[String, State], index: Int): Map[String, State] = {
      if combos.isEmpty then
        holder
      else
        val firstElement = combos.head
        val (lastIndex, newHolder) = updateHolderWithSequence(holder, firstElement._2, parseSequence(firstElement._1), index)
        generateStates(combos.drop(1), newHolder, lastIndex + 1)
  }


  @tailrec
  def updateHolderWithSequence(holder: Map[String, State], name: String, sequence: Array[String], index: Int): (Int, Map[String, State]) = {
    if sequence.isEmpty then
      (index, holder)
    else {
      val element = sequence.head
      val result = holder.updatedWith(element)((valueState: Option[State]) => {
        valueState match {
          case None => {
            if sequence.length == 1 then
              Some(State(index, Map.empty, true, name))
            else {
//              val (tempInt, ) = updateHolderWithSequence()
              Some(State(index, Map.empty, false, ""))
            }
          }
          case Some(prevState) =>
            if sequence.length == 1 then
              Some(State(prevState.id, prevState.nextMoves, true, name))
            else
              Some(prevState)
        }
      })

    }
  }

  private def parseSequence(sequence: String): Array[String] = {
    sequence.split(", ")
  }

}
