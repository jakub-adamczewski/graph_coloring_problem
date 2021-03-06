import algorithms.GreedyAlgorithm.colorWithGreedyAlgorithm
import org.junit.Test
import utils.Files
import utils.Util.graphFromResource

class GreedyInstancesTest {

    @Test
    fun `example_1`(){
        val graph = graphFromResource(Files.TXT_RES_LOCALIZATION, Files.allTxtFiles[0])
        assert(graph.colorWithGreedyAlgorithm().coloring.chromaticNumber == 5)
    }

    @Test
    fun `example_2`(){
        val graph = graphFromResource(Files.TXT_RES_LOCALIZATION,Files.allTxtFiles[1])
        assert(graph.colorWithGreedyAlgorithm().coloring.chromaticNumber == 3)
    }

    @Test
    fun `gc500`(){
        val graph = graphFromResource(Files.TXT_RES_LOCALIZATION,Files.allTxtFiles[2])
        assert(graph.colorWithGreedyAlgorithm().coloring.chromaticNumber == 90)
    }

    @Test
    fun `gc1000_300013`(){
        val graph = graphFromResource(Files.TXT_RES_LOCALIZATION,Files.allTxtFiles[3])
        assert(graph.colorWithGreedyAlgorithm().coloring.chromaticNumber == 155)
    }

    @Test
    fun `gc_26`(){
        val graph = graphFromResource(Files.TXT_RES_LOCALIZATION,Files.allTxtFiles[4])
        assert(graph.colorWithGreedyAlgorithm().coloring.chromaticNumber == 31)
    }


    @Test
    fun `le450_5a`(){
        val graph = graphFromResource(Files.TXT_RES_LOCALIZATION,Files.allTxtFiles[5])
        assert(graph.colorWithGreedyAlgorithm().coloring.chromaticNumber == 14)
    }

    @Test
    fun `miles250`(){
        val graph = graphFromResource(Files.TXT_RES_LOCALIZATION,Files.allTxtFiles[6])
        assert(graph.colorWithGreedyAlgorithm().coloring.chromaticNumber == 9)
    }

    @Test
    fun `queen6`(){
        val graph = graphFromResource(Files.TXT_RES_LOCALIZATION,Files.allTxtFiles[7])
        assert(graph.colorWithGreedyAlgorithm().coloring.chromaticNumber == 11)
    }
}