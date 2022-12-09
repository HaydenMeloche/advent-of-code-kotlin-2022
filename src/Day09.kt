fun main() {
    val input = readInput("Day09")

    val partOnePositions = mutableSetOf<Location>()
    val partTwoPositions = mutableSetOf<Location>()

    var headLocation = Location(0, 0)
    val tails = (0 until 9).map {
        Location(0, 0)
    }.toMutableList()

    input.forEach {
        val (direction, amount) = it.split(" ")
        when (direction) {
            "R" -> {
                (0 until amount.toInt()).forEach { _ ->
                    headLocation = headLocation.moveRight(1)
                    moveTails(tails, partTwoPositions, headLocation)
                    partOnePositions.add(tails[0])
                }
            }

            "L" -> {
                (0 until amount.toInt()).forEach { _ ->
                    headLocation = headLocation.moveLeft(1)
                    moveTails(tails, partTwoPositions, headLocation)
                    partOnePositions.add(tails[0])
                }
            }

            "U" -> {
                (0 until amount.toInt()).forEach { _ ->
                    headLocation = headLocation.moveUp(1)
                    moveTails(tails, partTwoPositions, headLocation)
                    partOnePositions.add(tails[0])
                }
            }

            "D" -> {
                (0 until amount.toInt()).forEach { _ ->
                    headLocation = headLocation.moveDown(1)
                    moveTails(tails, partTwoPositions, headLocation)
                    partOnePositions.add(tails[0])
                }
            }
        }
    }

    println("answer one: ${partOnePositions.size}")
    println("answer two: ${partTwoPositions.size}")
}

fun moveTails(tails: MutableList<Location>, partTwoPositions: MutableSet<Location>, headLocation: Location) {
    tails.forEachIndexed { index, _ ->
        if (index == 0) {
            tails[index] = moveTail(headLocation, tails[index])
        } else tails[index] = moveTail(tails[index - 1], tails[index])

        if (index == tails.size - 1) {
            partTwoPositions.add(tails[8])
        }
    }
}

fun moveTail(headLocation: Location, tailLocation: Location): Location {
    var tempLocation = tailLocation
    if (!headLocation.withinRange(tailLocation)) {
        val moveX: Int
        val moveY: Int
        if (headLocation.x != tailLocation.x && headLocation.y != tailLocation.y) {
            moveX = if (headLocation.x < tailLocation.x) -1 else 1
            moveY = if (headLocation.y < tailLocation.y) -1 else 1
            tempLocation = tempLocation.moveRight(moveX).moveUp(moveY)
        } else if (headLocation.x != tailLocation.x) {
            moveX = if (headLocation.x < tailLocation.x) -1 else 1
            tempLocation = tempLocation.moveRight(moveX)
        } else if (headLocation.y != tailLocation.y) {
            moveY = if (headLocation.y < tailLocation.y) -1 else 1
            tempLocation = tempLocation.moveUp(moveY)
        }
    }

    return tempLocation
}

data class Location(
    val x: Int,
    val y: Int
)

fun Location.moveRight(num: Int) = this.copy(x = this.x + num, y = this.y)
fun Location.moveLeft(num: Int) = this.copy(x = this.x - num, y = this.y)
fun Location.moveUp(num: Int) = this.copy(x = this.x, y = this.y + num)
fun Location.moveDown(num: Int) = this.copy(x = this.x, y = this.y - num)
fun Location.withinRange(location: Location): Boolean {
    return if (this == location) {
        true
    } else (this.x + 1 == location.x && this.y == location.y) ||
            (this.x - 1 == location.x && this.y == location.y) ||
            (this.y + 1 == location.y && this.x == location.x) ||
            (this.y - 1 == location.y && this.x == location.x) ||
            (this.x + 1 == location.x && this.y + 1 == location.y) ||
            (this.x + 1 == location.x && this.y - 1 == location.y) ||
            (this.x - 1 == location.x && this.y - 1 == location.y) ||
            (this.x - 1 == location.x && this.y + 1 == location.y) ||
            (this.y + 1 == location.y && this.x + 1 == location.x) ||
            (this.y + 1 == location.y && this.x - 1 == location.x) ||
            (this.y - 1 == location.y && this.x - 1 == location.x) ||
            (this.y - 1 == location.y && this.x + 1 == location.x)
}