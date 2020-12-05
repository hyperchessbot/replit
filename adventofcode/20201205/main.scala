object Main extends App {
  val inputs = scala.io.Source.fromFile("input.txt").getLines.toList

  val occupSeatIds = inputs
    .map(_
      .replaceAll("B|R", "1")
      .replaceAll("F|L", "0")
    )
    .map(Integer.parseInt(_, 2))
    
  println(s"max seat ID ${occupSeatIds.max}")

  for(i <- 1 until 1023) {
    if(
      !occupSeatIds.contains(i)
      && occupSeatIds.contains(i - 1)
      && occupSeatIds.contains(i + 1)
    ) {
      println(s"missing seat ID satisfying constraints $i")
    }
  }
}
