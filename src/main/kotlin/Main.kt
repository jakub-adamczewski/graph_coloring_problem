import algorithms.GreedyAlgorithm.colorWithGreedyAlgorithm
import algorithms.TabuSearch
import graph.UndirectedGraph
import utils.Files
import utils.Util.graphFromResource
import java.util.concurrent.TimeUnit
import kotlin.math.ceil
import kotlin.system.measureTimeMillis

fun main() {
    basicTabuSearchTest(graphFromResource(Files.COL_RES_LOCALIZATION, "miles250"), 8)
//        greedyTests()
}

fun basicTabuSearchTest(graph: UndirectedGraph, optimalColorsNumber: Int) {
    println("Tabu Search test for ${graph.name}")
    val greedyChromaticNumber = graph.colorWithGreedyAlgorithm().coloring.chromaticNumber
    println("Greedy coloring chromatic number: $greedyChromaticNumber")
    graph.coloring.clear()

    val computationsDurationMillis = measureTimeMillis {
        TabuSearch.getTabuSearchSolution(graph, optimalColorsNumber)
    }
    val seconds = TimeUnit.MILLISECONDS.toSeconds(computationsDurationMillis)
    println("Execution duration seconds: $seconds")
}

fun testGraphWithTabuSearch(graph: UndirectedGraph) {
    val greedyChromaticNumber = graph.colorWithGreedyAlgorithm().coloring.chromaticNumber
    println("Greedy coloring chromatic number: $greedyChromaticNumber")
    var jumpValue = ceil(greedyChromaticNumber.toFloat() / 2).toInt()
    var subtract = true

    var colorsNumberToTest: Int = greedyChromaticNumber - jumpValue
    var previouslyTestedColorsNumber: Int = -1

    while (colorsNumberToTest != previouslyTestedColorsNumber) {
        graph.coloring.clear()

        val tabuResult = TabuSearch.getTabuSearchSolution(graph, colorsNumberToTest)
        subtract = tabuResult != null

        previouslyTestedColorsNumber = colorsNumberToTest
        jumpValue = ceil(jumpValue.toFloat() / 2).toInt()
        colorsNumberToTest = if(subtract){
            colorsNumberToTest - jumpValue
        }else {
            colorsNumberToTest + jumpValue
        }
    }
}

fun greedyTests() {
//    Files.allTxtFiles.forEach { fileName ->
//        println("$fileName chromaticNumber: " +
//                "${graphFromResource(Files.TXT_RES_LOCALIZATION, fileName).getGreedyChromaticNumber()}")
//    }
    Files.allColFiles.forEach { fileName ->
        println(
            "$fileName greedyChromaticNumber: " +
                    "${
                        graphFromResource(
                            Files.COL_RES_LOCALIZATION,
                            fileName
                        ).colorWithGreedyAlgorithm().coloring.chromaticNumber
                    }"
        )
    }
}
