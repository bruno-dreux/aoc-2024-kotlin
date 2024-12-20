fun main() {
    val emptySpaceChar: Char = '.'
    val antinodeChar: Char = '#'

    fun printMap(map: MutableList<MutableList<Char>>) {
        println("Printing map: ")
        for (row in map) {
            println(row)
        }
    }

    fun getAntinodePositions(antenna1: Pair<Int,Int>, antenna2: Pair<Int,Int>): List<Pair<Int,Int>> {
        val antinodePositions: MutableList<Pair<Int,Int>> = mutableListOf()

        val deltaRow = antenna2.first - antenna1.first
        val deltaColumn = antenna2.second - antenna1.second

        antinodePositions.add(Pair(antenna1.first - deltaRow,antenna1.second - deltaColumn))
        antinodePositions.add(Pair(antenna2.first + deltaRow,antenna2.second + deltaColumn))

        return antinodePositions

    }

    fun checkInBounds(map: MutableList<MutableList<Char>>, row: Int, column: Int): Boolean {
        return row >= 0 && row < map.size && column >= 0 && column < map[0].size
    }

    fun getAntinodePositionsWithRepetition(map: MutableList<MutableList<Char>>,antenna1: Pair<Int,Int>, antenna2: Pair<Int,Int>): List<Pair<Int,Int>> {
        val antinodePositions: MutableList<Pair<Int,Int>> = mutableListOf()

        val deltaRow = antenna2.first - antenna1.first
        val deltaColumn = antenna2.second - antenna1.second

        var step: Int = 1

        var inBounds: Boolean = checkInBounds(map,antenna1.first - deltaRow * step,antenna1.second - deltaColumn * step)

        while(inBounds) {
            antinodePositions.add(Pair(antenna1.first - deltaRow * step,antenna1.second - deltaColumn * step))
            step++
            inBounds = checkInBounds(map,antenna1.first - deltaRow * step,antenna1.second - deltaColumn * step)
        }

        step = 1
        inBounds = checkInBounds(map,antenna2.first + deltaRow * step,antenna2.second + deltaColumn * step)

        while(inBounds) {
            antinodePositions.add(Pair(antenna2.first + deltaRow * step,antenna2.second + deltaColumn * step))
            step++
            inBounds = checkInBounds(map,antenna2.first + deltaRow * step,antenna2.second + deltaColumn * step)
        }

        return antinodePositions

    }



    fun part1(input: List<String>): Int {
        val map: MutableList<MutableList<Char>> = input.map { it.toMutableList() }.toMutableList()
        val mapOfAntinodes: MutableList<MutableList<Char>> = input.map { it.toMutableList() }.toMutableList()
        printMap(map)

        for (row in map.indices) {
            for (column in map[row].indices) {
                if(map[row][column] != emptySpaceChar) { //current position is an antenna
                    val currentAntenaChar = map[row][column]
                    var listOfSimilarAntennas: MutableList<Pair<Int, Int>> = mutableListOf()
                    println("Current antenna: $currentAntenaChar, at row: $row, column: $column")
                    for (innerRow in row until map.size) {
                        for (innerColumn in map[innerRow].indices) {
                            if (map[innerRow][innerColumn] == currentAntenaChar) {
                                listOfSimilarAntennas.add(Pair(innerRow, innerColumn))
                            }
                        }
                    }
                    listOfSimilarAntennas = listOfSimilarAntennas.drop(1).toMutableList() //Gambiarra to remove the first element, as it is the current antenna
                    println("Other positions with same antenna: $listOfSimilarAntennas")

                    for (similarAntenna in listOfSimilarAntennas) {
                        val potentialAntinodes = getAntinodePositions(Pair(row,column), Pair(similarAntenna.first,similarAntenna.second))
                        for (potentialAntinode in potentialAntinodes) {
                            if(checkInBounds(map,potentialAntinode.first, potentialAntinode.second)) {
                                mapOfAntinodes[potentialAntinode.first][potentialAntinode.second] = antinodeChar
                            }
                        }
                    }

                }
            }
        }

        printMap(mapOfAntinodes)
        return mapOfAntinodes.flatMap { it }.count { it == antinodeChar }
    }


    fun part2(input: List<String>): Int {
        val map: MutableList<MutableList<Char>> = input.map { it.toMutableList() }.toMutableList()
        val mapOfAntinodes: MutableList<MutableList<Char>> = input.map { it.toMutableList() }.toMutableList()
        printMap(map)

        for (row in map.indices) {
            for (column in map[row].indices) {
                if(map[row][column] != emptySpaceChar) { //current position is an antenna
                    val currentAntenaChar = map[row][column]
                    var listOfSimilarAntennas: MutableList<Pair<Int, Int>> = mutableListOf()
                    println("Current antenna: $currentAntenaChar, at row: $row, column: $column")
                    for (innerRow in row until map.size) {
                        for (innerColumn in map[innerRow].indices) {
                            if (map[innerRow][innerColumn] == currentAntenaChar) {
                                listOfSimilarAntennas.add(Pair(innerRow, innerColumn))
                            }
                        }
                    }
                    if(listOfSimilarAntennas.size > 1) {
                        for (similarAntenna in listOfSimilarAntennas) {
                            mapOfAntinodes[similarAntenna.first][similarAntenna.second] = antinodeChar
                        }

                    }
                    listOfSimilarAntennas = listOfSimilarAntennas.drop(1).toMutableList() //Gambiarra to remove the first element, as it is the current antenna
                    println("Other positions with same antenna: $listOfSimilarAntennas")

                    for (similarAntenna in listOfSimilarAntennas) {
                        val potentialAntinodes = getAntinodePositionsWithRepetition(map,Pair(row,column), Pair(similarAntenna.first,similarAntenna.second))
                        for (potentialAntinode in potentialAntinodes) {
                            mapOfAntinodes[potentialAntinode.first][potentialAntinode.second] = antinodeChar
                        }
                    }

                }
            }
        }

        printMap(mapOfAntinodes)
        return mapOfAntinodes.flatMap { it }.count { it == antinodeChar }
    }



    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
//    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the `src/Day08.txt` file.
    val input = readInput("Day08")
//    part1(input).println()
    part2(input).println()
}