/** Misc. utilities */

/**
 * Converts a parsed text file line to a HuffChar
 * text file template: { char freq }
 */
fun List<String>.fileEntryToHuffChar(): HuffChar {
    val letter = this[0].toLowerCase().toCharArray()[0]
    val freq = this[1].toDouble() * 100.0
    return HuffChar(letter, freq)
}

/**
 * Converts a given HashMap of weights to a list
 * to prepare it for insertion in a Huffman tree
 */
fun HashMap<Char, Double>.huffListFromMap(): Collection<HuffChar> {
    val nodes = mutableListOf<HuffChar>()
    this.map { HuffChar(it.key, it.value) }
            .toCollection(nodes)

    return nodes
}

/**
 * Percentage of the number of occurrences in a string
 */
fun Int.asStringPercentage(str: String): Double {
    val percent = (this.toDouble()).div(str.length)
    return percent * 100
}

fun parseFreqFile(lines: List<String>): HashMap<Char, Double> {
    val map = HashMap<Char, Double>()
    val fileContent = lines
            .map { it -> it.split(" ").toList() } as ArrayList

    fileContent
            .map { it.fileEntryToHuffChar() }
            .forEach { map.put(it.content!!, it.frequency) }

    return map
}
