import scala.io.Source
import scala.sys.process._
import scala.io.StdIn.readLine
import utils.KeyUtils
import utils.Loop.loop



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
    loop(ts)


    terminalHandler.restoreTTY(ts)


  }
}
