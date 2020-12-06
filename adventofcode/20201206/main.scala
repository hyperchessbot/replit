object Main extends App {
  for(input <- List("example", "input")){    
    println(s"\nfor '$input' :\n")

    val groups = scala.io.Source.fromFile(s"$input.txt").mkString("").split("\n\n").toList

    def countYesAnsweredQuestions(all:Boolean)(group:String):Int = {
      group.split("\n")
        .map(_.split("").toSet)
        .reduce((a, b) => if(all) a.intersect(b) else a.union(b))
        .count(_ => true)      
    }

    for(all <- List(false, true)) println(s"yes by ${if(all) "all " else "some"} = ${groups.map(countYesAnsweredQuestions(all)).sum}")
  }
}
