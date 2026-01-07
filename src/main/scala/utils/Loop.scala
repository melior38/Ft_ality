package utils

import KeyUtils.collectKey

import scala.annotation.tailrec


object Loop {
  @tailrec
  def loop(ts : TerminalData): Unit = {
    val key = collectKey(ts)
    var run = true
    key match
      case Some(c) =>
        print(s"Found char $c\n")
        if (c == 'q')
          return
      case None =>
        Thread.sleep(1000)
    loop(ts)
  }
}

