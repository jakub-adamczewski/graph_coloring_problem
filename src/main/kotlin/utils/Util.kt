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

    fun Pair<Int, Int>.sorted(): Pair<Int,Int> = listOf(first, second).sorted().toPair()

    fun Pair<Int, Int>.sortedDesc(): Pair<Int,Int> = listOf(first, second).sortedDescending().toPair()

    private fun <T> List<T>.toPair(): Pair<T, T> {
        if (this.size != 2) throw RuntimeException("List converted to pair should have size of 2")
        return this[0] to this[1]
    }
}