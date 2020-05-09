fun main() {
    /*
    * Объявление (функции загоняются вручную)
    * x ∨ y = x or y (или ||)
    * x&y = x and y (или &&)
    * ¬x = !x (или not)
    * x → y = x implies y
    * x ← y = x converseImplies y
    * x ↑ y = x nand y
    * x ↓ y = x nor y
    * x ∆ y = x symDiffers y
    * x \ y = x differs y
    * x ⊕ y = x xor y
    * x ~ y = x == y
    */

    // Создание таблицы истинности (булевого вектора)
    // с помощью заданной функции
    val truthTableD = TruthTable.from("D", listOf('a', 'b', 'c')) { a, b, c ->
        // (a \ b) -> c
        (a differs b) implies c
    }
    val truthTableE = TruthTable.from("E", listOf('a', 'b', 'c')) { a, b, c ->
        // (a ∆ b) ↑ ¬(a & b)
        (a symDiffers b) nand !(a and b)
    }
    val truthTableF = TruthTable.from("F", listOf('a', 'b', 'c')) { a, b, c ->
        // (a ↓ b) ← 1
        (a nor b) converseImplies true
    }

    // Создание таблицы истинности по заданному булевому вектору
    val truthTableC = TruthTable.from("C", listOf('x', 'y', 'z'), "11111111")
    val truthTableV = TruthTable.from("V", listOf('a', 'b', 'c'), "11111111")

    // Вывод таблицы истинности
    truthTableD.printTable()

    println()

    // Вывод булевого вектора
    truthTableD.printVector()
    truthTableE.printVector()
    truthTableF.printVector()
    truthTableC.printVector()

    println()

    // Нахождение СДНФ и СКНФ
    truthTableD.perfectDisjunctiveNormalForm() // СДНФ
    truthTableD.perfectConjunctiveNormalForm() // СКНФ

    println()
    // Проверяем на взаимное расположение
    println("Взаимное расположение:")
    TruthTable.printSubsetsEquality(
        listOf(
            truthTableD,
            truthTableC,
            truthTableV // Еще один вектор 11111111, но с другим именем для проверки равенства множеств
        )
    )

    println()
    // Нахождение противоположных и соседних наборов
    // Записывать можно в любом удобном формате
    // Главное, чтобы наборы были разделены запятыми
    val subset1 = "{(01100),(10100),(11110),(10011),(00011)};"
    val subset2 = "A = {(01011),(00011),(11101),(11111),(00010)};"
    println(subset1)
    findOppositeSubsets(subset1)
    findNeighboursSubsets(subset1)
    println()
    println(subset2)
    findOppositeSubsets(subset2)
    findNeighboursSubsets(subset2)
}
