import java.security.InvalidParameterException
import java.util.*

/**
 * Huffman character, short for a pair of a char. and its frequency
 */
class HuffChar(val content: Char?, val frequency: Double) : Comparable<HuffChar> {

    override fun compareTo(other: HuffChar): Int {
        return this.frequency.compareTo(other.frequency)
    }

    override fun toString(): String {
        return "Char: ${this.content}, Freq: ${this.frequency}"
    }

    constructor(frequency: Double) : this(null, frequency)
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
