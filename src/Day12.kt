fun main() {
    val lines = readInput("Day12")
    val graph = Graph(lines.mapIndexed { y, line -> line.mapIndexed { x, code -> Vertex(x, y, code) } })

    println("answer one: ${graph.findShortestPath('S')}")
    println("answer two: ${graph.findShortestPath('a')}")
}

class Vertex(val x: Int, val y: Int, val code: Char) {
    val height: Int
        get() = when(code) {
            'S' -> 0
            'E' -> 26
            else -> code - 'a'
        }
}

class Graph(private val vertexes: List<List<Vertex>>) {
    private val xMax = vertexes[0].lastIndex
    private val yMax = vertexes.lastIndex

    fun findShortestPath(target: Char): Int? {
        val end = vertexes.flatten().single { it.code == 'E' }
        val exploringVertex = ArrayDeque(listOf(end to 0))
        val visited = mutableSetOf(end)
        while (!exploringVertex.isEmpty()) {
            val (vertex, distance) = exploringVertex.removeFirst()
            if (vertex.code == target) return distance
            vertex.reachableNeighbors(vertex.height).forEach {
                if (visited.add(it)) exploringVertex.add(it to distance + 1)
            }
        }
        return null
    }

    private fun Vertex.neighbors() = listOfNotNull(
        vertexAt(x - 1, y), vertexAt(x + 1, y),
        vertexAt(x, y - 1), vertexAt(x, y + 1)
    )

    private fun Vertex.reachableNeighbors(height: Int) = neighbors().filter { it.height + 1 >= height}

    private fun vertexAt(x: Int, y: Int) =
        if (x < 0 || x > xMax || y < 0 || y > yMax) null else vertexes[y][x]
}