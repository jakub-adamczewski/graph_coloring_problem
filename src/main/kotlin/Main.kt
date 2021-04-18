import utils.UndirectedGraphGenerator

fun main() {
    val g1 = UndirectedGraphGenerator.generateCoherentGraphWithDensity(10, 1F)
    println(g1.edgesNumber)
}
