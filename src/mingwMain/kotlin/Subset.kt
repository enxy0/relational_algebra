/**
 * Парсит строку из булевых векторов (наборов), записанных в разных форматах, но разделенных запятыми.
 *
 * @param input строка, содержащая булев векторы (наборы) в любом удобном формате, но разделенные запятыми.
 * Примеры:
 * (1001), (1000), (0110)
 * {1001, 1000, 0110}
 * A = {(1001), (1000), (0110)};
 * @return [ArrayList] из булевых векторов (наборов).
 */
fun parse(input: String): ArrayList<String> {
    val regex = Regex("[^0-1,]")
    return regex.replace(input, "").split(',') as ArrayList<String>
}

/**
 * Находит соседние булевы векторы (наборы) и выводит их. Соседними называются такие наборы, которые
 * отличаются на единицу.
 * @param text строка, содержащая булев векторы (наборы) в любом удобном формате, но разделенные запятыми.
 * In: (1001), (1000), (0110) // или {1001, 1000, 0110}
 * Out:
 * Соседние наборы:
 * (1001), (1000)
 */
fun findNeighboursSubsets(text: String) {
    println("Соседние наборы:")
    val subsets = parse(text)
    permutations(subsets, subsets).filter { it.first != it.second }.forEach {
        if (it.first.length == it.second.length) {
            val differences = it.first.foldIndexed(0) { index, acc, item ->
                if (item != it.second[index]) acc + 1 else acc
            }
            if (differences == 1)
                println("($it.first), ($it.second)")
        } else
            throw Throwable("Нельзя сравнить булевы векторы разной длины. ${it.first}.length=${it.first.length},  ${it.second}.length=${it.second.length}.")
    }
}

/**
 * Находит противоположные булевы векторы (наборы) и выводит их.
 *
 * @param text строка, содержащая булев векторы (наборы) в любом удобном формате, но разделенные запятыми.
 * In: (1001), (1000), (0110) // или {1001, 1000, 0110}
 * Out:
 * Противоположные наборы:
 * (1001), (0110)
 */
fun findOppositeSubsets(text: String) {
    println("Противоположные наборы:")
    val subsets = parse(text)
    permutations(subsets, subsets).filter { it.first != it.second }.forEach {
        if (it.first.length == it.second.length) {
            val differences = it.first.foldIndexed(0) { index, acc, item ->
                if (item != it.second[index]) acc + 1 else acc
            }
            if (differences == it.first.length)
                println("($it.first), ($it.second)")
        } else
            throw Throwable("Нельзя сравнить булевы векторы разной длины. ${it.first}.length=${it.first.length},  ${it.second}.length=${it.second.length}.")
    }
}