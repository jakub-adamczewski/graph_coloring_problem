package graph

class MutableUndirectedGraph(vertexNumber: Int) : UndirectedGraph(vertexNumber) {

    fun addConnection(connection: Pair<Int, Int>) = connection.run {
        throwIfIsZero(first, second)
        throwIfIsArgsEqual(connection)

        if (areNotConnected(connection)) {
            matrix[first - 1][second - 1] = true
            matrix[second - 1][first - 1] = true
            edgesNumber++
        }
    }

    fun addConnections(vararg connections: Pair<Int, Int>) = connections.forEach { connection ->
        addConnection(connection)
    }
}