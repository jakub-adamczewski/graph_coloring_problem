package utils

import graph.UndirectedGraph
import graph.MutableUndirectedGraph
import java.io.File

object Extensions {

    fun graphFromTxtFile(filePath: String): UndirectedGraph {
        val inputStream = File(filePath).inputStream()
        val lines = mutableListOf<String>()
        inputStream.bufferedReader().forEachLine { lines.add(it) }

        val vertexNumber = lines.first().toInt()
        val connections = lines.apply { removeFirst() }.map { it.twoStringNumbersToIntPair() }

        return MutableUndirectedGraph(vertexNumber).apply {
            connections.forEach {
                addConnection(it)
            }
        }
    }

    private fun String.twoStringNumbersToIntPair(): Pair<Int, Int> {
        val stringNumbers = split(" ")
        return stringNumbers.first().toInt() to stringNumbers.last().toInt()
    }

    fun <T> Array<T>.printIfDebug(debug: Boolean) {
        if (debug) {
            forEach { print(it) }
            println()
        }
    }

    fun <T> Iterable<T>.printIfDebug(debug: Boolean) {
        if (debug) {
            forEach { print(it) }
            println()
        }
    }

}