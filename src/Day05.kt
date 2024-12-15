fun main() {

    fun processInput(input: List<String>): Pair<List<List<Int>>, List<List<Int>>> {
        val rulesList: MutableList<MutableList<Int>> = mutableListOf()
        val updatesList: MutableList<MutableList<Int>> = mutableListOf()
        var flagInputType: String = "rules"

        for (line in input) {
            if (line.isEmpty()) {
                flagInputType = "updates"
            }
            else if (flagInputType == "rules") {
                //Add line to rulesList
                val parts = line.split(Regex("\\|")) // Split by vertical separator
                val tempList = mutableListOf(parts[0].toInt(), parts[1].toInt())
                rulesList.add(tempList)
            }
            else { //flagInputType == "updates"
                //Add line to updatesList
                val parts = line.split(Regex(",")) // Split by commas
                val tempList: MutableList<Int> = mutableListOf()
                for (part in parts) {
                    tempList.add(part.toInt())
                }
                updatesList.add(tempList)
            }
        }
        return Pair(rulesList.toList(), updatesList.toList())
    }

    fun topologicalSort(rulesList: List<List<Int>>): List<Int> {
        // Step 1: Extract all unique nodes from the rulesList
        val nodes = rulesList.flatten().toSet()

        // Step 2: Build adjacency list and in-degree map
        val adjacencyList = mutableMapOf<Int, MutableList<Int>>()
        val inDegree = mutableMapOf<Int, Int>().withDefault { 0 }

        // Initialize graph structure
        for (node in nodes) {
            adjacencyList[node] = mutableListOf()
            inDegree[node] = 0
        }

        // Populate the graph based on the rules
        for (rule in rulesList) {
            val (smaller, larger) = rule
            adjacencyList[smaller]?.add(larger)
            inDegree[larger] = inDegree.getValue(larger) + 1
        }

        // Step 3: Find all nodes with no incoming edges (in-degree 0)
        val queue = ArrayDeque<Int>()
        for (node in nodes) {
            if (inDegree.getValue(node) == 0) {
                queue.add(node)
            }
        }

        // Step 4: Perform topological sorting
        val result = mutableListOf<Int>()
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            result.add(current)

            // Reduce the in-degree of all neighbors
            for (neighbor in adjacencyList[current] ?: emptyList()) {
                inDegree[neighbor] = inDegree.getValue(neighbor) - 1
                if (inDegree.getValue(neighbor) == 0) {
                    queue.add(neighbor)
                }
            }
        }

//         Step 5: Check if sorting was successful
        if (result.size != nodes.size) {
            throw IllegalArgumentException("Cycle detected: The comparisons are inconsistent.")
        }

        return result
    }

    fun checkUpdate (update: List<Int>, rules: List<List<Int>>): Int {
        for (i in update.indices) {
            var beforeRules: MutableList<Int> = mutableListOf()
            var afterRules: MutableList<Int> = mutableListOf()

            var beforeUpdate: MutableList<Int> = mutableListOf()
            var afterUpdate: MutableList<Int> = mutableListOf()

            if (i-1 >=0) {
                beforeUpdate = update.subList(0,i).toMutableList()
            }
            if (i+1 <=update.lastIndex) {
                afterUpdate = update.subList(i+1,update.size).toMutableList()
            }

            for (rule in rules) {
                if (rule[0] == update[i]) {
                    afterRules.add(rule[1])
                }
                else if (rule[1] == update[i]) {
                    beforeRules.add(rule[0])
                }
            }

            val violation1 = beforeUpdate.intersect(afterRules)
            val violation2 = afterUpdate.intersect(beforeRules)

            if (violation1.isNotEmpty() || violation2.isNotEmpty()) { //list is invalid
                println("List is invalid: $update")
//                println(violation1)
//                println(violation2)
                return 0
            }

//            println("Before Update: $beforeUpdate")
//            println("After Update: $afterUpdate")
//            println("Before Rules: $beforeRules")
//            println("After Rules: $afterRules")

//            println(beforeUpdate)
//            println(afterUpdate)

//            println(beforeRules)
//            println(afterRules)

        }

        val midpoint = update[update.size/2]
        println("List is valid: $update, midpoint is $midpoint")
        return midpoint
    }

    fun swapItemsInUpdate (update: MutableList<Int>, first: Int, second: Int): MutableList<Int> {
        val newList: MutableList<Int> = mutableListOf()
        for (item in update) {
            if(item == first) {
                newList.add(second)
            }
            else if (item == second) {
                newList.add(first)
            }
            else {
                newList.add(item)
            }
        }
        return newList
    }

    fun checkUpdateWithPermutations (update: List<Int>, rules: List<List<Int>>): Int {
        var updateWithPermutations: MutableList<Int> = update.toMutableList()

        for (i in updateWithPermutations.indices) {
            var beforeRules: MutableList<Int> = mutableListOf()
            var afterRules: MutableList<Int> = mutableListOf()

            var beforeUpdate: MutableList<Int> = mutableListOf()
            var afterUpdate: MutableList<Int> = mutableListOf()



            if (i-1 >=0) {
                beforeUpdate = updateWithPermutations.subList(0,i).toMutableList()
            }
            if (i+1 <=update.lastIndex) {
                afterUpdate = updateWithPermutations.subList(i+1,update.size).toMutableList()
            }

            for (rule in rules) {
                if (rule[0] == updateWithPermutations[i]) {
                    afterRules.add(rule[1])
                }
                else if (rule[1] == updateWithPermutations[i]) {
                    beforeRules.add(rule[0])
                }
            }

            val violation1 = beforeUpdate.intersect(afterRules)
            val violation2 = afterUpdate.intersect(beforeRules)

            if (violation1.isNotEmpty() || violation2.isNotEmpty()) { //list is invalid
                for (violation in violation1) {
                    println("Violation 1")
                    println("List before permutation: $updateWithPermutations, $violation, ${updateWithPermutations[i]}")
                    updateWithPermutations = swapItemsInUpdate(updateWithPermutations, violation, updateWithPermutations[i])
                    println("List after permutation: $updateWithPermutations")
                    return(checkUpdateWithPermutations(updateWithPermutations, rules))
                }
                for (violation in violation2) {
                    println("Violation 2")
                    println("List before permutation: $updateWithPermutations, $violation, ${updateWithPermutations[i]}")
                    updateWithPermutations = swapItemsInUpdate(updateWithPermutations, violation, updateWithPermutations[i])
                    println("List after permutation: $updateWithPermutations")
                    return(checkUpdateWithPermutations(updateWithPermutations, rules))
                }
            }

//            println("List is valid: $update, midpoint is $midpoint")
//            println("Before Update: $beforeUpdate")
//            println("After Update: $afterUpdate")
//            println("Before Rules: $beforeRules")
//            println("After Rules: $afterRules")

//            println(beforeUpdate)
//            println(afterUpdate)

//            println(beforeRules)
//            println(afterRules)

        }
        return checkUpdate(updateWithPermutations, rules)

    }

    fun part1(input: List<String>): Int {
        val inputPair = processInput(input)
        val rulesList: List<List<Int>> = inputPair.first
        val updatesList: List<List<Int>> = inputPair.second
        var cumulativeSum = 0

//        println(rulesList)
//        println(updatesList)

        for (update in updatesList) {
            cumulativeSum += checkUpdate(update, rulesList)
        }
        return cumulativeSum
    }

