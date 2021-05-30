package algorithms

import graph.UndirectedGraph
import java.util.*
import kotlin.math.ceil
import kotlin.random.Random

object TabuSearch {

    fun optimizationProblem(
        graph: UndirectedGraph, nTry: Int, startingColorsNumber: Int, tabuSize: Int, reps: Int, maxIterations: Int
    ) {
        val checkedChromaticNumbers = mutableListOf<Int>()
        var currentColorsNumber = ceil(startingColorsNumber / 2f).toInt()
        var jumpValue = ceil(startingColorsNumber / 2f).toInt()
        var chromaticNumber = startingColorsNumber

        for (i in 0 until nTry) {
            val newGraph = copy(graph)
            val newSolution = getTabuSearchSolution(newGraph, currentColorsNumber, tabuSize, reps, maxIterations)
            checkedChromaticNumbers.add(currentColorsNumber)
            jumpValue = if (ceil(jumpValue / 2f).toInt() == 0) 1 else ceil(jumpValue / 2f).toInt()

            if (newSolution != null) {
                chromaticNumber = currentColorsNumber
                currentColorsNumber -= jumpValue
            } else {
                currentColorsNumber += jumpValue
            }
            if (checkedChromaticNumbers.contains(currentColorsNumber)) {
                break
            }
        }
        println("Tabu Search optimum chromatic number is: $chromaticNumber")

    }

    fun getTabuSearchSolution(
        graph: UndirectedGraph, colorsNumber: Int, tabuSize: Int, reps: Int, maxIterations: Int
    ): UndirectedGraph? {
        var minimumConflictCount = 10_000
        println()
        println("Checking coloring for $colorsNumber")
//        tabu is Deque of pairs of nodes with new colors (both indexed starting from 1)
        val tabu = ArrayDeque<Pair<Int, Int>>()
        val aspirationLevel = mutableMapOf<Int, Int>()

        var solution = graph.colorRandomly(colorsNumber)

        var conflictCount: Int? = null
        for (i in 0..maxIterations) {
            val moveCandidates = solution.wrongConnections.flatMap { it.toList() }.distinct()
            conflictCount = solution.wrongConnections.size
            if (conflictCount == 0) {
                println(",$conflictCount")
                println("ConflictCount is 0")
                break
            }

            var newSolution: UndirectedGraph? = null
            var vertexToMove: Int? = null
            for (r in 0 until reps) {
                vertexToMove = moveCandidates.random()

                val newColor = randomColor(colorsNumber)

                newSolution = copy(solution)
                newSolution.coloring.color(vertexToMove, newColor)

                val newConflictsCount = newSolution.wrongConnections.size
                if (newConflictsCount < conflictCount) {
                    if (newConflictsCount <= aspirationLevel.getOrDefault(conflictCount, conflictCount - 1)) {
                        aspirationLevel[conflictCount] = newConflictsCount - 1

                        if (tabu.contains(vertexToMove to newColor)) {
                            tabu.remove(vertexToMove to newColor)
                        }
                    } else {
                        if (tabu.contains(vertexToMove to newColor)) {
                            continue
                        }
                    }
                    break
                }
            }

            tabu.add(vertexToMove!! to solution.coloring.getColor(vertexToMove))
            if (tabu.size > tabuSize) {
                tabu.removeLast()
            }

            solution = newSolution!!

            if (conflictCount < minimumConflictCount) minimumConflictCount = conflictCount
            if (i > 0 && i % 10 == 0) println(" - iteration: $i, minimumConflictCount: $minimumConflictCount")
            print(",$conflictCount")
        }

        return if (conflictCount!! == 0) {
            println("Coloring for $colorsNumber, chromaticNumber: ${solution.coloring.chromaticNumber}")
            println()
            solution
        } else {
            println()
            println("No coloring for $colorsNumber")
            println()
            null
        }
    }

    private fun UndirectedGraph.colorRandomly(colorsNumber: Int): UndirectedGraph {
        return apply {
            (1..vertexNumber).forEach {
                coloring.color(it, randomColor(colorsNumber))
            }
        }
    }

    private fun copy(oldGraph: UndirectedGraph): UndirectedGraph {
        return UndirectedGraph(oldGraph.vertexNumber, oldGraph.name).apply {
            addConnections(*oldGraph.allConnections.toTypedArray())
            coloring.color(oldGraph.coloring.currentSolution)
        }
    }

    private fun randomColor(to: Int): Int {
        return Random.nextInt(1, to + 1)
    }
}