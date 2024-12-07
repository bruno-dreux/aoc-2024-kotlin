fun main() {

    fun checkSafe (differences: MutableList<Int>): Int {
        val minValue = differences.min()
        val maxValue = differences.max()

        if (differences.contains(0)) { //no repeating values
            return 0
        }

        if (!(minValue <0 && maxValue <0) && !(minValue >0 && maxValue >0)) { //All increasing or all decreasing
            return 0
        }

        for (difference in differences) {
            if ((kotlin.math.abs(difference) >3) || kotlin.math.abs(difference) <1) {
                return 0
            }
        }


        return 1
    }

    fun part1(input: List<String>): Int {
        val results = mutableListOf<Int>()
        var row = mutableListOf<Int>()
        var differences = mutableListOf<Int>()


        for (line in input) {
            val parts = line.split(Regex("\\s+")) // Split by one or more spaces
            row = mutableListOf()
            differences = mutableListOf()
            for (part in parts) {
                row.add(part.toInt())
            }

            var i = 0
            while (i < row.indices.max()) {
//                print(i)
                differences.add(row[i+1] - row[i])
                i++
            }
//            println(differences)
            results.add(checkSafe(differences))

        }
//        println(results)

        return results.sum()
    }





    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
//    part2(input).println()

}

//TODO
//Day 2, part 2: When it is unsafe, try all combinations of removing one element and see which ones become safe then (brute force)
//Day 3, part 2: Split the strings on the dos and dont's, then run the regex only on the parts after the dos
//Day 4, part 1: Need to create the diagonals in the opposite direction