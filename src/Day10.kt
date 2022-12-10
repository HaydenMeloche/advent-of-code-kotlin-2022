fun main() {
    val input = readInput("Day10")
    val state = State()
    input.forEach {
        when (it) {
            "noop" -> state.tick()
            else -> state.modifyX(it.split(" ")[1].toInt())
        }
    }
    println("answer one: ${state.strength}")
}

class State {
    private var cycle = 0
    private var x = 1
    var strength = 0

    fun tick() {
        print(if (cycle % 40 in (x - 1..x + 1)) '#' else ' ')
        when (++cycle % 40) {
            20 -> strength += x * cycle
            0 -> println()
        }
    }

    fun modifyX(amount: Int) {
        repeat(2) { tick() }
        x += amount
    }

}