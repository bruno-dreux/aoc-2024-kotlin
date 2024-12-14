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

    fun <T> List<T>.permute(): Sequence<List<T>> = sequence {
        if (size <= 1) {
            yield(this@permute) // Base case: yield the single permutation
        } else {
            for (i in indices) {
                val element = this@permute[i]
                val subList = this@permute.filterIndexed { index, _ -> index != i }
                for (perm in subList.permute()) { // Generate permutations lazily
                    yield(listOf(element) + perm) // Yield each new permutation
                }
            }
        }
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
//                println("List is invalid: $update")
//                println(violation1)
//                println(violation2)
                return 0
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

        val midpoint = update[update.size/2]
//        println("List is valid: $update, midpoint is $midpoint")
        return midpoint
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

    fun part2(input: List<String>): Int {
        val inputPair = processInput(input)
        val rulesList: List<List<Int>> = inputPair.first
        val updatesList: List<List<Int>> = inputPair.second
        var cumulativeSum = 0

        for (update in updatesList) {
            if (checkUpdate(update, rulesList) == 0) { //invalid list
                val permutations = update.permute()
                for (permutation in permutations) {
                    if (checkUpdate(permutation, rulesList) > 0) {
                        cumulativeSum += checkUpdate(permutation, rulesList)
                        break
                    }
                }
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
