import java.io.File
import java.io.FileReader
import java.nio.file.Path
import java.nio.file.Paths
import java.security.InvalidParameterException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by ahmed on 5/20/17.
 */
fun main(args: Array<String>): Unit {
    val pwd = System.getProperty("user.dir")
    var file = Paths.get(pwd, "freq.txt").toString()
    var fileContent = ArrayList<List<String>>()

    if (!args.isEmpty()) {
        file = args[0]
    } else if (File(file).exists()) {

    } else {
        // Dummy input entries
        val arr = arrayListOf("a", "b", "c", "d", "e", "f")
        val freq = arrayListOf("0.05", "0.09", "0.12", "0.13", "0.16", "0.45")
        (0..arr.size - 1).mapTo(fileContent) { listOf(arr[it], freq[it]) }
    }

    val fileReader = FileReader(file)

    fileContent = fileReader.readLines()
            .map { it -> it.split(" ").toList() } as ArrayList

    val map = HashMap<Char, Double>()

    for (entry in fileContent) {
        val letter = entry[0].toCharArray()[0]
        val freq = entry[1].toDouble() * 100
        map.put(letter, freq)
    }

    val huffMap = huffCode(map)
    println(huffMap.toString())
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
 * Builds the Huffman tree
 */
fun Collection<HuffChar>.getHuffTree(): HuffNode {
    if (size == 0) {
        throw InvalidParameterException()
    }

    val pQueue = PriorityQueue<HuffNode>(this.size)
    pQueue.addAll(this.map { HuffNode(it, null, null) })

    while (pQueue.size > 1) {
        val first = pQueue.remove()
        val second = pQueue.remove()

        val newFreq = first.getFrequency() + second.getFrequency()
        val newNode = HuffNode(HuffChar(newFreq), first, second)

        pQueue.add(newNode)
    }

    return pQueue.remove()
}

fun HashMap<Char, Double>.huffListFromMap(): Collection<HuffChar> {
    val nodes = mutableListOf<HuffChar>()
    this.map { HuffChar(it.key, it.value) }
            .toCollection(nodes)

    return nodes
}


/**
 * Alternative function to calculate Huffman coding
 * for a given string
 */
fun huffCode(str: String): HuffNode {

    val list: Collection<HuffChar> = str
            .getCharFrequencies()
            .huffListFromMap()

    return list.getHuffTree()
}

/**
 * Calculates the frequencies of the characters
 * Note: The sum of frequencies will mostly exceed
 * 100 because of the rounding
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

/**
 * Percentage of the number of occurrences in a string
 */
fun Int.asStringPercentage(str: String): Double {
    val percent = (this.toDouble()).div(str.length)
    return percent * 100
}

