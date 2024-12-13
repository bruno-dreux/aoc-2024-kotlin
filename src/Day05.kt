fun main() {

    fun processInput(input: List<String>): Pair<Map<Int, Int>, List<List<Int>>> {
        var rulesMap: MutableMap<Int, Int> = mutableMapOf()
        var updatesList: MutableList<List<Int>> = mutableListOf()



        return Pair(rulesMap.toMap(), updatesList.toList())
    }

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

//    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    part1(testInput).println()
//    part2(testInput).println()


    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day05")
//    part1(input).println()
//    part2(input).println()

}
