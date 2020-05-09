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

fun Boolean.toInt() = if (this) 1 else 0

fun String.wrapWithBrackets() = "($this)"

/**
 * Декартово произведение [Set] из [Boolean] (специально для таблицы истинности)
 */
fun cartesianProduct(vararg sets: Collection<Boolean>): Set<List<Boolean>> =
    when (sets.size) {
        0, 1 -> emptySet()
        else -> sets.fold(listOf(listOf<Boolean>())) { acc, set ->
            acc.flatMap { list -> set.map { element -> list + element } }
        }.toSet()
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