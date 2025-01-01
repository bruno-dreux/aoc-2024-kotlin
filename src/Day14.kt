import sun.util.calendar.CalendarUtils.mod

fun main() {

    class Robot(var x: Int, var y: Int, val vx: Int, val vy: Int) {
        override fun toString(): String {
            return "Robot in position: $x,$y, with speed: $vx,$vy"
        }
    }

    class Map(var listRobots: List<Robot>,val sizeX: Int,val sizeY: Int) {
        var robotMap: MutableList<MutableList<Int>> = mutableListOf()
        fun initializeMap(){
            for (row in 0 until sizeY) {
                val listRow = mutableListOf<Int>()
                for (column in 0 until sizeX) {
                    listRow.add(0)
                }
                robotMap.add(listRow)
            }
//            printMap()
        }
        fun printMap(){
            for (row in 0 until sizeY) {
                for (column in 0 until sizeX) {
                    print(robotMap[row][column])
                }
                println("")
            }
        }
        fun iterateRobot(robot: Robot, steps: Int){
            var currentX = robot.x
            var currentY = robot.y
            for (i in 1 .. steps) {
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
        fun iterateAllRobots(steps: Int){
            for (robot in listRobots){
                println("Iterating robot at position: ${robot.x}, ${robot.y}...")
                iterateRobot(robot, steps)
                println("Robot ended at position: ${robot.x}, ${robot.y}")
                robotMap[robot.y][robot.x]++
            }
        }

        fun calculateSecurityFactor(): Int {
            var qd1: Int = 0
            var qd2: Int = 0
            var qd3: Int = 0
            var qd4: Int = 0

            for (x in 0 until (sizeX-1)/2) {
                for (y in 0 until (sizeY-1)/2) {
                    qd1 += robotMap[y][x]
                }
                for (y in (sizeY-1)/2+1 until sizeY) {
                    qd2 += robotMap[y][x]
                }
            }
            for (x in (sizeX-1)/2+1 until sizeX) {
                for (y in 0 until (sizeY-1)/2) {
                    qd3 += robotMap[y][x]
                }
                for (y in (sizeY-1)/2+1 until sizeY) {
                    qd4 += robotMap[y][x]
                }
            }

            return qd1 * qd2 * qd3 * qd4

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
        val map = Map(listRobots,sizeX,sizeY)
        map.initializeMap()

//        var testRobot = Robot(2,4,2,-3)
//        map.iterateRobot(testRobot, 5)
//        map.robotMap[testRobot.y][testRobot.x]++
//        map.printMap()

        map.iterateAllRobots(100)
        map.printMap()

        return map.calculateSecurityFactor()
    }


    fun part2(input: List<String>): Int {
        return 0
    }



    // Or read a large test input from the file:
//    val testInput = readInput("Day14_test")
//    part1(testInput,11,7).println()
//    part2(testInput).println()

    // Read the input from the file.
    val input = readInput("Day14")
    part1(input,101,103).println()
//    part2(input).println()
}