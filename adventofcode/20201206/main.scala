object Main extends App {
  for(input <- List("example", "input")){    
    println(s"\nfor '$input' :\n")

    val groups = scala.io.Source
      .fromFile(s"$input.txt").mkString("").split("\n\n")
      .map(_.split("\n").map(_.split("").toSet))

    def countYesAnsweredQuestions(all:Boolean)(group:Array[Set[String]]):Int = (if(all)
      group.reduce(_.intersect(_)) else
      group.reduce(_.union(_)))
      .size

    for(all <- List(false, true)) println(s"yes by ${if(all) "all " else "some"} = ${groups.map( countYesAnsweredQuestions(all)).sum}")
  }
}
