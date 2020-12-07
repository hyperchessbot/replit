object Main extends App {
  val requiredColor = "shiny gold"

  for(input <- List("example", "example2", "input")){    
    println(s"\nfor '$input' :\n")

    case class Color(colorStr:String){
      var parts = colorStr.split(" ").init
      var quantity = 1            
      if(parts.length > 2){
        quantity = parts(0).toInt
        parts = parts.tail
      }
      val color = parts.mkString(" ")
    }

    case class Rule(ruleStr:String){
      val parts = ruleStr.split(" contain |, ")
      val color = Color(parts(0)).color
      val contains = parts.tail.map(Color(_)).map(color => color.color -> color).toMap      
      def mustContain(rules:Map[String, Rule]):Int = {
        var sum = 0
        for((key, color) <- contains){
          sum += color.quantity
          if(rules.contains(key)) sum += color.quantity * rules(key).mustContain(rules)
        }
        sum
      }
    }

    val rules = scala.io.Source.fromFile(s"$input.txt").getLines.filter(!_.contains("contain no other")).map(Rule(_)).map(rule => rule.color -> rule).toMap

    def canContain(color:String):Int = {
      var colors = Set[String](color)      
      for((_, _) <- rules){                
        for((key, rule) <- rules){       
          for((testColor, _) <- rule.contains){
            if(colors.contains(testColor)){
              colors += key
            }
          }          
        }
      }          
      colors.size - 1
    }

    println(canContain(requiredColor))
    
    println(rules(requiredColor).mustContain(rules))
  }
}
