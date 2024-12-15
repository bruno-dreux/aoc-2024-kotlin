fun main() {

    fun checkDifferences (differences: MutableList<Int>): Int {
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

    fun checkLine(parts: List<String>): Int{
        var row = mutableListOf<Int>()
        var differences = mutableListOf<Int>()

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

        return checkDifferences(differences)
    }

    fun part1(input: List<String>): Int {
        val results = mutableListOf<Int>()

        for (line in input) {
            val parts = line.split(Regex("\\s+")) // Split by one or more spaces
            results.add(checkLine(parts))

        }
        println(results)
        return results.sum()
    }


    fun part2(input: List<String>): Int {
        val results = mutableListOf<Int>()

        for (line in input) {
            var parts = line.split(Regex("\\s+")) // Split by one or more
            if(checkLine(parts) == 1) {
                results.add(checkLine(parts))
            }
            else{
                var i = 0
                var flagSafeAfterRemoval = 0
                while(i<parts.size){
                    var smallerLine = parts.toMutableList()
                    smallerLine.removeAt(i)
                    if(checkLine(smallerLine) == 1){
                        results.add(checkLine(smallerLine))
                        flagSafeAfterRemoval = 1
                        i = parts.size
                    }
                    i++
                }
                if(flagSafeAfterRemoval == 0){
                    results.add(0)
                }
            }

        }
        println(results)
        return results.sum()
    }



    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()

}