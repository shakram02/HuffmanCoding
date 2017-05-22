class HuffNode(var huffChar: HuffChar,
               var leftChild: HuffNode?, var rightChild: HuffNode?) : Comparable<HuffNode> {
    override fun compareTo(other: HuffNode): Int {
        return this.huffChar.compareTo(other.huffChar)
    }

    fun getFrequency(): Double {
        return this.huffChar.frequency
    }

    override fun toString(): String {
        return "${this.huffChar}"
    }
}

fun HuffNode.getCodes(oldCode: StringBuilder): List<Pair<Char, String>> {
    val codes = ArrayList<Pair<Char, String>>()
    val parentCode = oldCode.toString()

    // Content of the huff char is null in parent tree nodes
    if (this.huffChar.content != null) {
        codes.add(Pair(this.huffChar.content!!, oldCode.toString()))
    }

    if (this.leftChild != null) {
        oldCode.append("0")
        val childCodes = this.leftChild!!.getCodes(oldCode)
        codes.addAll(childCodes)
    }

    // When moving from a left child to a right child,
    // reset the code to remove the codes added in the lower subtrees

    oldCode.setLength(0)    // Clear the string builder
    oldCode.append(parentCode)

    if (this.rightChild != null) {
        oldCode.append("1")
        val childCodes = this.rightChild!!.getCodes(oldCode)
        codes.addAll(childCodes)
    }

    return codes
}
