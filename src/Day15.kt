
fun manhattanDistance(x1: Int, y1: Int, x2: Int, y2: Int) = kotlin.math.abs(x1 - x2) + kotlin.math.abs(y1 - y2)

data class Point(val x: Int, val y: Int)
data class Sensor(val loc: Point, val beacon: Point) {
    val distance = manhattanDistance(loc.x, loc.y, beacon.x, beacon.y)
    fun inRange(x: Int, y: Int) = manhattanDistance(loc.x, loc.y, x, y) <= distance
    fun isBeacon(x: Int, y: Int) = beacon.x == x && beacon.y == y
    fun noBeacon(x: Int, y: Int) = inRange(x, y) && !isBeacon(x, y)
}

class SensorGrid(val sensors: List<Sensor>) {
    val xMin = sensors.minOf { it.loc.x - it.distance }
    val xMax = sensors.maxOf { it.loc.x + it.distance }

    fun scanDepth(depth: Int) = (xMin..xMax).count { x -> sensors.any { it.noBeacon(x, depth) } }
    fun scan(maxRange: Int): Point? {
        for (x in 0..maxRange) {
            var y = 0
            while (y <= maxRange) {
                val sensor = sensors.find { it.inRange(x, y) }
                if (sensor == null) return Point(x, y)
                y = sensor.loc.y + sensor.distance - kotlin.math.abs(x - sensor.loc.x) + 1
            }
        }
        return null
    }
}

fun main() {
    val pattern = Regex(".*x=(-?\\d+), y=(-?\\d+).*x=(-?\\d+), y=(-?\\d+)")
    val lines = readInput("Day15")
    val sensors = lines.map { line ->
        val (x1, y1, x2, y2) = pattern.find(line)!!.destructured
        Sensor(Point(x1.toInt(), y1.toInt()), Point(x2.toInt(), y2.toInt()))
    }
    val sensorGrid = SensorGrid(sensors)

    println("answer one: ${sensorGrid.scanDepth(2000000)}")
    println("answer two: ${sensorGrid.scan(4000000)?.let { it.x.toLong() * 4000000L + it.y.toLong() }}")

}