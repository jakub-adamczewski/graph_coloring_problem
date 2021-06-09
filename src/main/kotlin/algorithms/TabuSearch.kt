package algorithms

import graph.UndirectedGraph
import java.util.*
import kotlin.math.ceil
import kotlin.random.Random

object TabuSearch {

    fun optimizationProblem(
        graph: UndirectedGraph,
        nTry: Int,
        startingColorsNumber: Int,
        tabuSize: Int,
        reps: Int,
        maxIterations: Int,
        maxExecutionTimeMinutes: Int
    ): Int {
        val maxExecutionTimeMillis = maxExecutionTimeMinutes * 60 * 1000
        val endTimeMillis = System.currentTimeMillis() + maxExecutionTimeMillis

        val checkedChromaticNumbers = mutableListOf<Int>()
        var currentColorsNumber = ceil(startingColorsNumber / 2f).toInt()
        var jumpValue = ceil(startingColorsNumber / 2f).toInt()
        var chromaticNumber = startingColorsNumber

        var newSolution: UndirectedGraph? = null
        for (i in 0 until nTry) {
            if (System.currentTimeMillis() > endTimeMillis) break

            val newGraph = copy(graph)
            newSolution =
                getTabuSearchSolution(newGraph, currentColorsNumber, tabuSize, reps, maxIterations, endTimeMillis)
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
        return chromaticNumber
    }

    fun getTabuSearchSolution(
        graph: UndirectedGraph,
        colorsNumber: Int,
        tabuSize: Int,
        reps: Int,
        maxIterations: Int,
        endTimeMillis: Long? = null
    ): UndirectedGraph? {
        val checkStages = true
        var stepsCount = 1

        println("colorsNumber: $colorsNumber, tabuSize: $tabuSize, reps: $reps, maxIterations: $maxIterations")
        var minimumConflictCount = 10_000
        var iterationInWhichMinWasAssigned = -1
        println()
        println("Checking coloring for $colorsNumber")
//        tabu is Deque of pairs of nodes with new colors (both indexed starting from 1)
        val tabu = ArrayDeque<Pair<Int, Int>>()
        val aspirationLevel = mutableMapOf<Int, Int>()

        var solution = graph.colorRandomly(colorsNumber)

        var conflictCount: Int? = null

        for (i in 0..maxIterations) {


            if (endTimeMillis != null && System.currentTimeMillis() > endTimeMillis) {
                println()
                println("End of time")
                break
            }

            val moveCandidates = solution.wrongConnections.flatMap { it.toList() }.distinct()
            conflictCount = solution.wrongConnections.size

            if (checkStages) {
                println()
                if(stepsCount == 1){
                    println("Faza $stepsCount ukazuje losowe kolorowanie grafu, w kolejnych fazach to rozwiązanie jest ulepszane")
                }
                println("---------- Faza ${stepsCount++} --------------------------------------------------------")
                println("Liczba konfliktów: $conflictCount")
                println("Tabu:")
                tabu.forEachIndexed { index, pair -> println("$index: vertex: ${pair.first}, color: ${pair.second}") }
                println("Coloring:")
                solution.coloring.readableSolution.forEach { println(it) }
                println()
            }

            if (conflictCount == 0) {
                println("ConflictCount is $conflictCount")
                break
            }

            var newSolution: UndirectedGraph? = null
            var vertexToMove: Int? = null

            for (r in 0 until reps) {
                vertexToMove = moveCandidates.random()

//                val newColor = randomColor(colorsNumber)
                val newColor =
                    solution.coloring.getNeighboursColors(solution.getNeighbours(vertexToMove))
                        .firstAvailableColor(colorsNumber) ?: randomColor(colorsNumber)

                newSolution = copy(solution)
                newSolution.coloring.color(vertexToMove, newColor)
                if(checkStages){
                    println()
                    println("W następnej fazie kolorujemy vertex $vertexToMove na kolor $newColor")
                }

//                if(checkStages){
//                    println()
//                    println("Kolorujemy vertex $vertexToMove na kolor $newColor")
//                }

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

            if (conflictCount < minimumConflictCount) {
                minimumConflictCount = conflictCount
            }
            if(!checkStages){
                if (i > 0 && i % 10 == 0) println(" - iteration: $i, minimumConflictCount: $minimumConflictCount")
                print(",$conflictCount")
            }
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