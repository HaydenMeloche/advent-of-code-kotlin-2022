fun main() {
    val input = readInput("Day08")

    val parsed = input.map { it.toList().map { c -> c.toString().toInt() }}

    val visible = getVisibleCoords(parsed)
    println("answer one: ${visible.size}")

    val scenicMeasures = visible.map { calculateScenicMeasure(parsed, it.first, it.second) }
    println("answer two: ${scenicMeasures.max()}")
}


private fun getVisibleCoords(parsed: List<List<Int>>): Set<Pair<Int, Int>> {
    val visible = mutableSetOf<Pair<Int, Int>>()
    for (x in parsed.indices) {
        for (y in parsed[x].indices) {
            if (isVisible(parsed, x, y)) {
                visible.add(x to y)
            }
        }
    }
    return visible
}

fun isVisible(parsed: List<List<Int>>, x: Int, y: Int): Boolean {
    val ownHeight = parsed[x][y]
    val leftVisible = (0 until x).all { parsed[it][y] < ownHeight }
    val rightVisible = (x + 1 until  parsed[x].size).all { parsed[it][y] < ownHeight }
    val topVisible = (0 until  y).all { parsed[x][it] < ownHeight }
    val bottomVisible = (y + 1 until parsed.size).all { parsed[x][it] < ownHeight }
    return leftVisible || rightVisible || topVisible || bottomVisible ||
            x == 0 || y == 0 || x == parsed[y].size - 1 || y == parsed.size - 1
}

fun calculateScenicMeasure(parsed: List<List<Int>>, x: Int, y: Int): Int {
    val ownHeight = parsed[x][y]
    val leftVisible = (0 until x).reversed().countUntil { parsed[it][y] >= ownHeight }
    val rightVisible = (x + 1 until  parsed[x].size).countUntil { parsed[it][y] >= ownHeight }
    val topVisible = (0 until  y).reversed().countUntil { parsed[x][it] >= ownHeight }
    val bottomVisible = (y + 1 until parsed.size).countUntil { parsed[x][it] >= ownHeight }
    return leftVisible * rightVisible * topVisible * bottomVisible
}

fun Iterable<Int>.countUntil(pred: (Int) -> Boolean): Int {
    var res = 0
    for (element in this) {
        res++
        if (pred(element))
            return res
    }
    return res
}