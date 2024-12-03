fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        val differences = mutableListOf<Int>()

        for (line in input) {
            val parts = line.split(Regex("\\s+")) // Split by one or more spaces
            list1.add(parts[0].toInt())
            list2.add(parts[1].toInt())
        }

        list1.sort()
        list2.sort()

        for (i in list1.indices) {
            differences.add(kotlin.math.abs(list2[i] - list1[i]))
        }

//        print(list1)
//        print(list2)
//        print(differences)

        return differences.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

//    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    part1(testInput).println()

    check(part1(testInput) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
//    part2(input).println()

}
