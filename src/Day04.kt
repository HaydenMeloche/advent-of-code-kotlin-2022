fun main() {
    val input = readInput("Day04")

    // part one
    val answerOne = input.count { line ->
        val (a, b) = line.split(",")
            .map { it.split("-")
                .map { it.toInt() }
                .let { (a, b) -> (a..b).toSet() }
            }
        a.containsAll(b) || b.containsAll(a)
    }
    // part two
    val answerTwo = input.count { line ->
        val (a, b) = line.split(",")
            .map { it.split("-")
                .map { it.toInt() }
                .let { (a, b) -> (a..b) }
            }
        (a.first in b) || (b.first in a)
    }

    println("answer one: $answerOne")
    println("answer two: $answerTwo")
}