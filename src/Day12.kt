import kotlin.math.abs

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

    fun checkConditionsForSimilarity(map: MutableList<MutableList<Char>>, setSimilarElements: MutableSet<Pair<Int,Int>>, currentValue: Char, row: Int, column: Int): Boolean {
        return isInBounds(map, row, column) && map[row][column] == currentValue && !setSimilarElements.contains(Pair(row,column))
    }

    fun findSimilarElements(map: MutableList<MutableList<Char>>, set: MutableSet<Pair<Int,Int>>, currentValue: Char, currentRow: Int, currentColumn: Int): MutableSet<Pair<Int,Int>> {
        var setSimilarElements = set
        setSimilarElements.add(Pair(currentRow,currentColumn))
        if (checkConditionsForSimilarity(map,setSimilarElements,currentValue,currentRow,currentColumn-1)) { //Case LEFT
            setSimilarElements.add(Pair(currentRow,currentColumn-1))
            setSimilarElements = findSimilarElements(map,setSimilarElements, currentValue, currentRow,currentColumn-1)
        }
        if (checkConditionsForSimilarity(map,setSimilarElements,currentValue,currentRow-1,currentColumn)) { //Case UP
            setSimilarElements.add(Pair(currentRow-1,currentColumn))
            setSimilarElements = findSimilarElements(map,setSimilarElements, currentValue,currentRow-1,currentColumn)
        }
        if (checkConditionsForSimilarity(map,setSimilarElements,currentValue,currentRow,currentColumn+1)) { //Case RIGHT
            setSimilarElements.add(Pair(currentRow,currentColumn+1))
            setSimilarElements = findSimilarElements(map,setSimilarElements, currentValue, currentRow,currentColumn+1)
        }
        if (checkConditionsForSimilarity(map,setSimilarElements,currentValue,currentRow+1,currentColumn)) { //Case DOWN
            setSimilarElements.add(Pair(currentRow+1,currentColumn))
            setSimilarElements = findSimilarElements(map,setSimilarElements, currentValue, currentRow+1,currentColumn)
        }

        return setSimilarElements
    }

    fun part1(input: List<String>): Int {
        val map = processInput(input)
        println(map)

        val mapReached: MutableList<MutableList<Int>> = mutableListOf()  //Map with the positions already reached
        val listPerimeters: MutableList<Int> = mutableListOf()
        val listAreas: MutableList<Int> = mutableListOf()
        val listPrices: MutableList<Int> = mutableListOf()

        for (row in map.indices) {
            val newRow: MutableList<Int> = mutableListOf()
            for (column in map[row].indices) {
                newRow.add(0)
            }
            mapReached.add(newRow)
        }

        for (row in map.indices) {
            for (column in map[row].indices) {
                if(mapReached[row][column] != 1) {
                    var setSimilarElements: MutableSet<Pair<Int,Int>> = mutableSetOf()
                    setSimilarElements = findSimilarElements(map,setSimilarElements,map[row][column],row,column)
                    println(setSimilarElements)

                    var similarElementsPerimeter: Int = 0
                    for (element in setSimilarElements) {
                        mapReached[element.first][element.second] = 1
                        similarElementsPerimeter += calculatePerimeterForPosition(map,element.first,element.second)
                    }
                    listAreas.add(setSimilarElements.size)
                    listPerimeters.add(similarElementsPerimeter)
                    listPrices.add(setSimilarElements.size*similarElementsPerimeter)
                }
            }
        }

        return listPrices.sum()
    }


    fun part2(input: List<String>): Int {
        val map = processInput(input)
        println(map)

        val mapReached: MutableList<MutableList<Int>> = mutableListOf()  //Map with the positions already reached
        val listPerimeters: MutableList<Int> = mutableListOf()
        val listAreas: MutableList<Int> = mutableListOf()
        val listPrices: MutableList<Int> = mutableListOf()

        for (row in map.indices) {
            val newRow: MutableList<Int> = mutableListOf()
            for (column in map[row].indices) {
                newRow.add(0)
            }
            mapReached.add(newRow)
        }

        for (row in map.indices) {
            for (column in map[row].indices) {
                if(mapReached[row][column] != 1) {
                    var setSimilarElements: MutableSet<Pair<Int,Int>> = mutableSetOf()
                    setSimilarElements = findSimilarElements(map,setSimilarElements,map[row][column],row,column)
                    println("${map[row][column]}: $setSimilarElements")

                    var similarElementsSides: Int = 4
                    //TODO: Calculate the similar elements sides from the set

                    for (element in setSimilarElements) {
                        mapReached[element.first][element.second] = 1
                    }
                    listAreas.add(setSimilarElements.size)
                    listPerimeters.add(similarElementsSides)
                    listPrices.add(setSimilarElements.size*similarElementsSides)
                }
            }
        }

        return listPrices.sum()
    }



    // Or read a large test input from the file:
    val testInput = readInput("Day12_test")
//    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the file.
//    val input = readInput("Day12")
//    part1(input).println()
//    part2(input).println()
}