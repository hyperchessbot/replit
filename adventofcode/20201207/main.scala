object Main extends App {
  for(input <- List("example", "input")){    
    println(s"\nfor '$input' :\n")

    def toColor(color:String) = color.replaceAll("^\\d+ | bag$| bag\\.$| bags$| bags\\.$", "")

    case class Rule(ruleStr: String){
      val parts = ruleStr.split(" contain |, ")
      val color = toColor(parts(0))
      val contains = parts.tail.map(toColor).toSet      
      def canContain(color:String, rules:List[Rule]):Boolean = {
        if(contains.contains(color)) return true        
        for(rule <- rules.filter(rule => contains.contains(rule.color))) if(rule.canContain(color, rules)) return true
        false
      }
    }

    val rules = scala.io.Source.fromFile(s"$input.txt").getLines.map(Rule(_)).toList

    val requiredColor = "shiny gold"

    println(s"number of bag colors that can contain $requiredColor = ${rules.filter(_.canContain(requiredColor, rules)).size}")
  }
}
