import scala.util.control.Breaks._

object Main extends App {    
  for(input <- List(("example", 5), ("input", 25))){    
    println(s"\nfor '$input' :\n")

    val numbers = scala.io.Source.fromFile(s"${input._1}.txt").getLines.map(_.toLong).toList

    val preamble = input._2

    def checkContig(contig:Int, required: Long):Boolean = {
      for(i <- 0 until numbers.length - contig + 1){
        val range = (i until (i + contig)).map(numbers(_))
        if(range.sum == required){
          println(range, range.min + range.max)
          return true
        }
      }
      false
    }

    breakable{
      for(i <- preamble until numbers.length){
        val pocket = ((i - preamble) until i).map(numbers(_)).toSet
        val current = numbers(i)
        val check = pocket.map(current - _).toSet
        if(pocket.intersect(check).isEmpty){
          println(current)
          var contig = 2
          while(!checkContig(contig, current)) contig += 1
          break
        }
      }
    }
  }
}
