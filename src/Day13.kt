import kotlinx.serialization.json.*
import kotlinx.serialization.decodeFromString

fun main() {
    val input = readInput("Day13")

    input.asSequence()
            .filter { it.isNotBlank() }
            .map {
                Json.decodeFromString<JsonArray>(it)
            }
            .windowed(size = 2, step = 2)
            .mapIndexedNotNull { index, list ->
                val leftSide = list[0]
                val rightSide = list[1]
                if (compare(leftSide, rightSide) == true) index + 1 else null
            }
            .sum()
            .also { println("answer one: $it") }


    val partTwoInput = input
            .toMutableList()

    partTwoInput.add("[[2]]")
    partTwoInput.add("[[6]]")

    partTwoInput
            .asSequence()
            .filter { it.isNotBlank() }
            .map {
                Json.decodeFromString<JsonArray>(it)
            }
            .sortedWith { a, b -> if (compare(a, b) == true) -1 else 1 }
            .mapIndexedNotNull { index, entry ->
                if (entry.toString() == partTwoInput[partTwoInput.size - 2] || entry.toString() == partTwoInput[partTwoInput.size - 1]) {
                    index+1
                } else null
            }
            .reduce(Int::times)
            .also { println("answer two: $it") }
}



fun compare(a: JsonArray, b: JsonArray, pos: Int = 0): Boolean? {
    val left = a.elementAtOrNull(pos)
    val right = b.elementAtOrNull(pos)
    return when {
        left == null && right == null -> null
        left == null || right == null -> left == null
        left is JsonPrimitive && right is JsonPrimitive -> if (left == right) compare(a, b, pos + 1) else left.int < right.int
        left is JsonArray && right is JsonArray -> compare(left, right) ?: compare(a, b, pos + 1)
        left is JsonPrimitive && right is JsonArray -> compare(JsonArray(listOf(left)), right) ?: compare(a, b, pos + 1)
        left is JsonArray && right is JsonPrimitive -> compare(left, JsonArray(listOf(right))) ?: compare(a, b, pos + 1)
        else -> throw IllegalStateException("$left $right")
    }
}