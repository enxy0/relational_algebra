fun parse(input: String): ArrayList<String> {
    val regex = Regex("[^0-1,]")
    return regex.replace(input, "").split(',') as ArrayList<String>
}

fun findNeighboursSubsets(text: String) {
    println("Соседние наборы:")
    val subsets = parse(text)
    val permutations = permutations(subsets, subsets).filter { it.first != it.second }
    for ((first, second) in permutations) {
        if (first.length == second.length) {
            val differences = first.foldIndexed(0) { index, differences, element ->
                if (element != second[index])
                    differences + 1
                else
                    differences
            }
            if (differences == 1)
                println("($first), ($second)")
        } else {
            throw Throwable("Нельзя сравнить булевы векторы разной длины. ${first}.length=${first.length},  ${second}.length=${second.length}.")
        }
    }
}
fun findOppositeSubsets(text: String) {
    println("Противоположные наборы:")
    val subsets = parse(text)
    val permutations = permutations(subsets, subsets).filter { it.first != it.second }
    for ((first, second) in permutations) {
        if (first.length == second.length) {
            val differences = first.foldIndexed(0) { index, differences, element ->
                if (element != second[index])
                    differences + 1
                else
                    differences
            }
            if (differences == second.length)
                println("($first), ($second)")
        } else {
            throw Throwable("Нельзя сравнить булевы векторы разной длины. ${first}.length=${first.length},  ${second}.length=${second.length}.")
        }
    }
}