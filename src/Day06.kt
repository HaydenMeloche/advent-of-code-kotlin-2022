fun main() {
    val input = readInput("Day06")

    println("answer one: ${findAnswer(input[0], 4)}")
    println("answer two: ${findAnswer(input[0], 14)}")
}

fun findAnswer(input: String, size: Int): Int {
    return input.windowed(size).indexOfFirst {
        it.toSet().size == size
    } + size
}