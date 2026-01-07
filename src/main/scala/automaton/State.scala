package automaton

case class State(id: Int,nextMoves: Map[String, State], isFinal: Boolean, comboName: String, comboKey: String)

