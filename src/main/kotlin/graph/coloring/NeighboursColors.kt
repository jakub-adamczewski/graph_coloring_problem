package graph.coloring

class NeighboursColors(private val neighboursColors: List<Int>) {

    fun firstAvailableColor(colorsNumber: Int): Int? {
        var color = 1
        while (neighboursColors.contains(color)) {
            color++
            if (color > colorsNumber) return null
        }
        return color
    }

    val firstAvailableColor: Int
        get() {
            var color = 1
            while (neighboursColors.contains(color)) {
                color++
            }
            return color
        }
}