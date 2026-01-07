import automaton.Automaton
import logger.Logger
import utils.KeyUtils.collectKey
import utils.TerminalData

import scala.annotation.tailrec


object Loop {
  @tailrec
  def loop(automaton: Automaton, ts : TerminalData, logger: Logger): Unit = {
    val key = collectKey(ts)
    var run = true
    key match
      case Some(c) =>
        logger.logDebug(s"Found char $c")
        if (c == 'q')
          return
      case None =>
        Thread.sleep(10)
    loop(automaton, ts, logger)
  }
}

