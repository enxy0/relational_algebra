import kotlin.math.log2

@Suppress("DataClassPrivateConstructor")
data class TruthTable private constructor(
    val name: Char,
    private val varNames: List<Char>,
    private val vector: String,
    private val table: List<Pair<List<Boolean>, Boolean>>
) {
    // Таблица истинности создается через функцию build
    // Передайте булев вектор или логическую фунцкию для 2х, 3х или 4х переменных.
    companion object {
        /**
         * Создает [TruthTable] из булевого вектора
         * @param name имя для логической функции
         * @param varNames [List] имена для переменных таблицы истинности (нужны для ее вывода). Пример: x, y, z, ...
         * @param fn логическая функция с 2 параметрами
         */
        fun build(name: Char, varNames: List<Char>, fn: (Boolean, Boolean) -> Boolean): TruthTable {
            var vector = ""
            val table = cartesianProduct(setOf(false, true), 2).map { vars ->
                val fnVal = fn(vars[0], vars[1])
                vector += if (fnVal) "1" else "0"
                vars to fnVal
            }
            return TruthTable(name, varNames, vector, table)
        }

        /**
         * Создает [TruthTable] из булевого вектора
         * @param name имя для логической функции
         * @param varNames [List] имена для переменных таблицы истинности (нужны для ее вывода). Пример: x, y, z, ...
         * @param fn логическая функция с 3 параметрами
         */
        fun build(name: Char, varNames: List<Char>, fn: (Boolean, Boolean, Boolean) -> Boolean): TruthTable {
            var vector = ""
            val table = cartesianProduct(setOf(false, true), 3).map { vars ->
                val fnVal = fn(vars[0], vars[1], vars[2])
                vector += if (fnVal) "1" else "0"
                vars to fnVal
            }
            return TruthTable(name, varNames, vector, table)
        }

        /**
         * Создает [TruthTable] из булевого вектора
         * @param name имя для логической функции
         * @param varNames [List] имена для переменных таблицы истинности (нужны для ее вывода). Пример: x, y, z, ...
         * @param fn логическая функция с 4 параметрами
         */
        fun build(name: Char, varNames: List<Char>, fn: (Boolean, Boolean, Boolean, Boolean) -> Boolean): TruthTable {
            var vector = ""
            val table = cartesianProduct(setOf(false, true), 4).map { vars ->
                val fnVal = fn(vars[0], vars[1], vars[2], vars[3])
                vector += if (fnVal) "1" else "0"
                vars to fnVal
            }
            return TruthTable(name, varNames, vector, table)
        }

        /**
         * Создает [TruthTable] из булевого вектора
         * @param name имя для логической функции
         * @param varNames [List] имена для переменных таблицы истинности (нужны для ее вывода). Пример: x, y, z, ...
         * @param vector булев вектор. Пример: "10001111".
         */
        fun build(name: Char, varNames: List<Char>, vector: String): TruthTable {
            val isPowerOfTwo = (vector.length) and (vector.length - 1) == 0
            if (isPowerOfTwo && vector.length >= 4) {
                val repeat = log2(vector.length.toDouble()).toInt()
                val table = cartesianProduct(setOf(false, true), repeat).mapIndexed { index, it ->
                    it to vector[index].toBoolean()
                }
                return TruthTable(name, varNames, vector, table)
            } else
                throw Throwable("Нельзя создать таблицу истинности для данного вектора. Проверьте его длину на корректность.")
        }

        /**
         * Выводит взаимное расположение множеств (является ли подмножеством, равны ли пожмножества)
         */
        fun printSubsetsEquality(tables: List<TruthTable>) {
            cartesianProduct(tables, 2)
                .filter { it[0].name != it[1].name }
                .forEach {
                    if (it[0] == it[1])
                        println("${it[0].name} ⊆ ${it[1].name}") // first ⊂ second and first = second
                    else if (it[0].isSubsetOf(it[1]))
                        println("${it[0].name} ⊂ ${it[1].name}")
//                    else
//                        println(("${it[0].name} ⊄ ${it[1].name}"))
                }
        }
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is TruthTable -> this.table == other.table
            is String -> this.name == other
            else -> false
        }
    }

    override fun hashCode(): Int = super.hashCode()

    fun perfectDisjunctiveNormalForm() {
        var pdnf = ""
        this.table.forEach { (vars, fnVal) ->
            if (fnVal) {
                pdnf += vars.mapIndexed { index, it -> if (it) this.varNames[index] else "¬${this.varNames[index]}" }
                    .joinToString(separator = " & ")
                    .wrapWithBrackets()
                    .plus(" ⋁ ")
            }
        }
        println("СДНФ: ${pdnf.dropLast(2)}")
    }

    fun perfectConjunctiveNormalForm() {
        var pcnf = ""
        this.table.forEach { (vars, fnVal) ->
            if (!fnVal) {
                pcnf += vars.mapIndexed { index, it -> if (!it) this.varNames[index] else "¬${this.varNames[index]}" }
                    .joinToString(separator = " ⋁ ")
                    .wrapWithBrackets()
                    .plus(" & ")
            }
        }
        println("СКНФ: ${pcnf.dropLast(2)}")
    }

    /**
     * Проверяет является ли данный булев вектор подмножестом другого булевого вектора
     */
    fun isSubsetOf(other: TruthTable): Boolean {
        if (this.vector.length == other.vector.length) {
            for (letter in this.vector.indices) {
                if (this.vector[letter] == '1' && other.vector[letter] == '0')
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
        println(this.vector.split("").filter { it != "" }.joinToString(prefix = "(", postfix = ")"))
    }

    /**
     * Выводит таблицу истинности
     */
    fun printTable() {
        val printDivider = { println("+---".repeat(this.varNames.size + 1).plus("+")) }

        printDivider()
        // Prints variable names and Function (Table) name
        println(
            this.varNames.joinToString(separator = " | ", prefix = "| ", postfix = " | ")
                .plus(name)
                .plus(" |")
        )
        printDivider()
        this.table.forEach { (vars, fnVal) ->
            println(
                vars.map(Boolean::toInt)
                    .joinToString(separator = " | ", prefix = "| ", postfix = " | ")
                    .plus(fnVal.toInt())
                    .plus(" |")
            )
        }
        printDivider()
    }
}