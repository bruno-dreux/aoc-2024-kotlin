fun main() {

    val wallChar: Char = '#'
    val boxChar = 'O'
    val emptyChar = '.'
    val currentPositionChar = '@'

    class Warehouse (val map: MutableList<MutableList<Char>>, val moves: List<Char>) {
        var currentRow: Int = -1
        var currentColumn: Int = -1

        init {
            for (rowIndex in map.indices) {
                for (columnIndex in map[rowIndex].indices) {
                    if (map[rowIndex][columnIndex] == currentPositionChar) {
                        currentRow = rowIndex
                        currentColumn = columnIndex
                        break
                    }
                }
            }
            if (currentRow == -1 && currentColumn == -1) {
                println("Missing initial position of robot in map")
            }
        }

        override fun toString(): String {
            var outputString: String = ""
            outputString += "Map: \n"
            for (row in map) {
                for (item in row) {
                    outputString += item
                }
                outputString += "\n"
            }
            outputString += "\n"
            outputString += "Moves: \n"
            outputString += moves
            outputString += "\nCurrent robot position: $currentRow, $currentColumn"

            return outputString
        }

        fun moveOneStep(moveRow: Int, moveColumn: Int, direction: Char): Boolean {
            var deltaRow: Int = 0
            var deltaColumn: Int = 0

            if(direction == '<') deltaColumn = -1
            else if(direction == '>') deltaColumn = 1
            else if(direction == '^') deltaRow = -1
            else if(direction == 'v') deltaRow = 1
            else println("Wrong direction!")

            val currentChar = map[moveRow][moveColumn]
            val futurePositionChar = map[moveRow+deltaRow][moveColumn+deltaColumn]

            if(futurePositionChar == wallChar) {
                return false
            }
            else if(futurePositionChar == emptyChar) {
                map[moveRow+deltaRow][moveColumn+deltaColumn] = currentChar
                map[moveRow][moveColumn] = emptyChar
                if(currentChar == currentPositionChar) {
                    currentRow = moveRow+deltaRow
                    currentColumn = moveColumn+deltaColumn
                }
                return true
            }
            else if(futurePositionChar == boxChar) {
                if (this.moveOneStep(moveRow+deltaRow,moveColumn+deltaColumn, direction)) {
                    map[moveRow+deltaRow][moveColumn+deltaColumn] = currentChar
                    map[moveRow][moveColumn] = emptyChar
                    if(currentChar == currentPositionChar) {
                        currentRow = moveRow+deltaRow
                        currentColumn = moveColumn+deltaColumn
                    }
                    return true
                }
            }
            return false
        }

        fun move() {
            for(move in moves) {
                moveOneStep(currentRow,currentColumn,move)
//                println(this)
            }
            println(this)
        }

        fun calculateCoordinate(): Int {
            var totalCoordinateScore: Int = 0
            for (rowIndex in map.indices) {
                for (columnIndex in map[rowIndex].indices) {
                    if (map[rowIndex][columnIndex] == boxChar) {
                        totalCoordinateScore += rowIndex * 100 + columnIndex
                    }
                }
            }
            return totalCoordinateScore
        }

    }

    fun processInput(input: List<String>): Warehouse {
        val map: MutableList<MutableList<Char>> = mutableListOf()
        val moves: MutableList<Char> = mutableListOf()

        for (row in input) {
            if (row.isNotEmpty() && row[0] == wallChar) { //Inside the map
                val mapRow: MutableList<Char> = mutableListOf()
                for (item in row) {
                    mapRow.add(item)
                }
                map.add(mapRow)
            }
            else if(row.isNotEmpty()) { //In the moves part
                for (item in row) {
                    moves.add(item)
                }
            }
        }

        return Warehouse(map,moves.toList())
    }


    fun part1(input: List<String>): Int {
        var warehouse = processInput(input)
        println(warehouse)

//        //Test code for move 1 row
//        for (i in 0 until 10) {
//            warehouse.moveOneStep(warehouse.currentRow,warehouse.currentColumn,'>')
//            println(warehouse)
//        }
//        warehouse.moveOneStep(3,4,'v')
//        println(warehouse)
//        warehouse.moveOneStep(3,4,'v')
//        println(warehouse)

        println("Moving robot...")
        warehouse.move()



        return warehouse.calculateCoordinate()
    }


    fun part2(input: List<String>): Int {
        return 0
    }



    // Or read a large test input from the file:
//    val testInput = readInput("Day15_test")
//    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the file.
    val input = readInput("Day15")
    part1(input).println()
//    part2(input).println()
}