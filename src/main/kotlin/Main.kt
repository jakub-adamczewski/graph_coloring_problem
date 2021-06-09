import algorithms.GreedyAlgorithm.colorWithGreedyAlgorithm
import algorithms.TabuSearch
import graph.UndirectedGraph
import utils.Files
import utils.UndirectedGraphGenerator
import utils.Util.graphFromResource
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

fun main() {
//    first()
//    second()
//    third()
//    fourth()
//    fifth()
    tabuSearchStages(12, 0.7f)
}

fun tabuSearchStages(size: Int, density: Float) {
    val graph = UndirectedGraphGenerator.generateCoherentGraph(size, density)
    println("Połączenia:")
    graph.allConnections.forEach {
        println("Vertex ${it.first} is connected with vertex ${it.second}")
    }
    graph.colorWithGreedyAlgorithm()
    val greedyChromaticNumber = graph.coloring.chromaticNumber
    println()
    println("Greedy chromatic number: $greedyChromaticNumber")
    graph.coloring.clear()
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    TabuSearch.getTabuSearchSolution(graph, greedyChromaticNumber, 7, reps, 1000)

}

fun solutionCheckTest() {
    val graph = graphFromResource(Files.TXT_RES_LOCALIZATION, "queen6")
    graph.colorWithGreedyAlgorithm()
    graph.coloring.readableSolution.forEach {
        println(it)
    }
}

fun first() {
    val graph = graphFromResource(Files.TXT_RES_LOCALIZATION, "queen6")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedTabuSearchTest(graph, 7, reps, 10000, 5)
}

fun second() {
    val graph = graphFromResource(Files.COL_RES_LOCALIZATION, "le450_5a")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedTabuSearchTest(graph, 7, reps, 7000, 5, 1)
}

fun third() {
    val graph = graphFromResource(Files.COL_RES_LOCALIZATION, "anna")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedTabuSearchTest(graph, 7, reps, 500, 5)
}

fun fourth() {
    val graph = graphFromResource(Files.COL_RES_LOCALIZATION, "miles500")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedTabuSearchTest(graph, 7, reps, 2000, 5)
}

fun fifth() {
    val graph = graphFromResource(Files.COL_RES_LOCALIZATION, "miles1500")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedTabuSearchTest(graph, 7, reps, 1000, 5)
}

fun testWithComputedValues(graph: UndirectedGraph, nTry: Int) {
    val reps = (graph.density * graph.vertexNumber - 1).toInt()
    val maxIterations = graph.vertexNumber * 200
    val tabuSize = 7
    advancedTabuSearchTest(graph, tabuSize, reps, maxIterations, nTry)
}

fun advancedTabuSearchTest(
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
        TabuSearch.optimizationProblem(
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