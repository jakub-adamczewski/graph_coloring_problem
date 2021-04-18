package graph

import model.NeighboursColors

open class UndirectedGraph(val vertexNumber: Int, private val debug: Boolean = false) {

    var edgesNumber: Int = 0
        protected set

    protected val matrix = Array(vertexNumber) { BooleanArray(vertexNumber) { false } }

    // 0 is no color
    private val colors = Array(vertexNumber) { 0 }

    val currentColorsNumber
        get() = colors.maxOf { it }

    val allConnections: List<Pair<Int, Int>>
        get() = (1..vertexNumber).flatMap { vertex ->
            getNeighbours(vertex).map { neighbour ->
                listOf(vertex, neighbour).sorted().toPair()
            }
        }.distinct()

    fun areConnected(connection: Pair<Int, Int>): Boolean = connection.run {
        throwIfIsZero(first, second)
        throwIfIsArgsEqual(connection)

        return matrix[first - 1][second - 1] && matrix[second - 1][first - 1]
    }

    fun areNotConnected(connection: Pair<Int, Int>): Boolean = !areConnected(connection)

    fun color(vertex: Int, color: Int) {
        throwIfIsZero(vertex)
        colors[vertex - 1] = color
    }

    fun getNeighboursColors(vertex: Int): NeighboursColors {
        throwIfIsZero(vertex)
        return NeighboursColors(getNeighbours(vertex).map { getColor(it) })
    }

    fun getNeighbours(vertex: Int): List<Int> {
        throwIfIsZero(vertex)
        return matrix[vertex - 1].mapIndexed { index, isConnected ->
            if (isConnected) index + 1 else 0
        }.filter {
            it > 0
        }
    }

    fun getColor(vertex: Int): Int {
        throwIfIsZero(vertex)
        return colors[vertex - 1]
    }

    fun isColored(vertex: Int): Boolean {
        return getColor(vertex) > 0
    }

    fun <T> List<T>.toPair(): Pair<T, T> {
        if (this.size != 2) throw RuntimeException("List converted to pair should have size of 2")
        return this[0] to this[1]
    }

    protected fun throwIfIsZero(vararg args: Int) {
        if (args.any { it <= 0 }) throw IndexOutOfBoundsException("Matrix is indexed starting with 1")
    }

    protected fun throwIfIsArgsEqual(args: Pair<Int, Int>) {
        if (args.first == args.second) throw RuntimeException("Args can not be equal")
    }

    override fun toString(): String {
        return matrix.map { row ->
            row.map { item ->
                if (item) "1" else "0"
            } + "\n"
        }.toString()
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")
            .replace(" ", "")
            .replace("", " ")
    }
}