object Main extends App {
  implicit class Combinations(n: Int) {
    private def fact(n: Int): Int = (1 to n).foldLeft(1)(_ * _)
    def ! = fact(n) // allows 10!
    def choose(k: Int): Int = fact(n) / (fact(n - k) * fact(k))
  }
  for(input <- List("example", "example2", "input")){    
    println(s"\nfor '$input' :\n")

    val jolts:List[Int] = 0 +: scala.io.Source.fromFile(s"$input.txt").getLines.map(_.toInt).toList.sorted

    val diffs = jolts.tail.zipWithIndex.map{case(jolt, i) => jolt - jolts(i)} :+ 3

    println(diffs)

    val ones = diffs.count(_ == 1)
    val threes = diffs.count(_ == 3)

    println(ones, threes, ones * threes)

    def vars(pocket:Int):Long = {
      if(pocket == 1) return 2
      if(pocket == 2) return 4
      if(pocket == 3) return 7
      println(s"sorry, no vars for $pocket")
      System.exit(1)
      1
    }

    val pockets = diffs.map(_.toString).mkString.split("3").filter(_.length > 1).map(_.length - 1).toList

    println(pockets)
    println(pockets.map(vars(_)).reduce(_ * _))
  }
}
