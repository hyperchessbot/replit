object Main extends App {
  for(input <- List("example", "input")){    
    println(s"\nfor '$input' :\n")

    case class Instruction(instructionStr:String){
      val parts = instructionStr.split(" ")
      var op = parts(0)
      val arg = parts(1).toInt
      var executed = false
    }

    case class Machine(instructions: List[Instruction], var revert:Int = -1){
      var acc = 0
      var line = 0
      def terminated:Boolean = line >= instructions.length
      def step():Boolean = {        
        if(terminated) return false
        val instruction = instructions(line)        
        if(instruction.executed) return false
        var jmp = 1
        var op = instruction.op
        if(line == revert){
          if(instruction.op == "jmp") op = "nop"
          if(instruction.op == "nop") op = "jmp"
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

    while(!machine.terminated){
      machine.reset()
      machine.revert = machine.revert + 1
      machine.run() 
    }

    println(machine.acc)
  }
}
