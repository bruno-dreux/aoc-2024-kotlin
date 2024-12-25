fun main() {
    val amountSteps = 25

    fun readInput(input: List<String>): List<ULong> {
        var parts = input[0].split(Regex("\\s+")) // Split by one or more spaces
        return parts.map {it.toULong()}
    }

    fun blink(list: List<ULong>): List<ULong> {
        var newList: MutableList<ULong> = mutableListOf()
        for (element in list) {
//            println(element)
            val elementString = element.toString()
            if (element == 0.toULong()) { //case 0
                newList.add(1.toULong())
            }
            else if (elementString.length %2 == 0) { //case even number of digits
                val leftPart = elementString.substring(0, elementString.length/2)
                val rightPart = elementString.substring(elementString.length/2, elementString.length)
//                println("left part: $leftPart")
//                println("right part: $rightPart")
                newList.add(leftPart.toULong())
                newList.add(rightPart.toULong())
            }
            else{
                newList.add(element*2024.toULong())
            }
        }
        return newList.toList()
    }

    fun part1(input: List<String>): Int {
        var list = readInput(input)
        println("Initial list: ")
        println(list)


        for (i in 1..amountSteps) {
            println("After $i blinks: ")
            list = blink(list)
            println(list)
        }

        return list.size
    }


    fun part2(input: List<String>): Int {
        return 0
    }



    // Or read a large test input from the file:
//    val testInput = readInput("Day11_test")
//    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the file.
    val input = readInput("Day11")
    part1(input).println()
//    part2(input).println()
}