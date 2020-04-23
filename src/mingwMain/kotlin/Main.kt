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
    */

    // Создание таблицы истинности (булевого вектора)
    // с помощью заданной функции
    val truthTableD = TruthTable.from("D") { a, b, c ->
        // (a \ b) -> c
        (a differs b) implies c
    }
    val truthTableE = TruthTable.from("E") { a, b, c ->
        // (a ∆ b) ↑ ¬(a & b)
        (a symDiffers b) nand !(a and b)
    }
    val truthTableF = TruthTable.from("F") { a, b, c ->
        // (a ↓ b) ← 1
        (a nor b) converseImplies true
    }

    // Создание таблицы истинности по заданному булевому вектору
    val truthTableC = TruthTable.from("C", "11111111")
    val truthTableV = TruthTable.from("V", "11111111")

    // Вывод таблицы истинности
    truthTableD.printTable()

    // Вывод булевого вектора
    truthTableD.printVector()
    truthTableE.printVector()
    truthTableF.printVector()
    truthTableC.printVector()

    // Проверяем на взаимное расположение
    TruthTable.printSubsetsEquality(
        listOf(
            truthTableD,
            truthTableC,
            truthTableV // Еще один вектор 11111111, но с другим именем для проверки равенства множеств
        )
    )
}
