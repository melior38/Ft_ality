import Loop.loop
import automaton.*
import grammar.Grammar
import logger.Logger
import utils.KeyUtils



object Main {
  def main(args: Array[String]): Unit = {
    val logger: Logger = Logger(args contains "-debug")
    val ValidArguments = args.filter((data: String) => data != "-debug")
    if ValidArguments.length != 1 then {
      logger.logError("Missing Grammar File !!")
    }

    val grammar = Grammar(ValidArguments(0), logger)

    val alphabet: Map[String, String] = grammar.extractAlphabet()

    alphabet.foreach((tpl) => logger.log(s"${tpl._1} -> ${tpl._2}"))
    logger.log("---------------------")


    val automatonTrainer: AutomatonTrainer = AutomatonTrainer()
    val originalState: State = automatonTrainer.generateStates(grammar.extractCombos(), State(0, Map.empty, false, List.empty, ""), 1)

    val terminalHandler = KeyUtils

    val ts = terminalHandler.setupTTY() match {
      case Some(validTerm) => validTerm
      case None => logger.logError("Error happened with tty Setup, exiting !")
    }

    val automaton: Automaton = Automaton(originalState, originalState, alphabet, 0)
    loop(automaton, ts, logger)
    terminalHandler.restoreTTY(ts, logger)
  }
}
