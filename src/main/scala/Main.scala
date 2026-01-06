import scala.io.Source
import scala.sys.process._
import scala.io.StdIn.readLine
import utils.KeyUtils


object Main {
  def main(args: Array[String]): Unit = {

    val terminalHandler = KeyUtils

    val ts = terminalHandler.setupTTY() match {
      case Some(validTerm) =>
        validTerm
      case None =>
        print("Error happened with tty Setup, exiting !\n")
        sys.exit(1)
    }

    print("Starting run\n")
    var run = true
    var i = 0
    while run do {
      val collected = terminalHandler.collectKey(ts)
      collected match
        case Some(c) =>
          print(s"Found char $c\n")
          i += 1
        case None =>
          Thread.sleep(10)
      if i >= 10 then
        run = false
    }


    terminalHandler.restoreTTY(ts)


  }
}
