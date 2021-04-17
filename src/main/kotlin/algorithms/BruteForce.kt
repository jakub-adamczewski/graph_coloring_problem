package algorithms

import graph.UndirectedGraph

object BruteForce {

    fun UndirectedGraph.getBruteForceChromaticNumber(): Int {
        color(1,1)
        for (vertex in 2 .. vertexNumber){
            color(vertex, getNeighboursColors(vertex).firstAvailableColor)
        }
        return  chromaticNumber
    }
}