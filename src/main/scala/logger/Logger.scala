package logger

case class Logger(debugMode: Boolean) {
  def logDebug(data: String): Unit = {
    if debugMode then {
      print(data + "\r\n")
      System.out.flush()
    }
  }

  def log(data: String): Unit = {
    print(data+"\r\n")
    System.out.flush()
  }

  def logError(data: String): Nothing = {
    print(data + "\r\n")
    System.out.flush()
    sys.exit(1)
  }

}
