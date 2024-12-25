fun main() {

    fun isInBounds (map: List<List<Int>>, row: Int, column: Int): Boolean {
        return row >=0 && row < map.size && column >=0 && column < map[row].size
    }

    fun getEndingPoints(map: List<List<Int>>, row: Int, column: Int, endingPositions: MutableList<Pair<Int,Int>>) {
        val currentNumber = map[row][column]

        if (currentNumber == 9){
            endingPositions.add(Pair(row, column))
            return
        }

        if(isInBounds(map, row -1, column) && map[row-1][column] == currentNumber+1) { //Position UP
            getEndingPoints(map, row-1, column, endingPositions)
        }

        if(isInBounds(map, row, column-1) && map[row][column-1] == currentNumber+1) { //Position LEFT
            getEndingPoints(map, row, column-1, endingPositions)
        }

        if(isInBounds(map, row, column+1) && map[row][column+1] == currentNumber+1) { //Position RIGHT
            getEndingPoints(map, row, column+1, endingPositions)
        }

        if(isInBounds(map, row +1, column) && map[row+1][column] == currentNumber+1) { //Position DOWN
            getEndingPoints(map, row+1, column, endingPositions)
        }

        return
    }

    fun countSequencesFromZero(map: List<List<Int>>, row: Int, column: Int): Int {
        if (map[row][column] == 0) {
            var listOfEndingPointsPositions: MutableList<Pair<Int,Int>> = mutableListOf()
            getEndingPoints(map, row, column, listOfEndingPointsPositions)

            var deduplicatedPositions: MutableList<Pair<Int,Int>> = mutableListOf()

            for (position in listOfEndingPointsPositions) {
                if (!(position in deduplicatedPositions)) {
                    deduplicatedPositions.add(position)
                }
            }
            return deduplicatedPositions.count()
        }
        return 0
    }

    fun countDistinctSequencesFromZero(map: List<List<Int>>, row: Int, column: Int): Int {
        if (map[row][column] == 0) {
            var listOfEndingPointsPositions: MutableList<Pair<Int,Int>> = mutableListOf()
            getEndingPoints(map, row, column, listOfEndingPointsPositions)

            return listOfEndingPointsPositions.count()
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

        var sumOfTrailheads: Int = 0

        for (row in map.indices) {
            for (column in map[row].indices) {
                sumOfTrailheads += countSequencesFromZero(map,row,column)
            }
        }

        return sumOfTrailheads
    }


    fun part2(input: List<String>): Int {
        val map = readMap(input)

        var sumOfTrailheads: Int = 0

        for (row in map.indices) {
            for (column in map[row].indices) {
                sumOfTrailheads += countDistinctSequencesFromZero(map,row,column)
            }
        }

        return sumOfTrailheads
    }



    // Or read a large test input from the file:
//    val testInput = readInput("Day10_test")
//    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the file.
    val input = readInput("Day10")
//    part1(input).println()
    part2(input).println()
}