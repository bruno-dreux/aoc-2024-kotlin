import sun.util.calendar.CalendarUtils.mod

fun main() {

    fun longestSequence(grid: MutableList<MutableList<Int>>): Int {
        val rows = grid.size
        val cols = if (rows > 0) grid[0].size else 0

        var maxLength = 0

        // Horizontal check
        for (row in grid) {
            var currentLength = 0
            for (value in row) {
                if (value != 0) {
                    currentLength++
                    maxLength = maxOf(maxLength, currentLength)
                } else {
                    currentLength = 0
                }
            }
        }

        // Vertical check
        for (col in 0 until cols) {
            var currentLength = 0
            for (row in 0 until rows) {
                if (grid[row][col] != 0) {
                    currentLength++
                    maxLength = maxOf(maxLength, currentLength)
                } else {
                    currentLength = 0
                }
            }
        }

        return maxLength
    }

    class Robot(var x: Int, var y: Int, val vx: Int, val vy: Int) {
        override fun toString(): String {
            return "Robot in position: $x,$y, with speed: $vx,$vy"
        }
    }

    class Map(var listRobots: List<Robot>,val sizeX: Int,val sizeY: Int) {
        var robotMap: MutableList<MutableList<Int>> = mutableListOf()
        fun initializeBlankMap() {
            for (row in 0 until sizeY) {
                val listRow = mutableListOf<Int>()
                for (column in 0 until sizeX) {
                    listRow.add(0)
                }
                robotMap.add(listRow)
            }
//            printMap()
        }

        fun initializeMap() {
            initializeBlankMap()
            for (robot in listRobots) {
                robotMap[robot.y][robot.x]++
            }
        }

        fun printMap() {
            for (row in 0 until sizeY) {
                for (column in 0 until sizeX) {
                    if (robotMap[row][column] == 0) print(" ")
                    else print(robotMap[row][column])
                }
                println("")
            }
        }

        fun iterateRobot(robot: Robot, steps: Int) {
            var currentX = robot.x
            var currentY = robot.y
            for (i in 1..steps) {
                currentX = (currentX + robot.vx) % sizeX
                currentY = (currentY + robot.vy) % sizeY
                if (currentX < 0) currentX += sizeX
                if (currentY < 0) currentY += sizeY
//                println("Position after $i steps: $currentX, $currentY")
            }
            robot.x = currentX
            robot.y = currentY
//            println("Final position after $steps steps: ${robot.x}, ${robot.y}")
        }

        fun iterateAllRobots(steps: Int) { //This assumes the robotMap is blank
            for (robot in listRobots) {
                println("Iterating robot at position: ${robot.x}, ${robot.y}...")
                iterateRobot(robot, steps)
                println("Robot ended at position: ${robot.x}, ${robot.y}")
                robotMap[robot.y][robot.x]++
            }
        }

        fun iterateAllRobots1Step() { //This assumes the robotMap already contains the starting positions
            for (robot in listRobots) {
                robotMap[robot.y][robot.x]--
                iterateRobot(robot, 1)
                robotMap[robot.y][robot.x]++
            }
        }

        fun calculateSecurityFactor(): Int {
            var qd1: Int = 0
            var qd2: Int = 0
            var qd3: Int = 0
            var qd4: Int = 0

            for (x in 0 until (sizeX - 1) / 2) {
                for (y in 0 until (sizeY - 1) / 2) {
                    qd1 += robotMap[y][x]
                }
                for (y in (sizeY - 1) / 2 + 1 until sizeY) {
                    qd2 += robotMap[y][x]
                }
            }
            for (x in (sizeX - 1) / 2 + 1 until sizeX) {
                for (y in 0 until (sizeY - 1) / 2) {
                    qd3 += robotMap[y][x]
                }
                for (y in (sizeY - 1) / 2 + 1 until sizeY) {
                    qd4 += robotMap[y][x]
                }
            }

            return qd1 * qd2 * qd3 * qd4

        }

        fun calculateTreeScore(): Double {
            var topRight: Int = 0
            var topLeft: Int = 0
            var bottomCenter: Int = 0

            for (x in 0 until (sizeX - 1) / 3) {
                for (y in 0 until (sizeY - 1) / 4) {
                    if (robotMap[y][x] > 0) {
                        topLeft++
                    }
                }
            }
            for (x in (sizeX - 1) * 2 / 3 + 1 until sizeX) {
                for (y in 0 until (sizeY - 1) / 4) {
                    if (robotMap[y][x] > 0) {
                        topRight++
                    }
                }
            }
            for (x in (sizeX - 1) / 3 + 1 until (sizeX - 1) * 2 / 3) {
                for (y in (sizeY - 1) / 4 * 3 + 1 until sizeY) {
                    if (robotMap[y][x] > 0) {
                        bottomCenter++
                    }
                }
            }

            return bottomCenter / (topLeft + topRight).toDouble()

        }

        fun calculateTreeScoreRows(): Double {
            val numberOfParts = 5
            var listRowSize: MutableList<Int> = mutableListOf()
            for (row in robotMap.indices) {
                var currentRowSize = 0
                for (column in robotMap[row].indices) {
                    if (robotMap[row][column] > 0) {
                        currentRowSize++
                    }
                }
                listRowSize.add(currentRowSize)
            }


            val partSize = listRowSize.size / numberOfParts
            val parts = listRowSize.chunked(partSize)

            // Ensure the list is evenly divisible
            val averages = parts.map { part ->
                part.average()
            }

            return averages[5] / averages[1]

        }

        fun calculateTreeScoreSequence(): Double {
            return longestSequence(robotMap).toDouble()
        }

    }

        fun processInput(input: List<String>): MutableList<Robot> {
            val regex = Regex("""p=(\d+),(\d+) v=(-?\d+),(-?\d+)""")
            var listRobots = mutableListOf<Robot>()
            for (line in input) {
                val matchResult = regex.find(line)
                if (matchResult != null) {
                    val x = matchResult.groupValues[1].toInt()
                    val y = matchResult.groupValues[2].toInt()
                    val vx = matchResult.groupValues[3].toInt()
                    val vy = matchResult.groupValues[4].toInt()
                    listRobots.add(Robot(x, y, vx, vy))
                }
            }
            return listRobots
        }

        fun part1(input: List<String>, sizeX: Int, sizeY: Int): Int {
            val listRobots = processInput(input)
            for (robot in listRobots) {
                println(robot)
            }
            val map = Map(listRobots, sizeX, sizeY)
            map.initializeBlankMap()

//        var testRobot = Robot(2,4,2,-3)
//        map.iterateRobot(testRobot, 5)
//        map.robotMap[testRobot.y][testRobot.x]++
//        map.printMap()

            map.iterateAllRobots(100)
            map.printMap()

            return map.calculateSecurityFactor()
        }


        fun part2(input: List<String>, sizeX: Int, sizeY: Int): Int {
            var listRobots = processInput(input)
            val treeScores: MutableMap<Int, Double> = mutableMapOf()
//        for (robot in listRobots) {
//            println(robot)
//        }
            var map = Map(listRobots, sizeX, sizeY)
            map.initializeMap()

            for (i in 1..1000000) {
                map.iterateAllRobots1Step()
                println("Step: $i")
//            map.printMap()
                treeScores.put(i, map.calculateTreeScoreSequence())
            }

            println(treeScores)
            val candidateSteps = treeScores.maxByOrNull { it.value }!!.key

            println("Candidate solution: $candidateSteps")

            listRobots = processInput(input)
            map = Map(listRobots, sizeX, sizeY)
            map.initializeMap()
            for (i in 1..candidateSteps) {
                map.iterateAllRobots1Step()
            }
            println("After $candidateSteps steps")
            map.printMap()

            return 0

        }


    // Or read a large test input from the file:
//    val testInput = readInput("Day14_test")
////    part1(testInput,11,7).println()
//    part2(testInput,11,7).println()

    // Read the input from the file.
    val input = readInput("Day14")
//    part1(input,101,103).println()
    part2(input,101,103).println()
}