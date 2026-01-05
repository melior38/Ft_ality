import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello World from ft_ality 🚀")
    val source : String = Source.fromFile("src/main/scala/grammars/moveset0").mkString
    val source2 : String = Source.fromFile("src/main/scala/grammars/moveset1").mkString
    val sourceArray = source2.lines()


    println(s"lines: $source \n\n\n $source2")
    println(s"-"*35)

    println(s"\n\n\n")

    var i : Int = 0
    for( line <- source.linesIterator) {
      i  = i + 1
      println(s"line [$i] : $line")
    }


  }
}