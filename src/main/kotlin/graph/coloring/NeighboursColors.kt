package graph.coloring

class NeighboursColors(private val neighboursColors: List<Int>){

    val firstAvailableColor: Int get() {
        var color = 1
        while (neighboursColors.contains(color)){
            color++
        }
        return color
    }
}