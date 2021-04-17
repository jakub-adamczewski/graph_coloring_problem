import algorithms.BruteForce.getBruteForceChromaticNumber
import utils.Extensions.graphFromTxtFile
import utils.FileLocalizations
import utils.UndirectedGraphGenerator

fun main() {
    val graph1 = graphFromTxtFile(FileLocalizations.EXAMPLE_1)
    val graph2 = graphFromTxtFile(FileLocalizations.EXAMPLE_2)

    println(graph1.getBruteForceChromaticNumber())
    println(graph2.getBruteForceChromaticNumber())

    UndirectedGraphGenerator.generateCoherentGraphWithDensity(10, 0.7F).apply {
        println(vertexNumber)
        println(edgesNumber)
    }
}