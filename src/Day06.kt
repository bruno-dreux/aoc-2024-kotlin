fun main() {
    val emptySpaceChar: Char = '.'
    val occupiedSpaceChar: Char = '#'
    val startingPositionChar: Char = '^'
    val passedThroughChar: Char = 'X'

    fun moveInDirection(currentRow: Int, currentColumn: Int, currentDirection: String, step: Int): Pair<Int, Int> {
        when (currentDirection) {
            "up" -> return Pair(currentRow-step, currentColumn)
            "right" -> return Pair(currentRow, currentColumn+step)
            "down" -> return Pair(currentRow+step, currentColumn)
            "left" -> return Pair(currentRow, currentColumn-step)
        }
        return Pair(currentRow, currentColumn)
    }

    fun turnRight(currentDirection: String): String {
        when (currentDirection) {
            "up" -> return "right"
            "right" -> return "down"
            "down" -> return "left"
            "left" -> return "up"
        }
        return "wrong direction"
    }

    fun printLab(labMap: MutableList<MutableList<Char>>) {
        println("Printing map: ")
        for (row in labMap) {
            println(row)
        }
    }


    fun part1(input: List<String>): Int {
        val labMap: MutableList<MutableList<Char>> = input.map { it.toMutableList() }.toMutableList()
        printLab(labMap)

        var currentDirection: String = "up"
        var currentRow: Int = -1
        var currentColumn: Int = -1

        //Find the starting position
        for (row in labMap.indices) {
            for (column in labMap[row].indices) {
                if (labMap[row][column] == startingPositionChar) {
                    currentRow = row
                    currentColumn = column
                    break
                }
            }
        }
        labMap[currentRow][currentColumn] = passedThroughChar
//        println(currentRow)
//        println(currentColumn)

        while (currentRow >= 0 && currentRow < labMap.size && currentColumn >= 0 && currentColumn < labMap[0].size) { //In bounds of the map
            when (labMap[currentRow][currentColumn]) {
                passedThroughChar, emptySpaceChar -> {
                    labMap[currentRow][currentColumn] = passedThroughChar
                    val move = moveInDirection(currentRow, currentColumn, currentDirection, 1)
                    currentRow = move.first
                    currentColumn = move.second
                }
                occupiedSpaceChar -> {
                    val move = moveInDirection(currentRow, currentColumn, currentDirection, -1)
                    currentRow = move.first
                    currentColumn = move.second
                    currentDirection = turnRight(currentDirection)
                }
            }

//            printLab(labMap)

        }


        return labMap.flatten().count { it == passedThroughChar }
    }


    fun part2(input: List<String>): Int {
        return 0
    }



    val testInput = readInput("Day06_test")
    part1(testInput).println()
//    part2(testInput).println()

    val input = readInput("Day06")
    part1(input).println()
//    part2(input).println()

}