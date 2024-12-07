fun main() {
    val pattern = Regex("""mul\((\d{1,3}),(\d{1,3})\)""") // Define the regex with capturing groups

    fun multiplyString(input: String): Int  {
        val list1: MutableList<Int> = mutableListOf()
        val list2: MutableList<Int> = mutableListOf()
        val multiplied: MutableList<Int> = mutableListOf()

        val matches = pattern.findAll(input) // Find all matches

        for (match in matches) {
            list1.add(match.groupValues[1].toInt()) // Group 1: first number
            list2.add(match.groupValues[2].toInt()) // Group 2: second number
            multiplied.add(match.groupValues[1].toInt() * match.groupValues[2].toInt())
        }

//        println(matches)
//        println(list1)
//        println(list2)

        return multiplied.sum()
    }

    fun part1(input: List<String>): Int {
        var longString: String = ""

        for (line in input) {
            longString += line
        }
        println(longString)

        return multiplyString(longString)
    }

    fun part2(input: List<String>): Int {
        var totalMultiplication: Int = 0

        var longString: String = ""

        for (line in input) {
            longString += line
        }
        println(longString)

        val doParts = longString.split("do()")

        for (doPart in doParts) {
            val dontParts = doPart.split("don't()")
            totalMultiplication += multiplyString(dontParts[0])
        }

        return totalMultiplication
    }





    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput: List<String> = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()

}
