fun main() {




    fun countXMASForward (input: String): Int {
        //TODO change this to get all occurrences in the line, not only 1
        return Regex("XMAS").findAll(input).count()
    }

    fun countXMASinString (input: String): Int {
        return countXMASForward(input) + countXMASForward(input.reversed())
    }


    fun part1(input: List<String>): Int {
        var globalCount: Int = 0
        var countHorizontal: Int = 0
        var countVertical: Int = 0
        var countDiagonal: Int = 0
        val verticalStrings: MutableList<String> = mutableListOf()
        val diagonalStrings: MutableList<String> = mutableListOf()
        val diagonalStrings2: MutableList<String> = mutableListOf()

        for (line in input) {
            countHorizontal += countXMASinString(line) //Searching for horizontal occurrences (forward and reverse)
        }

        // Building the vertical strings
        var j = 0
        while (j < input[0].length) {
            verticalStrings.add("")
            j++
        }

        var i = 0
        while (i < input.size) {
            val line = input[i]

            var j = 0
            while (j < line.length) {
                val currentValue = verticalStrings.getOrElse(j,{""})
                verticalStrings[j] = currentValue+line[j].toString()
                j++
            }
            i++
        }

//        println(verticalStrings)

        for (line in verticalStrings) {
            countVertical += countXMASinString(line) //Searching for horizontal occurrences (forward and reverse)
        }

        //Building the diagonal strings
        //Starting at the top left element and moving right
        var column = 0
        while (column < input[0].length) {
            diagonalStrings.add(input[0][column].toString())

            var row = 1

            while (row < input.size && column + row < input[0].length) {
                diagonalStrings[column] = diagonalStrings[column] + input[row][column+row]
                row++
            }
            column++
        }

        //Starting from the first element of the second row and moving down
        var row = 1

        while (row < input.size) { //This will skip the first row, as intended, as it was already counted
            diagonalStrings2.add(input[row][0].toString())

            column = 1

            while (column < input[0].length && row + column < input.size) {
                diagonalStrings2[row-1] = diagonalStrings2[row-1] + input[row+column][column]
                column++
            }
            row++
        }

//        println(diagonalStrings)
//        println(diagonalStrings2)

        for (line in diagonalStrings) {
            countDiagonal += countXMASinString(line) //Searching for horizontal occurrences (forward and reverse)
        }

        for (line in diagonalStrings2) {
            countDiagonal += countXMASinString(line) //Searching for horizontal occurrences (forward and reverse)
        }

        println(countHorizontal)
        println(countVertical)
        println(countDiagonal)
        globalCount = countHorizontal + countVertical + countDiagonal

        return globalCount
    }





    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput: List<String> = readInput("Day04_test")
    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day04")
//    part1(input).println()
//    part2(input).println()

}

// AFK
// BGL
// CH
// D
// I
// EJ