object Main extends App {
  for(input <- List("example", "input")){    
    println(s"\nfor '$input' :\n")

    val groups = scala.io.Source
      .fromFile(s"$input.txt").mkString("").split("\n\n")
      .map(_.split("\n").map(_.split("").toSet).toList)
      .toList

    def countYesAnsweredQuestions(all:Boolean)(group:List[Set[String]]):Int = group
      .reduce((a, b) => if(all) a.intersect(b) else a.union(b))
      .count(_ => true)

    for(all <- List(false, true)) println(s"yes by ${if(all) "all " else "some"} = ${groups.map(countYesAnsweredQuestions(all)).sum}")
  }
}
