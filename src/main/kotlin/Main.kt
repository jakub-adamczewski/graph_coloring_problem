import algorithms.GreedyAlgorithm.colorWithGreedyAlgorithm
import algorithms.TabuSearch
import graph.UndirectedGraph
import utils.Files
import utils.Util.graphFromResource
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

fun main() {
    // quick graphs - queen6, miles250,
    advancedTabuSearchTest(graphFromResource(Files.TXT_RES_LOCALIZATION, "queen6"))
//    pythonTest()
}

//private fun IntArray.toBoolean(): BooleanArray = this.map { it != 0 }.toBooleanArray()

//fun pythonTest(){
//    val graph = UndirectedGraph(12, "python").apply {
//        matrix = arrayOf(
//            intArrayOf(0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0).toBoolean(),
//            intArrayOf(1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0).toBoolean(),
//            intArrayOf(0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0).toBoolean(),
//            intArrayOf(0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0).toBoolean(),
//            intArrayOf(1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0).toBoolean(),
//            intArrayOf(0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1).toBoolean(),
//            intArrayOf(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1).toBoolean(),
//            intArrayOf(0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1).toBoolean(),
//            intArrayOf(0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0).toBoolean(),
//            intArrayOf(1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1).toBoolean(),
//            intArrayOf(0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0).toBoolean(),
//            intArrayOf(0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0).toBoolean()
//        )
//    }
//    val solution = TabuSearch.getTabuSearchSolution(graph, 10, 7, 100, 10_000)
//    println("Test solution python: ${solution?.coloring?.chromaticNumber ?: -1}")
//}

fun advancedTabuSearchTest(graph: UndirectedGraph) {
    println("Tabu Search optimization test for ${graph.name}")
    val greedyChromaticNumber = graph.colorWithGreedyAlgorithm().coloring.chromaticNumber
    println("Greedy coloring chromatic number: $greedyChromaticNumber")
    graph.coloring.clear()

    val computationsDurationMillis = measureTimeMillis {
        TabuSearch.optimizationProblem(graph, 20, greedyChromaticNumber, 7, 100, 3_000)
    }
    val seconds = TimeUnit.MILLISECONDS.toSeconds(computationsDurationMillis)
    println("Execution duration seconds: $seconds")
}

fun basicTabuSearchTest(graph: UndirectedGraph, optimalColorsNumber: Int) {
    println("Tabu Search test for ${graph.name}")
    val greedyChromaticNumber = graph.colorWithGreedyAlgorithm().coloring.chromaticNumber
    println("Greedy coloring chromatic number: $greedyChromaticNumber")
    graph.coloring.clear()

    val computationsDurationMillis = measureTimeMillis {
        TabuSearch.getTabuSearchSolution(graph, optimalColorsNumber, 7, 100, 1000)
    }
    val seconds = TimeUnit.MILLISECONDS.toSeconds(computationsDurationMillis)
    println("Execution duration seconds: $seconds")
}
