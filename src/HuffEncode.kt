/**
 * Created by ahmed on 5/22/17.
 */
fun huffEncode(str: String, huffIndex: HashMap<Char, String>): String {
    val builder = StringBuilder()

    for (c in str.toCharArray()) {
        if (huffIndex.containsKey(c)) {
            builder.append(huffIndex[c])
        } else {
            builder.append(c)
        }
    }

    return builder.toString()
}