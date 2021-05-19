package graph.coloring

import utils.Util.throwIfIsZero

class ColorsManager(private val vertexNumber: Int) {

    // 0 is no color
    // indexes are nodes, numbers inside array are colors
    private val coloring = Array(vertexNumber) { 0 }
    val currentSolution: Array<Int>
        get() = coloring

    val chromaticNumber: Int
        get() = currentSolution.maxOf { it }

    fun color(vertex: Int, color: Int) {
        throwIfIsZero(vertex, color)
        coloring[vertex - 1] = color
    }

    fun color(coloring: Array<Int>) {
        coloring.forEachIndexed { vertexMinusOne, color ->
            if (color != 0) {
                color(vertexMinusOne + 1, color)
            } else {
                clear(vertexMinusOne + 1)
            }
        }
    }

    fun getColor(vertex: Int): Int {
        throwIfIsZero(vertex)
        return coloring[vertex - 1]
    }

    fun isColored(vertex: Int): Boolean {
        return getColor(vertex) > 0
    }

    fun clear() {
        (0 until vertexNumber).forEach {
            coloring[it] = 0
        }
    }

    fun clear(vertex: Int) {
        throwIfIsZero(vertex)
        coloring[vertex - 1] = 0
    }

    fun getNeighboursColors(neighbours: List<Int>): NeighboursColors {
        return NeighboursColors(neighbours.map { getColor(it) })
    }
}