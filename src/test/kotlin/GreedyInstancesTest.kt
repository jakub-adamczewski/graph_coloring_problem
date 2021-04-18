import algorithms.GreedyAlgorithm.getGreedyChromaticNumber
import org.junit.Test
import utils.Util
import utils.Files
import utils.Util.graphFromResource

class GreedyInstancesTest {

    @Test
    fun `example_1`(){
        val graph = graphFromResource(Files.EXAMPLE_1)
        assert(graph.getGreedyChromaticNumber() == 5)
    }

    @Test
    fun `example_2`(){
        val graph = graphFromResource(Files.EXAMPLE_2)
        assert(graph.getGreedyChromaticNumber() == 3)
    }

    @Test
    fun `gc500`(){
        val graph = graphFromResource(Files.GC500)
        assert(graph.getGreedyChromaticNumber() == 90)
    }

    @Test
    fun `gc1000_300013`(){
        val graph = graphFromResource(Files.GC1000_300013)
        assert(graph.getGreedyChromaticNumber() == 155)
    }

    @Test
    fun `gc_26`(){
        val graph = graphFromResource(Files.GC_26)
        assert(graph.getGreedyChromaticNumber() == 31)
    }


    @Test
    fun `le450_5a`(){
        val graph = graphFromResource(Files.LE450_5a)
        assert(graph.getGreedyChromaticNumber() == 14)
    }

    @Test
    fun `miles250`(){
        val graph = graphFromResource(Files.MILES_250)
        assert(graph.getGreedyChromaticNumber() == 9)
    }

    @Test
    fun `queen6`(){
        val graph = graphFromResource(Files.QUEEN_6)
        assert(graph.getGreedyChromaticNumber() == 11)
    }
}