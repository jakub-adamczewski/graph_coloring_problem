package algorithms

import graph.UndirectedGraph

object GreedyAlgorithm {

    fun UndirectedGraph.getGreedyChromaticNumber(): Int {
        color(1,1)
        for (vertex in 2 .. vertexNumber){
            color(vertex, getNeighboursColors(vertex).firstAvailableColor)
        }
        return  currentColorsNumber
    }
}