package utils

import logger.Logger

case class TerminalData(state: Option[String], isRunning: Boolean)

object KeyUtils {


  def setupTTY(): Option[TerminalData] =
    try {
      val saveState = Runtime.getRuntime.exec(Array("sh", "-c", s"stty -g < /dev/tty"))
      val stateData = scala.io.Source.fromInputStream(saveState.getInputStream).mkString.trim
      saveState.waitFor()
      Runtime.getRuntime.exec(Array("sh", "-c", s"stty -echo raw < /dev/tty")).waitFor()
      Some(TerminalData(Some(stateData), true))
    } catch {
      case e: Exception => None
    }

  def restoreTTY(termState: TerminalData, logger: Logger): TerminalData =
    if termState.isRunning then {
      logger.logDebug("Restoring TTY")
      try
        termState.state match {
          case Some(state) =>
            logger.logDebug("Restore saved state")
            Runtime.getRuntime.exec(Array("sh", "-c", s"stty $state < /dev/tty")).waitFor()
          case None =>
            logger.logDebug("Restore with cooked state")
            Runtime.getRuntime.exec(Array("sh", "-c", s"stty echo cooked < /dev/tty")).waitFor()
        }
        termState.copy(isRunning=false)
      catch
        case e:Exception => TerminalData(None, false)
    } else
      termState

  def collectKey(terminalData: TerminalData): Option[List[Int]] = {
    if terminalData.isRunning then
      try {
        val available = System.in.available()
        if available > 0 then {
          Some(List.fill(available)(System.in.read()))
        } else
          None
      } catch
        case e: Exception => None
    else
      None
  }

}
