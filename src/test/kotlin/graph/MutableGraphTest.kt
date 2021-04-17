package graph

import org.junit.Test

class MutableGraphTest {

    @Test
    fun `connections checks are correct`() {
        val graph: UndirectedGraph = MutableUndirectedGraph(4).apply {
            addConnections(
                1 to 2,
                2 to 3,
                3 to 4,
                4 to 1
            )
        }

        assert(graph.areConnected(1 to 2))
        assert(graph.areConnected(2 to 3))
        assert(graph.areConnected(3 to 4))
        assert(graph.areConnected(4 to 1))
        assert(graph.areNotConnected(2 to 4))
        assert(graph.areNotConnected(1 to 3))
    }

    @Test
    fun `edges count`() {
        val graph: UndirectedGraph = MutableUndirectedGraph(4).apply {
            addConnections(
                1 to 2, 2 to 1,
                2 to 3, 3 to 2,
                3 to 4, 3 to 4,
                4 to 1,
                2 to 4,
            )
        }

        assert(graph.edgesNumber == 5)
    }
}