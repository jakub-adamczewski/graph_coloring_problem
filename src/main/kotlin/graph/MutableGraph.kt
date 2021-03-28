package graph

class MutableGraph(vertexNumber: Int) : Graph(vertexNumber) {

    fun addConnection(connection: Pair<Int, Int>) = connection.run {
        throwIfIsZero(first, second)

        matrix[first - 1][second - 1] = true
        matrix[second - 1][first - 1] = true
    }

    fun addConnections(vararg connections: Pair<Int, Int>) = connections.forEach { connection ->
        addConnection(connection)
    }
}