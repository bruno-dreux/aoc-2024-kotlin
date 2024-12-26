fun main() {
    val amountSteps = 75
    val listSizeToBreakdown = 1000000
    val amountStepsFirstPass = 39
    val amountStepsSecondPass = 75 - amountStepsFirstPass

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

    fun blinkOneElement(element: ULong): List<ULong> {
        var newList: MutableList<ULong> = mutableListOf()
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
        return newList.toList()
    }

    fun breakdownAndBlinkNSteps(list: List<ULong>, steps: Int, currentStep: Int): Int {
//        println("Called function with current step $currentStep and list $list")
        println("Current step: $currentStep")
        var totalSize: Int = 0
        var currentList: List<ULong> = list
        if(currentStep >= steps) {
            totalSize += currentList.size
            return totalSize
        }
        if(currentList.size > listSizeToBreakdown) {
            val leftList = currentList.subList(0, currentList.size/2)
            val rightList = currentList.subList(currentList.size/2, currentList.size)
            println("Breaking down in 2 lists")
            totalSize += breakdownAndBlinkNSteps(leftList,steps,currentStep) + breakdownAndBlinkNSteps(rightList, steps,currentStep)
        }
        else {
            currentList = blink(currentList)
            totalSize += breakdownAndBlinkNSteps(currentList,steps,currentStep+1)
//            println(currentList)
        }

        return totalSize
    }

    fun blinkNSteps(list: List<ULong>, steps: Int): List<ULong> {
//        println("Blink $steps steps.")
        var currentList: List<ULong> = list
        for(i in 0 until steps) {
//            println("Current step: $i")
            currentList = blink(currentList)
        }

        return currentList
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


    fun part2(input: List<String>): ULong {
        val list = readInput(input)
        println("Initial list: ")
        println(list)

        val listAfterFirstPass = blinkNSteps(list,amountStepsFirstPass)
        val mapSecondPass: MutableMap<ULong, ULong> = mutableMapOf()
        var cumulativeSum: ULong = 0.toULong()

        for (element in listAfterFirstPass) {
            if (mapSecondPass.containsKey(element)) {
                cumulativeSum += mapSecondPass.getValue(element)
            }
            else{
                println("Value $element not found in map. Blinking...")
                val listSecondPassElement = blinkNSteps(listOf(element), amountStepsSecondPass)
                mapSecondPass.put(element,listSecondPassElement.size.toULong())
                cumulativeSum += listSecondPassElement.size.toULong()
            }
        }

        return cumulativeSum

    }

    fun part2efficient(input: List<String>): ULong {
        val list = readInput(input)
        println("Initial list: ")
        println(list)

        var map: MutableMap<ULong, ULong> = mutableMapOf()

        for (element in list) {
            map[element] = (map[element] ?: 0u) + 1u
        }

        println(map)

        for (i in 1..amountSteps) {
            println("Step $i")
            var newMap: MutableMap<ULong, ULong> = mutableMapOf()

            for (element in map.keys) {
                val countElement = map[element]
                val listBlinks = blinkOneElement(element)
                for (blinkResult in listBlinks) {
                    newMap[blinkResult] = (newMap[blinkResult] ?: 0u) + 1u*countElement!!
                }
            }
            map = newMap

        }

        return map.values.sum()

    }



    // Or read a large test input from the file:
//    val testInput = readInput("Day11_test")
//    part1(testInput).println()
//    part2(testInput).println()

    // Read the input from the file.
    val input = readInput("Day11")
//    part1(input).println()
//    part2(input).println()
    part2efficient(input).println()

}