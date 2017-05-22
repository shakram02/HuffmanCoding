/**
 * Huffman character, short for a pair of a char. and its frequency
 */
class HuffChar(val content: Char?, val frequency: Int) : Comparable<HuffChar> {

    override fun compareTo(other: HuffChar): Int {
        return this.frequency.compareTo(other.frequency)
    }

    override fun toString(): String {
        return "Char: ${this.content}, Freq: ${this.frequency}"
    }

    constructor(frequency: Int) : this(null, frequency)
}