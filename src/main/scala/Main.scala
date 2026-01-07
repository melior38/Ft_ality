import utils.KeyUtils
import Loop.loop
import automaton.*
import grammar.Grammar
import logger.Logger



object Main {
  def main(args: Array[String]): Unit = {
    val logger: Logger = Logger(args contains "-debug")
    val ValidArguments = args.filter((data: String) => data != "-debug")
    if ValidArguments.length != 1 then {
      logger.logError("Missing Grammar File !!")
    }

    val grammar = Grammar(ValidArguments(0), logger)
    

    val terminalHandler = KeyUtils

    val ts = terminalHandler.setupTTY() match {
      case Some(validTerm) => validTerm
      case None => logger.logError("Error happened with tty Setup, exiting !")
    }

    val automaton: Automaton = Automaton(Map.empty, State(0, Map.empty, false, ""), State(0, Map.empty, false, ""), Map.empty)
    loop(automaton, ts, logger)
    terminalHandler.restoreTTY(ts, logger)
  }
}
