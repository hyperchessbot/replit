object Main extends App {
  //http://biercoff.com/easily-measuring-code-execution-time-in-scala/
  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/1000 + "ms")
    result
  }

  for(input <- List("example", "input")){    
    println(s"\nfor '$input' :\n")

    case class Instruction(instructionStr:String){
      val parts = instructionStr.split(" ")
      var op = parts(0)
      val arg = parts(1).toInt
      var executed = false
      var index = -1
      var target = -1
    }

    case class Machine(instructions: List[Instruction], var revert:Int = -1){
      var flip = false
      var acc = 0
      var line = 0
      var targets = scala.collection.mutable.Map[Int, Set[Int]]()
      def buildTargets():Unit = {                
        for(i <- 0 until instructions.length){
          val ins = instructions(i)
          ins.index = i
          var target = ins.op match {
            case "jmp" => i + ins.arg
            case _ => i + 1
          }
          // all instructions that jump out of end considered to jump right after end
          if(target >= instructions.length) target = instructions.length
          ins.target = target
          if(targets.contains(target)) targets(target) = targets(target) + i else targets(target) = Set(i)
        }        
      }
      time{
        println("building targets")
        buildTargets()
      }      
      var backwardSet = Set[Int]()
      def buildBakcwardSet():Unit = {
        var allNodes = (0 until instructions.length).toSet
        def examineRec(examine: Set[Int]):Unit = {          
          if(examine.isEmpty) return
          var aggr = Set[Int]()
          for(target <- examine){            
            if(targets.contains(target)){
              val froms = targets(target).intersect(allNodes)              
              allNodes = allNodes -- froms
              aggr = aggr.union(froms)
              backwardSet = backwardSet.union(froms)
            }
          }
          examineRec(aggr)
        }
        examineRec(Set(instructions.length))
      }
      time{
        println("building backward set")
        buildBakcwardSet()      
      }      
      def terminated:Boolean = line >= instructions.length
      def step():Boolean = {        
        if(terminated) return false
        val instruction = instructions(line)        
        if(instruction.executed) return false
        var jmp = 1
        var op = instruction.op
        var revertOp = op
        if(instruction.op == "jmp") revertOp = "nop"
        if(instruction.op == "nop") revertOp = "jmp"
        if(flip){
          var next = line + 1
          if(revertOp == "jmp") next = line + instruction.arg
          if(backwardSet.contains(next)){            
            flip = false
            revert = line
          }
        }
        if(line == revert){
          op = revertOp
        }
        //println(s"$line $acc ${instruction.op} ${op} ${instruction.arg}")        
        op match{
          case "nop" => {}
          case "acc" => acc += instruction.arg
          case "jmp" => jmp = instruction.arg
        }
        instruction.executed = true
        line += jmp        
        true
      }      
      def run():Boolean = {
        while(step()){}
        terminated
      }
      def reset():Unit = {
        acc = 0
        line = 0
        instructions.foreach(_.executed = false)
      }
    }

    val instructions = scala.io.Source.fromFile(s"$input.txt").getLines.map(Instruction(_)).toList

    var machine = Machine(instructions)

    machine.run()

    println(machine.acc)

    machine.reset()

    time{
      println("revert naive")
      while(!machine.terminated){
        machine.reset()
        machine.revert = machine.revert + 1
        machine.run() 
      }
    }

    println(machine.acc, machine.revert)

    time{
      println("revert backward set")
      machine.reset()
      machine.revert = -1
      machine.flip = true
      machine.run()
    }

    println(machine.acc, machine.revert)
  }
}
