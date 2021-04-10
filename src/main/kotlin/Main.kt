import algorithms.BruteForce.getBruteForceChromaticNumber
import graph.Graph
import utils.Extensions.graphFromTxtFile
import utils.FileLocalizations

fun main() {
    val graph1 = graphFromTxtFile(FileLocalizations.EXAMPLE_1)
    val graph2 = graphFromTxtFile(FileLocalizations.EXAMPLE_2)

    println(graph1.getBruteForceChromaticNumber())
    println(graph2.getBruteForceChromaticNumber())
}