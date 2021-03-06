package utils

import graph.UndirectedGraph

object UndirectedGraphGenerator {

    fun generateCoherentGraph(size: Int, graphDensity: Float, stableOrRandom: Boolean = true): UndirectedGraph {
        throwIfBadRange(graphDensity)

        val graph = UndirectedGraph(size).apply { addObligatoryEdges() }
        var edgesNumberToAdd = (maximumEdgesNumberForGraphSize(size) * graphDensity).toInt() - graph.edgesNumber
        while (edgesNumberToAdd > 0) {
            graph.apply {
                addConnection(
                    if (stableOrRandom) {
                        firstUnconnectedVertices()
                    } else {
                        randomUnconnectedVertices()
                    }
                )
            }
            edgesNumberToAdd--
        }

        return graph
    }

    private fun UndirectedGraph.addObligatoryEdges() {
        for (vertex in 1 until vertexNumber) {
            addConnection(vertex to vertex + 1)
        }
        addConnection(vertexNumber to 1)
    }

    private fun UndirectedGraph.firstUnconnectedVertices(): Pair<Int, Int> {
        val v1 = (1..vertexNumber).first { vertex -> getNeighbours(vertex).size < vertexNumber - 1 }
        val v2 = (1..vertexNumber).filter { vertex -> vertex != v1 }.first { vertex -> areNotConnected(v1 to vertex) }

        return v1 to v2
    }

    private fun UndirectedGraph.randomUnconnectedVertices(): Pair<Int, Int> {
        val v1 = (1..vertexNumber).filter { vertex -> getNeighbours(vertex).size < vertexNumber - 1 }.random()
        val v2 = (1..vertexNumber).filter { vertex -> vertex != v1 }.filter { vertex -> areNotConnected(v1 to vertex) }
            .random()

        return v1 to v2
    }

    private fun maximumEdgesNumberForGraphSize(size: Int): Int = size * (size - 1) / 2

    private fun throwIfBadRange(argument: Float) {
        if (argument < 0 || argument > 1) throw RuntimeException("Incorrect density, it should be float between 0 and 1")
    }
}