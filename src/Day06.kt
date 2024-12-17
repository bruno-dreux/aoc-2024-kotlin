fun main() {
    val emptySpaceChar: Char = '.'
    val occupiedSpaceChar: Char = '#'
    val startingPositionChar: Char = '^'
    val passedThroughChar: Char = 'X'
    val newObstacleChar: Char = 'O'

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

    fun turnRightAndDetectLoop(currentTurn: List<String>, previousTurns: MutableList<List<String>>): Pair<String,MutableList<List<String>>> { //currentTurn is a list with currentRow, currentColumn, currentDirection
        if (currentTurn in previousTurns) {
            return Pair("loop",previousTurns)
        }
        previousTurns.add(currentTurn)
        return Pair(turnRight(currentTurn[2]), previousTurns)
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
        val originalLabMap: MutableList<MutableList<Char>> = input.map { it.toMutableList() }.toMutableList()
        var labMap = originalLabMap.map { it.toMutableList() }.toMutableList()
        printLab(labMap)


        var currentRow: Int = -1
        var currentColumn: Int = -1
        var startingRow: Int = -1
        var startingColumn: Int = -1

        var obstructionPossibilitiesCount: Int = 0

        //Find the starting position
        for (row in labMap.indices) {
            for (column in labMap[row].indices) {
                if (labMap[row][column] == startingPositionChar) {
                    startingRow = row
                    startingColumn = column
                    break
                }
            }
        }

        for (row in labMap.indices) {
            for (column in labMap[row].indices) {
                if (labMap[row][column] == emptySpaceChar) {
                    labMap[row][column] = newObstacleChar
//                    printLab(labMap)

                    var currentDirection: String = "up"

                    labMap[startingRow][startingColumn] = passedThroughChar
                    currentRow = startingRow
                    currentColumn = startingColumn

                    val previousTurns: MutableList<List<String>> = mutableListOf()

                    while (currentRow >= 0 && currentRow < labMap.size && currentColumn >= 0 && currentColumn < labMap[0].size && currentDirection != "loop") { //In bounds of the map
                        when (labMap[currentRow][currentColumn]) {
                            passedThroughChar, emptySpaceChar -> {
                                labMap[currentRow][currentColumn] = passedThroughChar
                                val move = moveInDirection(currentRow, currentColumn, currentDirection, 1)
                                currentRow = move.first
                                currentColumn = move.second
                            }

                            occupiedSpaceChar, newObstacleChar -> {
                                val move = moveInDirection(currentRow, currentColumn, currentDirection, -1)
                                currentRow = move.first
                                currentColumn = move.second
                                val currentTurn: MutableList<String> = mutableListOf()
                                currentTurn.add(currentRow.toString())
                                currentTurn.add(currentColumn.toString())
                                currentTurn.add(currentDirection)
                                val executedTurn = turnRightAndDetectLoop(currentTurn, previousTurns)
                                currentDirection = executedTurn.first
                                previousTurns.add(currentTurn)
                            }
                        }
                    }
                    labMap = originalLabMap.map { it.toMutableList() }.toMutableList()
                    if (currentDirection == "loop") {
                        obstructionPossibilitiesCount++
                        println(obstructionPossibilitiesCount)
                    }
                }
            }
        }
        return obstructionPossibilitiesCount
    }

    val testInput = readInput("Day06_test")
//    part1(testInput).println()
//    part2(testInput).println()

    val input = readInput("Day06")
//    part1(input).println()
    part2(input).println()

}