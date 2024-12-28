fun main() {

    fun processInput(input: List<String>): MutableList<MutableList<Char>> {
        val output: MutableList<MutableList<Char>> = mutableListOf()
        for (row in input) {
            val outputRow: MutableList<Char> = mutableListOf()
            for (char in row) {
                outputRow.add(char)
            }
            output.add(outputRow)
        }
        return output
    }

    fun isInBounds(map: MutableList<MutableList<Char>>,row: Int, column: Int): Boolean {
        return row >= 0 && row < map.size && column >= 0 && column < map[row].size
    }

    fun checkDifferentAndAddEdge(map: MutableList<MutableList<Char>>, currentRow: Int, currentColumn: Int, rowOffset: Int, columnOffset: Int): Int {
        if(!isInBounds(map, currentRow + rowOffset, currentColumn + columnOffset)) {
            return 1
        }
        else if(map[currentRow][currentColumn] != map[currentRow+rowOffset][currentColumn + columnOffset]) {
            return 1
        }
        else {
            return 0
        }
    }

    fun calculatePerimeterForPosition(map: MutableList<MutableList<Char>>, currentRow: Int, currentColumn: Int): Int {
        var sumOfEdges: Int = 0
        sumOfEdges += checkDifferentAndAddEdge(map,currentRow,currentColumn,0,-1) //left
        sumOfEdges += checkDifferentAndAddEdge(map,currentRow,currentColumn,-1,0) //up
        sumOfEdges += checkDifferentAndAddEdge(map,currentRow,currentColumn,0,1) //right
        sumOfEdges += checkDifferentAndAddEdge(map,currentRow,currentColumn,1,0) //down
        return sumOfEdges
    }

    fun part1(input: List<String>): Int {
        val map = processInput(input)
        println(map)

        val mapReached: MutableList<MutableList<Int>> = mutableListOf()

        for (row in map.indices) { //Map with the positions already reached
            val newRow: MutableList<Int> = mutableListOf()
            for (column in map[row].indices) {
                newRow.add(0)
            }
            mapReached.add(newRow)
        }

        //Idea: go through the map. For each value, if it was not reached yet, look for all similar elements and create a set with all points that
        // have the similar value (and insert in the mapReached). Using that set, calculate the area and perimeter. The area will be simply the count of elements, and the perimeter will need to
        // use the "calculatePerimeterForPosition" function above. Area and perimeter should be stored somewhere (in two lists) so they can be multiplied later.

        return 0
    }


    fun part2(input: List<String>): Int {
        return 0
    }



    // Or read a large test input from the file:
    val testInput = readInput("Day12_test")
    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the file.
//    val input = readInput("Day12")
//    part1(input).println()
//    part2(input).println()
}