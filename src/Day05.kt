fun main() {
    val input = readInput("Day05")
    val inputReversed = input.asReversed()

    val startingPoint = inputReversed
        .indexOfFirst { it.startsWith(" 1") } + 1

    // build out map of queues
    val scenarioOne = mutableMapOf<Int, ArrayDeque<String>>()
    for (i in startingPoint until  inputReversed.size) {
        inputReversed[i]
            .chunked(4)
            .forEachIndexed { i, s ->
                val char = s.replace("[", "").replace("]", "")
                if (char.isNotBlank()) {
                    if (scenarioOne.containsKey(i + 1)) {
                        scenarioOne[i + 1]?.add(char.trim())
                    } else {
                        scenarioOne[i + 1] = ArrayDeque(listOf(char.trim()))
                    }
                }

            }
    }

    // copy board data for part two
    val scenarioTwo = scenarioOne
        .map { (k, v) ->  k to ArrayDeque(v)}
        .toMap(mutableMapOf())


    val instructionsStartingPoint = input.indexOfFirst { it.isEmpty() } + 1
    for (i in instructionsStartingPoint until input.size) {
        val line = input[i].replace("move", "")
            .replace("from", "")
            .replace("to", "")
            .split(" ")

        val amount = line[1].toInt()
        val from = line[3].toInt()
        val to = line[5].toInt()

        // part one
        (1 ..amount).map {
            scenarioOne[from]!!.removeLast()
        }.forEach {
            scenarioOne[to.toInt()]!!.add(it)
        }

        // part two
        (1 ..amount).map {
            scenarioTwo[from]!!.removeLast()
        }.reversed().forEach {
            scenarioTwo[to]!!.add(it)
        }

    }
    scenarioOne.forEach { (key, value) -> print(value.last()) }
    println()
    scenarioTwo.forEach { (key, value) -> print(value.last()) }
}