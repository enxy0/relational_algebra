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

/**
 * Декартово произведение двух [Collection]
 * in: cartesianProduct(listOf(1, 2, 3), listOf(1, 2, 3))
 * out: [(1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 3), (3, 1), (3, 2), (3, 3)]
 * @param c1 Первая [Collection]
 * @param c2 Вторая [Collection]
 * @return [List] из [Pair], где каждый элемент первой [Collection] сопоставлен
 * со всеми элементами второй [Collection]
 */
fun <T, U> cartesianProduct(c1: Collection<T>, c2: Collection<U>): List<Pair<T, U>> {
    return c1.flatMap { first -> c2.map { second -> first to second } }
}