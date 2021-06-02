import algorithms.GreedyAlgorithm.colorWithGreedyAlgorithm
import algorithms.TabuSearch
import graph.UndirectedGraph
import utils.Files
import utils.Util.graphFromResource
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

fun main() {
//    first()
//    second()
//    third()
//    fourth()
    fifth()
}

fun first(){
    val graph = graphFromResource(Files.TXT_RES_LOCALIZATION, "queen6")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedTabuSearchTest(graph, 7, reps, 10000, 5)
}

fun second(){
    val graph = graphFromResource(Files.COL_RES_LOCALIZATION, "le450_5a")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedTabuSearchTest(graph, 7, reps, 7000, 5)
}

fun third(){
    val graph = graphFromResource(Files.COL_RES_LOCALIZATION, "anna")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedTabuSearchTest(graph, 7, reps, 500, 5)
}

fun fourth(){
    val graph = graphFromResource(Files.COL_RES_LOCALIZATION, "miles500")
    val reps = (graph.density * (graph.vertexNumber - 1)).toInt()
    advancedTabuSearchTest(graph, 7, reps, 2000, 5)
}

fun fifth(){
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

fun testingFunction() {
//    Czytanie grafu z pliku
    val graphToTest = graphFromResource(Files.TXT_RES_LOCALIZATION, "queen6")
//    Generowanie grafu poprzez generator
//    val graphToTest = UndirectedGraphGenerator.generateCoherentGraph(30, 0.7f)

//    Drukowanie poćzątkowych danych o grafie
    println("Początkowe dane o grafie: ")
    println("Rozmiar: ${graphToTest.vertexNumber}")
    println("Połączenia: ")
    graphToTest.allConnections.forEach {
        println("${it.first} z ${it.second}")
    }

//    Bazowy test, z wypisaniem kolorowania grafu
//    val coloredGraph = basicTabuSearchTest(graphToTest, 7, 7, 100, 10000)
//    if (coloredGraph != null){
//        println("Graf jest pokolorowany na ${coloredGraph.coloring.chromaticNumber} kolorów.")
//        coloredGraph.coloring.currentSolution.forEachIndexed { index, i ->
//            println("Vertex ${index + 1} jest pokolorowany kolorem $i")
//        }
//    }else {
//        println("Nie ma kolorowania dla tej ilosci kolorów.")
//    }

//    Zaawansowany test
    advancedTabuSearchTest(graphToTest, 7, 100, 5000, 5)
}

fun advancedTabuSearchTest(graph: UndirectedGraph, tabuSize: Int, reps: Int, maxIterations: Int, nTry: Int) {
    println("Tabu Search optimization test for ${graph.name}")
    val greedyChromaticNumber = graph.colorWithGreedyAlgorithm().coloring.chromaticNumber
    println("Greedy coloring chromatic number: $greedyChromaticNumber")
    graph.coloring.clear()

    val computationsDurationMillis = measureTimeMillis {
        TabuSearch.optimizationProblem(graph, nTry, greedyChromaticNumber, tabuSize, reps, maxIterations)
    }
    val seconds = TimeUnit.MILLISECONDS.toSeconds(computationsDurationMillis)
    println("Execution duration seconds: $seconds")
}

fun basicTabuSearchTest(
    graph: UndirectedGraph,
    colorsNumberToTest: Int,
    tabuSize: Int,
    reps: Int,
    maxIterations: Int
): UndirectedGraph? {
    println("Tabu Search test for ${graph.name}")
    val greedyChromaticNumber = graph.colorWithGreedyAlgorithm().coloring.chromaticNumber
    println("Greedy coloring chromatic number: $greedyChromaticNumber")
    graph.coloring.clear()

    var coloredGraph: UndirectedGraph?
    val computationsDurationMillis = measureTimeMillis {
        coloredGraph = TabuSearch.getTabuSearchSolution(graph, colorsNumberToTest, tabuSize, reps, maxIterations)
    }
    println("Time: $computationsDurationMillis")
    return coloredGraph
}