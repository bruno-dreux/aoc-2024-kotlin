import kotlin.math.roundToInt

fun main() {

    class linearSystem(val ax: Int,val  bx: Int,val resultx: Int,val ay: Int,val by: Int,val resulty: Int) {
        var a: Double = -1.0
        var b: Double = -1.0
        var aInt: Int = -1
        var bInt: Int = -1

        override fun toString(): String {
            return "Linear system: $ax, $bx, $resultx, $ay, $by, $resulty"
        }

        fun solve() {
            val numerator: Double = resulty.toDouble() - (resultx*ay)/ax.toDouble()
            val denominator = by - (bx*ay).toDouble()/ax
            if(denominator != 0.0) {
                b = numerator/denominator
                a = (resultx-bx*b)/ax
                aInt = a.roundToInt()
                bInt = b.roundToInt()
            }
            else{
                println("Denominator is 0")
                println("$numerator, $denominator, $a, $b")
            }
            println("$numerator, $denominator, $a, $b")
        }

        fun checkIntegerSolution(): Boolean{
            return ax*aInt+bx*bInt == resultx && ay*aInt+by*bInt == resulty
        }
    }


    fun processInput(input: List<String>): MutableList<linearSystem> {
        val regexAB = Regex("""X\+(\d+), Y\+(\d+)""")
        val regexResult = Regex("""X=(\d+), Y=(\d+)""")
        val output = mutableListOf<linearSystem>()
        var ax: Int = 0
        var ay: Int = 0
        var bx: Int = 0
        var by: Int = 0
        var resultx: Int = 0
        var resulty: Int = 0
        for (row in input.indices) {
            if(input[row].contains("Button A:")) {
                val matchResultA = regexAB.find(input[row])
                if (matchResultA != null) {
                    ax = matchResultA.groupValues[1].toInt()
                    ay = matchResultA.groupValues[2].toInt()
                }
                val matchResultB = regexAB.find(input[row+1])
                if (matchResultB != null) {
                    bx = matchResultB.groupValues[1].toInt()
                    by = matchResultB.groupValues[2].toInt()
                }
                val matchResultResult = regexResult.find(input[row+2])
                if (matchResultResult != null) {
                    resultx = matchResultResult.groupValues[1].toInt()
                    resulty = matchResultResult.groupValues[2].toInt()
                }
                output.add(linearSystem(ax,bx,resultx,ay,by,resulty))
            }
        }
        return output
    }


    fun part1(input: List<String>): Int {
        var listSystems = processInput(input)
        var tokensSum = 0

        for (system in listSystems) {
            println(system)
            system.solve()
            println("A = ${system.a}/${system.aInt}, B = ${system.b}/${system.bInt}")
            if(system.checkIntegerSolution()) {
                println("Valid solution to system, A: ${system.aInt}, B: ${system.bInt}")
                tokensSum += system.aInt*3 + system.bInt
            }
        }
        return tokensSum
    }


    fun part2(input: List<String>): Int {
        return 0
    }



    // Or read a large test input from the file:
//    val testInput = readInput("Day13_test")
//    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the file.
    val input = readInput("Day13")
    part1(input).println()
//    part2(input).println()
}