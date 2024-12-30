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



    fun countCornersForPosition(map: MutableList<MutableList<Char>>,row: Int, column: Int): Int {
        //Obs: this function is based on this insight: https://www.reddit.com/r/adventofcode/comments/1hcf16m/2024_day_12_everyone_must_be_hating_today_so_here/
        val currentValue = map[row][column]
        val neighbors: MutableMap<String,Int> = mutableMapOf()
        neighbors.put("left",0)
        neighbors.put("right",0)
        neighbors.put("up",0)
        neighbors.put("down",0)
        neighbors.put("topleft",0)
        neighbors.put("topright",0)
        neighbors.put("bottomleft",0)
        neighbors.put("bottomright",0)
        if(isInBounds(map,row,column-1) && map[row][column-1] == currentValue) neighbors.put("left",1)
        if(isInBounds(map,row,column+1) && map[row][column+1] == currentValue) neighbors.put("right",1)
        if(isInBounds(map,row-1,column) && map[row-1][column] == currentValue) neighbors.put("up",1)
        if(isInBounds(map,row+1,column) && map[row+1][column] == currentValue) neighbors.put("down",1)
        if(isInBounds(map,row-1,column-1) && map[row-1][column-1] == currentValue) neighbors.put("topleft",1)
        if(isInBounds(map,row-1,column+1) && map[row-1][column+1] == currentValue) neighbors.put("topright",1)
        if(isInBounds(map,row+1,column-1) && map[row+1][column-1] == currentValue) neighbors.put("bottomleft",1)
        if(isInBounds(map,row+1,column+1) && map[row+1][column+1] == currentValue) neighbors.put("bottomright",1)
        val sideNeighbors =
            (neighbors["left"] ?: 0) +
                    (neighbors["right"] ?: 0) +
                    (neighbors["up"] ?: 0) +
                    (neighbors["down"] ?: 0)

        if(sideNeighbors == 0) { //No neighbors, 4 corners
            return 4
        }
        else if (sideNeighbors == 1) { //One of the four sides is 1
            return 2
        }
        else if (sideNeighbors == 2) { //Two of the four sides are 1
            if (neighbors["left"] == 1 && neighbors["right"] == 1) { //Opposing corners case 1
                return 0
            }
            else if (neighbors["up"] == 1 && neighbors["down"] == 1) { //Opposing corners case 2
                return 0
            }
            else if(neighbors["topleft"] == 1 && neighbors["up"] == 1 && neighbors["left"] == 1) {
                return 1
            }
            else if(neighbors["topright"] == 1 && neighbors["up"] == 1 && neighbors["right"] == 1) {
                return 1
            }
            else if(neighbors["bottomleft"] == 1 && neighbors["down"] == 1 && neighbors["left"] == 1) {
                return 1
            }
            else if(neighbors["bottomright"] == 1 && neighbors["down"] == 1 && neighbors["right"] == 1) {
                return 1
            }
            else {
                return 2
            }
        }
        else if (sideNeighbors == 3) {
            if (neighbors["up"] == 0) {
                return -((neighbors["bottomleft"] ?: 0) + (neighbors["bottomright"] ?: 0) -2) //Small magic to return the right number of corners
            }
            else if (neighbors["down"] == 0) {
                return -((neighbors["topleft"] ?: 0) + (neighbors["topright"] ?: 0) -2)
            }
            else if (neighbors["left"] == 0) {
                return -((neighbors["topright"] ?: 0) + (neighbors["bottomright"] ?: 0) -2)
            }
            else if (neighbors["right"] == 0) {
                return -((neighbors["topleft"] ?: 0) + (neighbors["bottomleft"] ?: 0) -2)
            }
        }
        else if (sideNeighbors == 4) {
            return -((neighbors["topleft"] ?: 0) + (neighbors["bottomleft"] ?: 0) + (neighbors["bottomright"] ?: 0) + (neighbors["topright"] ?: 0) -4)
        }
        return -1 //This should never be reached
    }

    fun calculateSides(map: MutableList<MutableList<Char>>, setSimilarElements: MutableSet<Pair<Int,Int>>): Int {
        if (setSimilarElements.isEmpty()) return 0
        var sides: Int = 0
        for (currentElement in setSimilarElements) {
            sides += countCornersForPosition(map,currentElement.first,currentElement.second)
//            println("Counting corners for position ${currentElement.first}, ${currentElement.second}: ${countCornersForPosition(map,currentElement.first,currentElement.second)}")
        }
        return sides
    }


    fun part2(input: List<String>): Int {
        val map = processInput(input)
        println(map)

        val mapReached: MutableList<MutableList<Int>> = mutableListOf()  //Map with the positions already reached
        val listSides: MutableList<Int> = mutableListOf()
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

                    val similarElementsSides = calculateSides(map,setSimilarElements)

                    for (element in setSimilarElements) {
                        mapReached[element.first][element.second] = 1
                    }
                    listAreas.add(setSimilarElements.size)
                    listSides.add(similarElementsSides)
                    listPrices.add(setSimilarElements.size*similarElementsSides)
                }
            }
        }

        println(listSides)

        return listPrices.sum()
    }



    // Or read a large test input from the file:
//    val testInput = readInput("Day12_test")
//    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the file.
    val input = readInput("Day12")
//    part1(input).println()
    part2(input).println()
}