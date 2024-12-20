fun main() {
    val fileFlag = 1
    val spaceFlag = -1
    val emptySpaceChar = "."


    fun createUncompactedDisk(compactedDisk: String): List<String> {
        var uncompactedDisk: MutableList<String> = mutableListOf()
        var flag = fileFlag
        var id = 0

        for (digit in compactedDisk) {
            if(flag == fileFlag) {
                for(i in 0 until digit.digitToInt()){
                    uncompactedDisk.add(id.toString())
                }
                id++
            }
            else if(flag == spaceFlag) {
                for(i in 0 until digit.digitToInt()){
                    uncompactedDisk.add(emptySpaceChar)
                }
            }
            flag *= -1 //Alternate between fileFlag and spaceFlag
        }

        return uncompactedDisk.toList()
    }

    fun createCompactedDisk(uncompactedDisk: List<String>): List<String> {
        val compactedDisk: MutableList<String> = uncompactedDisk.toMutableList()
        var lastNonEmptySpaceIndex = compactedDisk.size - 1

        for(i in compactedDisk.indices){ //i goes through the list from left to right
                while (compactedDisk[i] == emptySpaceChar && lastNonEmptySpaceIndex > i) {
                    if(compactedDisk[lastNonEmptySpaceIndex] != emptySpaceChar){
                        compactedDisk[i] = compactedDisk[lastNonEmptySpaceIndex]
                        compactedDisk[lastNonEmptySpaceIndex] = emptySpaceChar
                    }
                    lastNonEmptySpaceIndex--
                }
        }

        return compactedDisk.toList()

    }

    fun calculateChecksum(compactedDisk: List<String>): ULong {
        var checksum = 0.toULong()

        for (i in compactedDisk.indices){
            if(compactedDisk[i] != emptySpaceChar){
                checksum += i.toULong()*compactedDisk[i].toULong()
            }
        }

        return checksum
    }

    fun part1(input: String): ULong {
        println(input)
        val uncompactedDisk = createUncompactedDisk(input)
        println(uncompactedDisk)

        val compactedDisk = createCompactedDisk(uncompactedDisk)
        println(compactedDisk)

        return calculateChecksum(compactedDisk)
    }


    fun part2(input: String): Int {
        return 0
    }



    // Or read a large test input from the `src/Day09_test.txt` file:
//    val testInput = readInput("Day09_test")
//    part1(testInput[0]).println()
//    part2(testInput[0]).println()

    // Read the input from the `src/Day09.txt` file.
    val input = readInput("Day09")
    part1(input[0]).println()
//    part2(input[0]).println()
}