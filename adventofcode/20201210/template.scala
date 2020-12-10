object Main extends App {
  for(input <- List("example", "example2", "input")){    
    println(s"\nfor '$input' :\n")

    val raw = scala.io.Source.fromFile(s"$input.txt").mkString

    println(raw.length)
  }
}
