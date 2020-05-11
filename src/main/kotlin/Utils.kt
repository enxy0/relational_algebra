// IMPLY (->)
infix fun Boolean.implies(other: Boolean) = !this or other

// NOT-AND (↑)
infix fun Boolean.nand(other: Boolean) = !(this and other)

// NOR (↓)
infix fun Boolean.nor(other: Boolean) = (!this) and (!other)

// NOT-OR (←)
infix fun Boolean.converseImplies(other: Boolean) = !other or this

// Difference (\)
infix fun Boolean.differs(other: Boolean) = this and !other

// Symmetric Difference (∆)
infix fun Boolean.symDiffers(other: Boolean) = (this and !other) or (!this and other)

fun Int.toBoolean() = this == 1

fun Char.toBoolean() = this.toString().toInt().toBoolean()

fun Boolean.toInt() = if (this) 1 else 0

fun String.wrapWithBrackets() = "($this)"

/**
 * Декартово произведение
 */
fun<T> cartesianProduct(collection: Collection<T>, repeat: Int): Set<List<T>> {
    val collections: ArrayList<Collection<T>> = arrayListOf()
    for (i in 0 until repeat) collections.add(collection)
    return when (collections.size) {
        0, 1 -> emptySet()
        else -> collections.fold(listOf(listOf<T>())) { acc, set ->
            acc.flatMap { list -> set.map { element -> list + element } }
        }.toSet()
    }
}

/**
 * Перестановки двух [Collection]
 */
fun <T, U> permutations(c1: Collection<T>, c2: Collection<U>): List<Pair<T, U>> {
    val permutations = arrayListOf<Pair<T, U>>()
    for ((c1Index, c1Value) in c1.iterator().withIndex())
        for (c2Index in c1Index until c2.size)
            permutations.add(c1Value to c2.asIterable().elementAt(c2Index))
    return permutations
}