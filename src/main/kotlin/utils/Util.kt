package utils

import graph.MutableUndirectedGraph
import graph.UndirectedGraph
import utils.Files.RES_LOCALIZATION
import java.io.File
import java.io.InputStream

object Util {

    fun graphFromResource(fileName: String): UndirectedGraph {
        return Util::class.java.getResourceAsStream(RES_LOCALIZATION + fileName).toGraph()
    }

    fun graphFromTxtFile(filePath: String): UndirectedGraph {
        return File(filePath).inputStream().toGraph()
    }

    private fun InputStream.toGraph(): UndirectedGraph {
        val lines = bufferedReader().lineSequence().toList()
        val vertexNumber = lines.first().toInt()
        val connections = lines.filterIndexed { index, _ -> index > 0 }.map { it.twoStringNumbersToIntPair() }

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