//    fun part2(input: List<String>): Int {
//        val inputPair = processInput(input)
//        val rulesList: List<List<Int>> = inputPair.first
//        val updatesList: List<List<Int>> = inputPair.second
//        var cumulativeSum = 0
//
//        for (update in updatesList) {
//            val applicableRulesList: MutableList<MutableList<Int>> = mutableListOf()
//
//            for (rule in rulesList) {
//                if (update.contains(rule[0]) || update.contains(rule[1])) {
//                    applicableRulesList.add(rule.toMutableList())
//                }
//            }
//
//            val globalOrder = topologicalSort(applicableRulesList)
//            println(globalOrder)
//
//            if (checkUpdate(update, rulesList) == 0) { //invalid list
//                val globalOrderIndexList: MutableList<Int> = mutableListOf()
//                val orderedUpdates: MutableList<Int> = mutableListOf()
//                for (element in update) {
//                    globalOrderIndexList.add(globalOrder.indexOf(element))
//                }
//                globalOrderIndexList.sort()
//                for (index in globalOrderIndexList) {
//                    orderedUpdates.add(globalOrder[index])
//                }
//
//                cumulativeSum +=  checkUpdate(orderedUpdates, rulesList)
//            }
//        }
//
//        return cumulativeSum
//    }

    fun part2(input: List<String>): Int {
        val inputPair = processInput(input)
        val rulesList: List<List<Int>> = inputPair.first
        val updatesList: List<List<Int>> = inputPair.second
        var cumulativeSum = 0

//        println(rulesList)
//        println(updatesList)

        for (update in updatesList) {
            if (checkUpdate(update, rulesList) == 0) {
                cumulativeSum += checkUpdateWithPermutations(update, rulesList)
            }
        }
        return cumulativeSum
    }

//    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
//    part1(testInput).println()
//    part2(testInput).println()



    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
//    part1(input).println()
    part2(input).println()

}
