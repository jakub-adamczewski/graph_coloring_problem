package model

import graph.coloring.NeighboursColors
import org.junit.Test

class NeighboursColorsTest {

    @Test
    fun `when neighbours colors are given, then return first free color`() {
        val neighboursColors = NeighboursColors(listOf(0, 1, 0, 1, 3))

        assert(neighboursColors.firstAvailableColor == 2)
    }

    @Test
    fun `do not return 0, as available color`() {
        val neighboursColors = NeighboursColors(listOf(1, 2, 3, 4))

        assert(neighboursColors.firstAvailableColor == 5)
    }
}