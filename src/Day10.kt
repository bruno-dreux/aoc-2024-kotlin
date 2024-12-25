fun main() {

    fun isInBounds (map: List<List<Int>>, row: Int, column: Int): Boolean {
        return row >=0 && row < map.size && column >=0 && column < map[row].size
    }

    fun countSequencesFromPosition(map: List<List<Int>>, row: Int, column: Int): Int {
        val currentNumber = map[row][column]
        var amountOfSequences = 0

        if (currentNumber == 9) return 1

        if(isInBounds(map, row -1, column) && map[row-1][column] == currentNumber+1) { //Position UP
            amountOfSequences += countSequencesFromPosition(map, row-1, column)
        }

        if(isInBounds(map, row, column-1) && map[row][column-1] == currentNumber+1) { //Position LEFT
            amountOfSequences += countSequencesFromPosition(map, row, column-1)
        }

        if(isInBounds(map, row, column+1) && map[row][column+1] == currentNumber+1) { //Position RIGHT
            amountOfSequences += countSequencesFromPosition(map, row, column+1)
        }

        if(isInBounds(map, row +1, column) && map[row+1][column] == currentNumber+1) { //Position DOWN
            amountOfSequences += countSequencesFromPosition(map, row+1, column)
        }

        return amountOfSequences
    }

    fun countSequencesFromZero(map: List<List<Int>>, row: Int, column: Int): Int {
        if (map[row][column] == 0) {
            return countSequencesFromPosition(map, row, column)
        }
        return 0
    }

    fun readMap(input: List<String>): List<List<Int>> {
        val map: MutableList<MutableList<Int>> = mutableListOf()
        for (row in input) {
            val outputRow: MutableList<Int> = mutableListOf()
            for (column in row) {
                outputRow.add(column.digitToInt())
            }
            map.add(outputRow)
        }
        return map.toList()
    }

    fun part1(input: List<String>): Int {
        val map = readMap(input)

        println(countSequencesFromZero(map, 0,0))
        //TO DO: understand why the number is too high and fix it

        return 0
    }


    fun part2(input: List<String>): Int {
        return 0
    }



    // Or read a large test input from the file:
    val testInput = readInput("Day10_test")
    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the file.
//    val input = readInput("Day10")
//    part1(input).println()
//    part2(input).println()
}