package algorithms

import graph.UndirectedGraph

object GreedyAlgorithm {

    fun UndirectedGraph.colorWithGreedyAlgorithm(): UndirectedGraph {
        coloring.run {
            color(1, 1)
            for (vertex in 2..vertexNumber) {
                color(vertex, getNeighboursColors(getNeighbours(vertex)).firstAvailableColor)
            }
            return this@colorWithGreedyAlgorithm
        }
    }
}