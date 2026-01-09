import automaton.Automaton
import logger.Logger
import utils.KeyUtils.collectKey
import utils.TerminalData
import utils.Constants
import scala.annotation.tailrec

object Loop {
  @tailrec
  def loop(automaton: Automaton, ts : TerminalData, logger: Logger): Unit = {
    collectKey(ts) match
      case Some(c) if c.nonEmpty && (c.head == 3 || c.head == 4) =>
          ()
      case Some(c) =>
        val time = System.currentTimeMillis()
        val newState = automaton.delta(c)

        if newState.id != 0 then {
          logger.logDebug(s"State ${automaton.currentState.id}, \"${newState.comboKey}\" -> State ${newState.id}")
        }
        if newState.isFinal then
          newState.comboName.foreach(name => logger.logDebug(s"Found end state for \"${name}\" at: ${newState.id}"))
          newState.comboName.foreach(name => logger.log(name))
          logger.log("")
        val newAutomaton = automaton.copy(
          currentState = newState,
          timeSinceLastKey = time
        )
        loop(newAutomaton, ts, logger)
      case None =>
        Thread.sleep(10)
        //TODO: Check how to optimize this part not to clone automaton every 10 ms
        loop(automaton.copy(currentState = automaton.activeState()), ts, logger)
  }

  
  def update(automaton: Automaton, input: List[Int], time: Long): Automaton =
    automaton.copy(currentState = automaton.delta(input), timeSinceLastKey = time)
}

