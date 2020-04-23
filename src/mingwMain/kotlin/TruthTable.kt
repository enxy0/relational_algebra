class TruthTable private constructor(
    val name: String,
    private val body: String
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is TruthTable -> this.body == other.body
            is String -> this.body == other
            else -> false
        }
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    /**
     * Проверяет является ли данный булев вектор подмножестом другого булевого вектора
     *
     * @param other другой булев вектор для сравнения
     * @return [Boolean] является подмножеством
     */
    fun isSubsetOf(other: TruthTable): Boolean {
        if (this.body.length == other.body.length) {
            for (letter in this.body.indices) {
                if (this.body[letter] == '1' && other.body[letter] == '0')
                    return false
            }
            return true
        } else
            throw Throwable("Нельзя проверить два разных по длине вектора!")
    }

    /**
     * Выводит на экран булев вектор
     */
    fun printVector() {
        print("${this.name} = ")
        println(this.body.split("").filter { it != "" }.joinToString(prefix = "(", postfix = ")"))
    }

    /**
     * Выводит на экран таблицу имстинности
     */
    fun printTable() {
        println("Таблица истинности ${this.name}:")
        when (body.length) {
            4 -> println(prettyTwo(body))
            8 -> println(prettyThree(body))
            16 -> println(prettyFour(body))
            else -> throw Throwable("Было введено не поддерживаемое количество переменных!")
        }
    }

    // Таблица истинности создается через функцию from
    // Передайте булев вектор или логическую фунцкию для 2х, 3х или 4х переменных.
    companion object {
        /**
         * Создает [TruthTable] по логической функции для двух переменных
         *
         * @param name имя булевого вектора/таблицы истинности для вывода.
         * @param fn логическая функция, принимающая 2 аргумента (переменных)
         * @return [String] булев вектор
         */
        fun from(name: String, fn: (Boolean, Boolean) -> Boolean): TruthTable {
            var vector = ""
            for (a in 0..1)
                for (b in 0..1)
                    vector += if (fn(a == 1, b == 1)) "1" else "0"
            return TruthTable(name, vector)
        }

        /**
         * Создает [TruthTable] по логической функции для трех переменных
         *
         * @param name имя булевого вектора/таблицы истинности для вывода.
         * @param fn логическая функция, принимающая 3 аргумента (переменных)
         * @return [String] булев вектор
         */
        fun from(name: String, fn: (Boolean, Boolean, Boolean) -> Boolean): TruthTable {
            var vector = ""
            for (a in 0..1)
                for (b in 0..1)
                    for (c in 0..1)
                        vector += if (fn(a == 1, b == 1, c == 1)) "1" else "0"
            return TruthTable(name, vector)
        }

        /**
         * Создает [TruthTable] по логической функции для четырех переменных
         *
         * @param name имя булевого вектора/таблицы истинности для вывода.
         * @param fn логическая функция, принимающая 4 аргумента (переменных)
         * @return [String] булев вектор
         */
        fun from(name: String, fn: (Boolean, Boolean, Boolean, Boolean) -> Boolean): TruthTable {
            var vector = ""
            for (a in 0..1)
                for (b in 0..1)
                    for (c in 0..1)
                        for (d in 0..1)
                            vector += if (fn(a == 1, b == 1, c == 1, d == 1)) "1" else "0"
            return TruthTable(name, vector)
        }

        /**
         * Создает [TruthTable] из булевого вектора
         *
         * @param name имя булевого вектора/таблицы истинности для вывода.
         * @param vector булев вектор
         * @return [String] булев вектор
         */
        fun from(name: String, vector: String): TruthTable {
            val isPowerOfTwo = (vector.length) and (vector.length - 1) == 0
            if (isPowerOfTwo && vector.length != 0 && vector.length != 1)
                return TruthTable(name, vector)
            else
                throw Throwable("Нельзя создать таблицу истинности для данного вектора. Проверьте его длину на корректность.")
        }

        /**
         * Выводит взаимное расположение множеств (является ли подмножеством, равны ли пожмножества)
         *
         * @param tables - [List] из [TruthTable], которые нужно проверять
         */
        fun printSubsetsEquality(tables: List<TruthTable>) {
            val isSubset: (String, String) -> String = { a, b -> "$a является подмножеством $b" }
            val isNotSubset: (String, String) -> String = { a, b -> "$a не является подмножеством $b" }
            for (tablePos in tables.indices) {
                for (otherTablePos in tablePos + 1 until tables.size) {
                    if (tables[tablePos].isSubsetOf(tables[otherTablePos])) {
                        println(isSubset(tables[tablePos].name, tables[otherTablePos].name))
                        if (tables[tablePos] == tables[otherTablePos])
                            println("${tables[tablePos].name} равно ${tables[otherTablePos].name}")
                    } else {
                        println(isNotSubset(tables[tablePos].name, tables[otherTablePos].name))
                    }
                }
            }
        }
    }


    /**
     * Строит таблицу истинности по булевому вектору для двух переменных
     *
     * @param vectorFn Булев вектор типа [Array] из [Int], содержащий
     * функции
     * упорядоченный набор из чисел типа [Int]
     * @return [String] таблицу истинности, приятную для просмотра
     */
    private fun prettyTwo(vectorFn: String): String {
        var prettyTable = ""
        if (vectorFn.length == 4) {
            prettyTable += "+---+---+---+\n"
            prettyTable += "| a | b | F |\n"
            prettyTable += "+---+---+---+\n"
            var vectorPos = 0
            for (x in 0..1)
                for (y in 0..1)
                    prettyTable += "| $x | $y | ${vectorFn[vectorPos++]} |\n"
            prettyTable += "+---+---+---+"
            return prettyTable
        } else {
            throw Throwable("Для построения таблицы истинности из двух переменных, длина вектора должна равняться четерем.")
        }
    }

    /**
     * Строит таблицу истинности по булевому вектору для трех переменных
     *
     * @param vectorFn Булев вектор типа [Array] из [Int], содержащий
     * функции
     * упорядоченный набор из чисел типа [Int]
     * @return [String] таблицу истинности, приятную для просмотра
     */
    private fun prettyThree(vectorFn: String): String {
        var prettyTable = ""
        if (vectorFn.length == 8) {
            prettyTable += "+---+---+---+---+\n"
            prettyTable += "| a | b | c | F |\n"
            prettyTable += "+---+---+---+---+\n"
            var vectorPos = 0
            for (x in 0..1)
                for (y in 0..1)
                    for (z in 0..1)
                        prettyTable += "| $x | $y | $z | ${vectorFn[vectorPos++]} |\n"
            prettyTable += "+---+---+---+---+"
            return prettyTable
        } else {
            throw Throwable("Для построения таблицы истинности из двух переменных, длина вектора должна равняться восьми.")
        }
    }

    /**
     * Строит таблицу истинности по булевому вектору для четырех переменных
     *
     * @param vectorFn Булев вектор типа [Array] из [Int], содержащий
     * функции
     * упорядоченный набор из чисел типа [Int]
     * @return [String] таблицу истинности, приятную для просмотра
     */
    private fun prettyFour(vectorFn: String): String {
        var prettyTable = ""
        if (vectorFn.length == 16) {
            prettyTable += "+---+---+---+---+---+\n"
            prettyTable += "| a | b | c | d | F |\n"
            prettyTable += "+---+---+---+---+---+\n"
            var vectorPos = 0
            for (a in 0..1)
                for (b in 0..1)
                    for (c in 0..1)
                        for (d in 0..1)
                            prettyTable += "| $a | $b | c | d | ${vectorFn[vectorPos++]} |\n"
            prettyTable += "+---+---+---+---+---+"
            return prettyTable
        } else {
            throw Throwable("Для построения таблицы истинности из двух переменных, длина вектора должна равняться восьми.")
        }
    }
}