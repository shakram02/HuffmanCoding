import java.io.File
import java.io.FileReader
import java.nio.file.Paths
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Converts a given input string and file of frequencies
 * to the equivalent huffman encoding
 */
fun main(args: Array<String>): Unit {
    val test_string = "test string"
    val pwd = System.getProperty("user.dir")

    var file = Paths.get(pwd, "freq.txt").toString()
    var fileContent = ArrayList<String>()
    var frequencyMap = HashMap<Char, Double>()

    if (!args.isEmpty()) {
        file = args[0]
    }

    if (File(file).exists()) {

        val fileReader = FileReader(file)
        fileContent = fileReader.readLines() as ArrayList

    } else {

        // Dummy frequency file
        fileContent = arrayListOf("a 0.05", "b 0.09", "c 0.12",
                "d 0.13", "e 0.16", "f 0.45")

    }

    frequencyMap = parseFreqFile(fileContent.toList())

    // val huffMap = huffCode(test_string)  // Use with string input
    val huffMap = huffCode(frequencyMap) // Use with frequency file input
    println(huffMap.toString())

    println("The encoding of \"$test_string\" is" +
            " ${huffEncode(test_string, huffMap)}")
}

/**
 * Creates a map of the Huffman codes for the given set of
 * characters and frequencies
 */
fun huffCode(weights: HashMap<Char, Double>): HashMap<Char, String> {

    val list = weights.huffListFromMap()
    val root = list.getHuffTree()

    val map = HashMap<Char, String>()
    val builder = StringBuilder()

    // Build table of keys and codes
    val codes = root.getCodes(builder)
    codes.toMap(map)

    return map
}

/**
 * Alternative function to calculate Huffman coding
 * for a given string
 */
fun huffCode(str: String): HashMap<Char, String> {
    return huffCode(str.getCharFrequencies())
}

/**
 * Calculates the frequencies of the characters
 */
fun String.getCharFrequencies(): HashMap<Char, Double> {
    val chars = this.toCharArray()
    val result = HashMap<Char, Double>()

    // Weight of a single occurrence in a string
    val increment = 1.asStringPercentage(this)

    for (char in chars) {
        when (result[char]) {
        // Create new entry
            null -> result[char] = increment
        // Update count
            else -> result[char] = result[char]!! + increment
        }
    }

    return result
}

