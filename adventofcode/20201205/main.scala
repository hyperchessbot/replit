import scala.util.chaining._

object Main extends App {
  val inputs = scala.io.Source.fromFile("input.txt").getLines.toList

  inputs
    .map(_
      .replaceAll("B|R", "1")
      .replaceAll("F|L", "0")
    )
    .map(Integer.parseInt(_, 2))
    .max
    .tap(println)
}
