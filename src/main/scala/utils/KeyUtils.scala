package utils

case class TerminalData(state: Option[String], isRunning: Boolean)

object KeyUtils {


  def setupTTY(): Option[TerminalData] =
    try {
      val saveState = Runtime.getRuntime.exec(Array("sh", "-c", s"stty -g "))
      val stateData = scala.io.Source.fromInputStream(saveState.getInputStream).mkString.trim
      saveState.waitFor()
      Runtime.getRuntime.exec(Array("sh", "-c", s"stty -echo raw < /dev/tty")).waitFor()
      Some(TerminalData(Some(stateData), true))
    } catch {
      case e: Exception => None
    }

  def restoreTTY(termState: TerminalData): TerminalData =
    if termState.isRunning then
      try
        termState.state match {
          case Some(state) => Runtime.getRuntime.exec(Array("sh", "-c", s"stty $state < /dev/tty")).waitFor()
          case None => Runtime.getRuntime.exec(Array("sh", "-c", s"stty echo cooked < /dev/tty")).waitFor()
        }
        termState.copy(isRunning=false)
      catch
        case e:Exception => TerminalData(None, false)
    else
      termState

  def collectKey(terminalData: TerminalData): Option[Char] = {

    if terminalData.isRunning then
      try
        print(System.in.available())
        if System.in.available() > 0 then
          Some(System.in.read().toChar)
        else
          None
      catch
        case e: Exception => None
    else
      None
  }

}
