fun main() {
    val input = readInput("Day02")

    // part one
    var partOneScore = 0
    input.forEach { line ->
        val split = line.split(" ")
        val opponentHand = Play.from(split[0])
        val myHand = Play.from(split[1])

        partOneScore += myHand.score + myHand.computeScore(opponentHand)
    }
    println("part one score: $partOneScore")

    // part two
    var partTwoScore = 0
    input.forEach { line ->
        val split = line.split(" ")
        val opponentHand = Play.from(split[0])
        val desiredState = DesiredState.from(split[1])
        val myHand = opponentHand.computeHand(desiredState)
        partTwoScore += myHand.score + myHand.computeScore(opponentHand)
    }
    println("part two score: $partTwoScore")
}

enum class DesiredState {
    WIN,
    LOSE,
    TIE;

    companion object {
        fun from(value: String): DesiredState {
            return when (value) {
                "X" -> LOSE
                "Y" -> TIE
                "Z" -> WIN
                else -> throw Exception()
            }
        }
    }
}

private enum class Play(val score: Int) : Comparable<Play> {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun computeHand(desiredState: DesiredState): Play {
        return when (this) {
            ROCK -> when (desiredState) {
                DesiredState.WIN -> PAPER
                DesiredState.LOSE -> SCISSORS
                DesiredState.TIE -> this
            }
            PAPER -> when (desiredState) {
                DesiredState.WIN -> SCISSORS
                DesiredState.LOSE -> ROCK
                DesiredState.TIE -> this
            }
            SCISSORS -> when (desiredState) {
                DesiredState.WIN -> ROCK
                DesiredState.LOSE -> PAPER
                DesiredState.TIE -> this
            }
        }

    }

    fun computeScore(other: Play): Int {
        return when (this) {
            ROCK -> when (other) {
                ROCK -> 3
                PAPER -> 0
                SCISSORS -> 6
            }

            PAPER -> when (other) {
                ROCK -> 6
                PAPER -> 3
                SCISSORS -> 0
            }

            SCISSORS -> when (other) {
                ROCK -> 0
                PAPER -> 6
                SCISSORS -> 3
            }
        }
    }

    companion object {

        fun from(value: String): Play {
            return when (value) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> throw Exception()
            }
        }
    }
}
