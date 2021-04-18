package utils

import graph.UndirectedGraph
import java.io.File

object TxtGCPInstancesGenerator {

    fun UndirectedGraph.generateTxtGCPInstance(fileName: String): Boolean =
        File(fileName).apply {
            printWriter().use { writer ->
                writer.println("$vertexNumber")
                allConnections.forEach { connection ->
                    writer.println("${connection.first} ${connection.second}")
                }
            }
        }.createNewFile()
}