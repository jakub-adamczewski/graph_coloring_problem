package graph.coloring

import utils.Util.throwIfIsZero

class ColorsManager(vertexNumber: Int) {

    // 0 is no color
    private val colors = Array(vertexNumber) { 0 }

    val currentColorsNumber
        get() = colors.maxOf { it }

    fun color(vertex: Int, color: Int) {
        throwIfIsZero(vertex)
        colors[vertex - 1] = color
    }

    fun getColor(vertex: Int): Int {
        throwIfIsZero(vertex)
        return colors[vertex - 1]
    }

    fun isColored(vertex: Int): Boolean {
        return getColor(vertex) > 0
    }

    fun getNeighboursColors(neighbours: List<Int>): NeighboursColors {
        return NeighboursColors(neighbours.map { getColor(it) })
    }
}