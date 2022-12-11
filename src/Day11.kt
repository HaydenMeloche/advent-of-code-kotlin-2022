fun main() {
    val partTwo = true
    val input = readInput("Day11").toMutableList()
    val monkeys = mutableMapOf<Int, Monkey>()
    input.removeAll { it.isBlank() }
    input.windowed(size = 6, step = 6).forEach { entry ->
        val id = entry[0].substringBeforeLast(":")
            .takeLastUntilSpace()
            .toInt()
        val startingItems = entry[1].substringAfter(":")
            .split(",")
            .map { it.trim().toLong() }
            .toMutableList()

        monkeys[id] = Monkey(id, startingItems, entry[2], entry.slice(3 until 6))
    }

    val rounds = if (partTwo) 10_000 else 20
    repeat(rounds) {
        monkeys.forEach { (_, monkey) ->
            monkey.inspect(monkeys, partTwo)
        }
    }
    val twoMostActiveMonkey = monkeys.values.sortedByDescending { it.inspectionCount }
    if (partTwo) {
        println("answer two: ")
    } else println("answer one: ")

    print("${twoMostActiveMonkey[0].inspectionCount * twoMostActiveMonkey[1].inspectionCount}")
}

class Monkey(var id: Int, var items: MutableList<Long>, operation: String, test: List<String>) {
    var inspectionCount = 0L
    private var operation: (worry: Long) -> Long
    private var test: (worry: Long) -> Boolean
    private var targetMonkeyIfTrue: Int
    private var targetMonkeyIfFalse: Int
    private var divider: Long

    init {
        val operationSplit = operation.split(" ")
        val value = operationSplit[operationSplit.size - 1]
        val modifier = operationSplit[operationSplit.size - 2]
        this.operation = {
            val computeBy = if (value == "old") it else value.toLong()
            when (modifier) {
                "+" -> it + computeBy
                else -> it * computeBy
            }
        }
        divider = test[0].takeLastUntilSpace().toLong()
        this.test = {
            it % test[0].takeLastUntilSpace().toLong() == 0L
        }
        targetMonkeyIfTrue = test[1].takeLastUntilSpace().toInt()
        targetMonkeyIfFalse = test[2].takeLastUntilSpace().toInt()
    }

    fun inspect(monkeys: MutableMap<Int, Monkey>, partTwo: Boolean) {
        items.forEach { item ->
            inspectionCount++
            var worryLevel = operation.invoke(item)
            worryLevel = if (partTwo) {
                val mod = monkeys.values.map { it.divider }.reduce(Long::times)
                worryLevel % mod
            } else worryLevel / 3
            if (test(worryLevel)) {
                monkeys[targetMonkeyIfTrue]!!.items.add(worryLevel)
            } else monkeys[targetMonkeyIfFalse]!!.items.add(worryLevel)
        }
        items.clear()
    }
}