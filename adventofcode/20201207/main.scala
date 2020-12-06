object Main extends App {
  for(input <- List("example", "input")){    
    println(s"\nfor '$input' :\n")

    val inputStr = scala.io.Source
      .fromFile(s"$input.txt").mkString("")

    println(s"input size = ${inputStr.length}")
  }
}
