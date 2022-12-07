fun main() {
    val input = readInput("Day07")
        .toMutableList()
    input.removeAt(0) // hardcoded first node

    var currentNode = Node("/", Type.DIR, null, 0, mutableListOf())
    val root = currentNode
    input.forEach { line ->
        when {
            line == "$ ls" -> {}
            line.startsWith("$ cd") -> {
                val locationToJump = line.takeLastWhile { it != ' ' }
                currentNode = when (locationToJump) {
                    ".." -> currentNode.parent!!
                    else -> currentNode.children!!.find { it.name == locationToJump }!!
                }
            }
            line.startsWith("dir") -> {
                currentNode.children!!.add(Node(line.takeLastWhile { it != ' ' }, Type.DIR, currentNode, 0, mutableListOf()))
            }
            else -> run {
                val (size, name) = line.split(" ")
                currentNode.children!!.add(Node(name, Type.FILE, currentNode, size.toInt(), null))
            }
        }
    }


    // part two fields
    val actualSpace = 70000000 - root.getSize()
    val requiredSpace = 30000000
    val spaceToTrim = (actualSpace - requiredSpace).times(-1)
    val possibleDirectoriesToDelete = mutableListOf<Node>()

    var answerOne = 0

    fun navigate(node: Node) {
        node.children?.forEach { navigate(it) }
        if (node.type == Type.DIR) {
            if (node.getSize() <= 100000) {
                answerOne += node.getSize()
            }
            if (node.getSize() >= spaceToTrim) {
                possibleDirectoriesToDelete.add(node)
            }
        }
    }
    navigate(root)
    println("answer one: $answerOne")

    println("answer two: ${possibleDirectoriesToDelete.minBy { it.getSize() }.getSize()}")
}

data class Node(
    val name: String,
    val type: Type,
    val parent: Node?,
    private val size: Int?,
    val children: MutableList<Node>?
) {
    fun getSize(): Int {
        return (size ?: 0) + (children?.sumOf { it.getSize() } ?: 0)
    }
}

enum class Type {
    FILE,
    DIR
}