package graph

import java.io.File

open class Graph(val vertexNumber: Int) {

    protected val matrix = Array(vertexNumber) { BooleanArray(vertexNumber) { false } }

    fun areConnected(connection: Pair<Int, Int>): Boolean = connection.run {
        throwIfIsZero(first, second)
        return matrix[first - 1][second - 1] && matrix[second - 1][first - 1]
    }

    protected fun throwIfIsZero(vararg args: Int) {
        if (args.any { it <= 0 }) throw IndexOutOfBoundsException("Matrix is indexed starting with 1")
    }

    override fun toString(): String {
        return matrix.map { row ->
            row.map { item ->
                if (item) "1" else "0"
            } + "\n"
        }.toString()
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")
            .replace(" ", "")
            .replace("", " ")
    }

    companion object {
        fun fromTxtFile(filePath: String): Graph {
            val inputStream = File(filePath).inputStream()
            val lines = mutableListOf<String>()
            inputStream.bufferedReader().forEachLine { lines.add(it) }

            val vertexNumber = lines.first().toInt()
            val connections = lines.apply { removeFirst() }.map { it.twoStringNumbersToIntPair() }

            return MutableGraph(vertexNumber).apply {
                connections.forEach {
                    addConnection(it)
                }
            }
        }

        private fun String.twoStringNumbersToIntPair(): Pair<Int, Int> {
            val stringNumbers = split(" ")
            return stringNumbers.first().toInt() to stringNumbers.last().toInt()
        }
    }
}