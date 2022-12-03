fun main() {
    val input = readInput("Day03")
    var partOne = 0
    var partTwo = 0
    input.forEachIndexed { index, line ->
        val middle = line.length / 2
        val compartmentOne = line.take(middle).toSet()
        val compartmentTwo = line.takeLast(middle).toSet()
        compartmentOne.intersect(compartmentTwo).forEach {
            partOne += calcScore(it)
        }

        if ((index + 1) % 3 == 0 && index != 0) {
            val group1 = input[index - 2].toSet()
            val group2 = input[index - 1].toSet()
            val group3 = input[index].toSet()
            group1.intersect(group2).intersect(group3).forEach {
                partTwo += calcScore(it)
            }

        }
    }
    println("part one answer: $partOne")
    println("part two answer: $partTwo")
}

fun calcScore(char: Char): Int {
    return if (char.isUpperCase()) (char.code - 38)
    else (char.code - 96)
}