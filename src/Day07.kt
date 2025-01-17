fun main() {

    class puzzleLine(val testValue: ULong, val numbers: List<Int>) {
        var operatorsList: MutableList<String> = mutableListOf()
        override fun toString(): String {
            var output = "$testValue: "

            for (i in 0..numbers.size - 1) {
                output += "${numbers[i]} "
                if (i < operatorsList.size) {
                    output += "${operatorsList[i]} "
                }
            }

            output += "\n"

            return output
        }

        fun validateLine(): Boolean {
            var currentResult: ULong = numbers[0].toULong()

            for (i in 0 until numbers.size - 1) {
                if (operatorsList[i] == "+") {
                    currentResult += numbers[i+1].toULong()
                }
                else if (operatorsList[i] == "*") {
                    currentResult *= numbers[i+1].toULong()
                }
                else if (operatorsList[i] == "||") {
                    currentResult = "$currentResult${numbers[i+1]}".toULong()
                }
            }
            return currentResult == testValue
        }

    }

    fun processInput(input: List<String>): List<puzzleLine> {
        val puzzleInput: MutableList<puzzleLine> = mutableListOf()
        for (line in input) {
            val parts = line.split(Regex(":"))
            var numbers = parts[1].split(Regex(" "))
            numbers = numbers.drop(1)
            val convertedNumbers = numbers.map {it.toInt()}
            val puzzleInputLine: puzzleLine = puzzleLine(parts[0].toULong(), convertedNumbers)
            puzzleInput.add(puzzleInputLine)
        }
        return puzzleInput
    }

    fun generateAllOperatorCombinations(
        operatorsLength: Int,
        operatorTypes: List<String>,
        operatorCombinations: MutableList<MutableList<String>> = mutableListOf()
    ): MutableList<MutableList<String>> {
        // Start with a single empty combination
        operatorCombinations.add(mutableListOf())

        // For each level of operators to add
        repeat(operatorsLength) {
            val newCombinations = mutableListOf<MutableList<String>>()

            // Extend each existing combination with each operator in operatorTypes
            for (combination in operatorCombinations) {
                for (operator in operatorTypes) {
                    val newCombination = combination.toMutableList().apply { add(operator) }
                    newCombinations.add(newCombination)
                }
            }

            // Replace old combinations with the new ones
            operatorCombinations.clear()
            operatorCombinations.addAll(newCombinations)
        }

        return operatorCombinations
    }


    fun part1(input: List<String>): ULong {
        val puzzleInput = processInput(input)
        var totalCalibrationResult: ULong = 0.toULong()
        println(puzzleInput)

        for (line in puzzleInput) {
            val lineLength = line.numbers.size
            val operatorCombinations = generateAllOperatorCombinations(lineLength - 1,listOf("+","*"))
            for (combination in operatorCombinations) {
                line.operatorsList = combination.toMutableList()
                println(line)
                if(line.validateLine()) {
                    totalCalibrationResult += line.testValue
                    break
                }
            }
        }


        return totalCalibrationResult
    }


    fun part2(input: List<String>): ULong {
        val puzzleInput = processInput(input)
        var totalCalibrationResult: ULong = 0.toULong()
        println(puzzleInput)

        for (line in puzzleInput) {
            val lineLength = line.numbers.size
            val operatorCombinations = generateAllOperatorCombinations(lineLength - 1,listOf("+","*","||"))
            for (combination in operatorCombinations) {
                line.operatorsList = combination.toMutableList()
                println(line)
                if(line.validateLine()) {
                    totalCalibrationResult += line.testValue
                    break
                }
            }
        }


        return totalCalibrationResult
    }



    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInput("Day07_test")
//    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
//    part1(input).println()
    part2(input).println()
}