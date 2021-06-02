package graph

import graph.coloring.ColorsManager
import utils.Util.sorted
import utils.Util.throwIfIsArgsEqual
import utils.Util.throwIfIsZero

class UndirectedGraph(val vertexNumber: Int, val name: String = "no name", private val debug: Boolean = false) {

    val coloring = ColorsManager(vertexNumber)

    protected val matrix = Array(vertexNumber) { BooleanArray(vertexNumber) { false } }

    val edgesNumber: Int
        get() = matrix.flatMap { row -> row.filter { it } }.size / 2

    val allConnections: List<Pair<Int, Int>>
        get() = (1..vertexNumber).flatMap { vertex ->
            getNeighbours(vertex).map { neighbour ->
                (vertex to neighbour).sorted()
            }
        }.distinct()

    val density: Float
        get() = allConnections.size / maxConnectionsNumber.toFloat()

    val maxConnectionsNumber: Int = (vertexNumber * (vertexNumber - 1) / 2)

    val wrongConnections: List<Pair<Int, Int>>
        get() = allConnections.filter { (vertex1, vertex2) ->
            val color1 = coloring.getColor(vertex1)
            val color2 = coloring.getColor(vertex2)
            color1 == color2 && color1 != 0 && color2 != 0
        }

    fun areConnected(connection: Pair<Int, Int>): Boolean = connection.run {
        throwIfIsZero(first, second)
        throwIfIsArgsEqual(connection)

        return matrix[first - 1][second - 1] && matrix[second - 1][first - 1]
    }

    fun areNotConnected(connection: Pair<Int, Int>): Boolean = !areConnected(connection)

    fun addConnection(connection: Pair<Int, Int>) = connection.run {
        throwIfIsZero(first, second)
        throwIfIsArgsEqual(connection)

        if (areNotConnected(connection)) {
            matrix[first - 1][second - 1] = true
            matrix[second - 1][first - 1] = true
        }
    }

    fun addConnections(vararg connections: Pair<Int, Int>) = connections.forEach { connection ->
        addConnection(connection)
    }

    fun getNeighbours(vertex: Int): List<Int> {
        throwIfIsZero(vertex)
        return matrix[vertex - 1].mapIndexed { index, isConnected ->
            if (isConnected) index + 1 else 0
        }.filter {
            it > 0
        }
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