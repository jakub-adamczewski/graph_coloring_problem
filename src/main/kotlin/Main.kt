import algorithms.GreedyAlgorithm.getGreedyChromaticNumber
import utils.Files
import utils.Util.graphFromResource

fun main() {
    greedyTests()
}

fun greedyTests() {
//    Files.allTxtFiles.forEach { fileName ->
//        println("$fileName chromaticNumber: " +
//                "${graphFromResource(Files.TXT_RES_LOCALIZATION, fileName).getGreedyChromaticNumber()}")
//    }
    Files.allColFiles.forEach { fileName ->
        println("$fileName chromaticNumber: " +
                "${graphFromResource(Files.COL_RES_LOCALIZATION,fileName).getGreedyChromaticNumber()}")
    }
}
