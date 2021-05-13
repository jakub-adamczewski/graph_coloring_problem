package utils

import graph.MutableUndirectedGraph
import graph.UndirectedGraph
import java.io.InputStream

object Util {

    fun graphFromResource(resource: String, fileName: String): UndirectedGraph {
        val fileExtension = when (resource) {
            Files.TXT_RES_LOCALIZATION -> Files.TXT
            Files.COL_RES_LOCALIZATION -> Files.COL
            else -> throw RuntimeException("unsupported file type")
        }
        return Util::class.java.getResourceAsStream(resource + fileName + fileExtension).toGraph(fileExtension)
    }

    private fun InputStream.toGraph(fileExtension: String): UndirectedGraph {
        val lines = bufferedReader().lineSequence().toList()
        return when (fileExtension) {
            Files.TXT -> {
                val vertexNumber = lines.first().toInt()
                val connections = lines.filterIndexed { index, _ -> index > 0 }.map { it.twoStringNumbersToIntPair() }
                MutableUndirectedGraph(vertexNumber).apply {
                    addConnections(*connections.toTypedArray())
                }
            }
            Files.COL -> {
                val vertexNumber = lines
                    .first { it.contains("p edge ") }
                    .replace("p edge ", "")
                    .twoStringNumbersToIntPair()
                    .first
                val connections = lines
                    .filter {
                        !it.contains("c ") &&
                                !it.contains("p edge ") &&
                                it.contains("e ")
                    }
                    .map { it.replace("e ", "") }
                    .map { it.twoStringNumbersToIntPair() }
                MutableUndirectedGraph(vertexNumber).apply {
                    addConnections(*connections.toTypedArray())
                }
            }
            else -> throw RuntimeException("unsupported file type")
        }

    }

    private fun String.twoStringNumbersToIntPair(): Pair<Int, Int> {
        val stringNumbers = split(" ")
        return stringNumbers.first().toInt() to stringNumbers.last().toInt()
    }

    fun Pair<Int, Int>.sorted(): Pair<Int, Int> = listOf(first, second).sorted().toPair()

    fun Pair<Int, Int>.sortedDesc(): Pair<Int, Int> = listOf(first, second).sortedDescending().toPair()

    private fun <T> List<T>.toPair(): Pair<T, T> {
        if (this.size != 2) throw RuntimeException("List converted to pair should have size of 2")
        return this[0] to this[1]
    }

    fun throwIfIsZero(vararg args: Int) {
        if (args.any { it <= 0 }) throw IndexOutOfBoundsException("Matrix is indexed starting with 1")
    }

    fun throwIfIsArgsEqual(args: Pair<Int, Int>) {
        if (args.first == args.second) throw RuntimeException("Args can not be equal")
    }
}