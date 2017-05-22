import java.security.InvalidParameterException
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by ahmed on 5/20/17.
 */
fun main(args: Array<String>): Unit {
    val map = HashMap<Char, Int>()

    // Input entries
    val arr = charArrayOf('a', 'b', 'c', 'd', 'e', 'f')
    val freq = intArrayOf(5, 9, 12, 13, 16, 45)

    for (i in 0..arr.size - 1) {
        map.put(arr[i], freq[i])
    }

    val huffMap = huffCode(map)
    println(huffMap.toString())
}

/**
 * Creates a map of the Huffman codes for the given set of
 * characters and frequencies
 */
fun huffCode(weights: HashMap<Char, Int>): HashMap<Char, String> {

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

fun HashMap<Char, Int>.huffListFromMap(): Collection<HuffChar> {
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
fun String.getCharFrequencies(): HashMap<Char, Int> {
    val chars = this.toCharArray()
    val result = HashMap<Char, Int>()

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
fun Int.asStringPercentage(str: String): Int {
    val percent = (this.toDouble()).div(str.length)
    return Math.round(percent * 100).toInt()
}

