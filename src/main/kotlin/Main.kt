import algorithms.GreedyAlgorithm.colorWithGreedyAlgorithm
import algorithms.TabuSearch
import graph.UndirectedGraph
import utils.Files
import utils.UndirectedGraphGenerator
import utils.Util.graphFromResource
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

fun main() {

//    val graph = UndirectedGraphGenerator.generateCoherentGraph(200, 0.90f, false)
//    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
//    advancedQuickSearch(graph, 7, reps, 2000, 10, 5)

    val graph = graphFromResource(Files.TXT_RES_LOCALIZATION, "gc1000_300013")
    val reps = (graph.density * (graph.vertexNumber - 1) / 2).toInt()
//    advancedQuickSearch(graph, 7, reps, 3000, 10, 5)
//    advancedLinearTabuSearchTest(graph, 7, reps, 2000, 10, 10, 3)
    TabuSearch.getTabuSearchSolution(graph, 155, 7, reps, 5000)
}


//7
fun queen6Adv() {
    val graph = graphFromResource(Files.TXT_RES_LOCALIZATION, "queen6")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedQuickSearch(graph, 7, reps, 5_000, 10, 5)
}

//8
fun miles250Adv() {
    val graph = graphFromResource(Files.TXT_RES_LOCALIZATION, "miles250")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedQuickSearch(graph, 7, reps, 5_000, 10, 5)
}

//7
fun le450_5aAdv() {
    val graph = graphFromResource(Files.COL_RES_LOCALIZATION, "le450_5a")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedQuickSearch(graph, 7, reps, 5_000, 10, 5)
}

fun miles500Adv() {
    val graph = graphFromResource(Files.COL_RES_LOCALIZATION, "miles500")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedQuickSearch(graph, 7, reps, 2000, 10, 5)
}

fun graphConflictsTOIterations() {
    val graph = UndirectedGraphGenerator.generateCoherentGraph(300, 0.5f)
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    TabuSearch.getTabuSearchSolution(graph, 65, 7, reps, 1000)
}

fun testWithComputedValues(graph: UndirectedGraph, nTry: Int) {
    val reps = (graph.density * graph.vertexNumber - 1).toInt()
    val maxIterations = graph.vertexNumber * 200
    val tabuSize = 7
    advancedQuickSearch(graph, tabuSize, reps, maxIterations, nTry)
}

fun advancedQuickSearch(
    graph: UndirectedGraph,
    tabuSize: Int,
    reps: Int,
    maxIterations: Int,
    nTry: Int,
    maxExecutionTimeMinutes: Int = 1
) {
    println("Tabu Search optimization test for ${graph.name}")
    val greedyChromaticNumber = graph.colorWithGreedyAlgorithm().coloring.chromaticNumber
    println("Greedy coloring chromatic number: $greedyChromaticNumber")
    graph.coloring.clear()

    val computationsDurationMillis = measureTimeMillis {
        TabuSearch.quickSearchOptimizationProblem(
            graph,
            nTry,
            greedyChromaticNumber,
            tabuSize,
            reps,
            maxIterations,
            maxExecutionTimeMinutes
        )
    }
    val seconds = TimeUnit.MILLISECONDS.toSeconds(computationsDurationMillis)
    println("Execution duration seconds: $seconds")
}

fun advancedLinearTabuSearchTest(
    graph: UndirectedGraph,
    tabuSize: Int,
    reps: Int,
    maxIterations: Int,
    nTry: Int,
    maxExecutionTimeMinutes: Int = 1,
    jumpValue: Int = 1
) {
    println("Tabu Search optimization test for ${graph.name}")
    val greedyChromaticNumber = graph.colorWithGreedyAlgorithm().coloring.chromaticNumber
    println("Greedy coloring chromatic number: $greedyChromaticNumber")
    graph.coloring.clear()

    val computationsDurationMillis = measureTimeMillis {
        TabuSearch.linearOptimizationProblem(
            graph,
            nTry,
            greedyChromaticNumber,
            tabuSize,
            reps,
            maxIterations,
            maxExecutionTimeMinutes,
            jumpValue
        )
    }
    val seconds = TimeUnit.MILLISECONDS.toSeconds(computationsDurationMillis)
    println("Execution duration seconds: $seconds")
}

fun tabuSearchStages(size: Int, density: Float) {
    val graph = UndirectedGraphGenerator.generateCoherentGraph(size, density)
    graph.colorWithGreedyAlgorithm()
    val greedyChromaticNumber = graph.coloring.chromaticNumber
    println()
    println("Greedy chromatic number: $greedyChromaticNumber")
    graph.coloring.clear()
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    TabuSearch.getTabuSearchSolution(graph, greedyChromaticNumber, 7, reps, 5000)

}