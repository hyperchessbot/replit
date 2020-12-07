object Main extends App {
  for(input <- List("example", "example2", "input")){    
    println(s"\nfor '$input' :\n")

    def toColor(color:String) = color.replaceAll("^\\d+ | bag$| bag\\.$| bags$| bags\\.$", "")

    case class Rule(ruleStr: String){
      val parts = ruleStr.split(" contain |, ")
      val color = toColor(parts(0))      
      val tail = parts.tail.filter(!_.contains("no other"))
      val contains = tail.map(toColor).toSet      
      val quantities = tail.map(color => toColor(color) -> color.split(" ")(0).toInt).toMap
      def canContain(color:String, rules:List[Rule]):Boolean = {
        if(contains.contains(color)) return true        
        for(rule <- rules.filter(rule => contains.contains(rule.color))) if(rule.canContain(color, rules)) return true
        false
      }
      def mustContain(rules:List[Rule]):Int = {
        var sum = 0
        for(color <- contains){
          sum += quantities(color)
          val colorRules = rules.filter(rule => rule.color == color)
          if(colorRules.length == 1){
            sum += quantities(color) * colorRules(0).mustContain(rules)
          }
        }
        sum
      }
    }

    val rules = scala.io.Source.fromFile(s"$input.txt").getLines.map(Rule(_)).toList

    val requiredColor = "shiny gold"

    println(s"number of bag colors that can contain $requiredColor = ${rules.filter(_.canContain(requiredColor, rules)).size}")

    println(s"a $requiredColor bag must contain ${rules.filter(rule => rule.color == requiredColor)(0).mustContain(rules)} bags")
  }
}
