package algorithms

import graph.Graph

object BruteForce {

    fun Graph.getBruteForceChromaticNumber(): Int {
        color(1,1)
        for (vertex in 2 .. vertexNumber){
            color(vertex, getNeighboursColors(vertex).firstAvailableColor)
        }
        return  chromaticNumber
    }
}