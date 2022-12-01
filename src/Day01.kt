fun main() {
    val input = readInput("Day01")
    val listOfLists = input.flatMapIndexed { index, x ->
        when {
            index == 0 || index == input.lastIndex -> listOf(index)
            x.isEmpty() -> listOf(index - 1, index + 1)
            else -> emptyList()
        }
    }.windowed(size = 2, step = 2) { (from, to) -> input.slice(from..to) }

    val partOne = listOfLists.maxOfOrNull { it.sumOf { num -> num.toInt() } }
    println("part one answer: $partOne")

    val partTwo = listOfLists.map { it.sumOf { num -> num.toInt() } }.sortedDescending().take(3).sum()
    println("part two answer: $partTwo")
}
