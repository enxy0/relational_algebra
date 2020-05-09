import kotlin.math.log2

class TruthTable private constructor(
    val name: Char,
    private val varNames: List<Char>,
    private val vector: String,
    private val table: List<Pair<List<Boolean>, Boolean>>
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is TruthTable -> this.table == other.table
            is String -> this.name == other
            else -> false
        }
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

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

    // Таблица истинности создается через функцию from
    // Передайте булев вектор или логическую фунцкию для 2х, 3х или 4х переменных.
    companion object {
        /**
         * Создает [TruthTable] по логической функции для двух переменных
         */
        fun from(name: Char, varNames: List<Char>, fn: (Boolean, Boolean) -> Boolean): TruthTable {
            var vector = ""
            val table = arrayListOf<Pair<ArrayList<Boolean>, Boolean>>()
            for (a in 0..1)
                for (b in 0..1) {
                    table.add(arrayListOf(a.toBoolean(), b.toBoolean()) to fn(a.toBoolean(), b.toBoolean()))
                    vector += if (fn(a.toBoolean(), b.toBoolean())) "1" else "0"
                }
            return TruthTable(name, varNames, vector, table)
        }

        /**
         * Создает [TruthTable] по логической функции для трех переменных
         */
        fun from(name: Char, varNames: List<Char>, fn: (Boolean, Boolean, Boolean) -> Boolean): TruthTable {
            var vector = ""
            val table = arrayListOf<Pair<ArrayList<Boolean>, Boolean>>()
            for (a in 0..1)
                for (b in 0..1)
                    for (c in 0..1) {
                        table.add(
                            arrayListOf(a.toBoolean(), b.toBoolean(), c.toBoolean()) to fn(
                                a.toBoolean(),
                                b.toBoolean(),
                                c.toBoolean()
                            )
                        )
                        vector += if (fn(a.toBoolean(), b.toBoolean(), c.toBoolean())) "1" else "0"
                    }
            return TruthTable(name, varNames, vector, table)
        }

        /**
         * Создает [TruthTable] по логической функции для четырех переменных
         */
        fun from(name: Char, varNames: List<Char>, fn: (Boolean, Boolean, Boolean, Boolean) -> Boolean): TruthTable {
            var vector = ""
            val table = arrayListOf<Pair<ArrayList<Boolean>, Boolean>>()
            for (a in 0..1)
                for (b in 0..1)
                    for (c in 0..1)
                        for (d in 0..1) {
                            table.add(
                                arrayListOf(a.toBoolean(), b.toBoolean(), c.toBoolean(), d.toBoolean()) to fn(
                                    a.toBoolean(),
                                    b.toBoolean(),
                                    c.toBoolean(),
                                    d.toBoolean()
                                )
                            )
                            vector += if (fn(a.toBoolean(), b.toBoolean(), c.toBoolean(), d.toBoolean())) "1" else "0"
                        }
            return TruthTable(name, varNames, vector, table)
        }

        /**
         * Создает [TruthTable] из булевого вектора
         */
        fun from(name: Char, varNames: List<Char>, vector: String): TruthTable {
            val isPowerOfTwo = (vector.length) and (vector.length - 1) == 0
            if (isPowerOfTwo && vector.length >= 4) {
                val product = when (vector.length) {
                    4 -> cartesianProduct(setOf(false, true), setOf(false, true))
                    8 -> cartesianProduct(setOf(false, true), setOf(false, true), setOf(false, true))
                    16 -> cartesianProduct(
                        setOf(false, true),
                        setOf(false, true),
                        setOf(false, true),
                        setOf(false, true)
                    )
                    else -> throw Throwable("Введено неподдерживаемое количество переменных, равное: ${log2(vector.length.toDouble())}")
                }
                val table = product.mapIndexed { index, it -> it to vector[index].toBoolean() }
                return TruthTable(name, varNames, vector, table)
            } else
                throw Throwable("Нельзя создать таблицу истинности для данного вектора. Проверьте его длину на корректность.")
        }

        /**
         * Выводит взаимное расположение множеств (является ли подмножеством, равны ли пожмножества)
         */
        fun printSubsetsEquality(tables: List<TruthTable>) {
            tables.flatMap { first -> tables.map { second -> first to second } }
                .filter { it.first.name != it.second.name }
                .forEach {
                    if (it.first == it.second)
                        println("${it.first.name} ⊆ ${it.second.name}") // first ⊂ second and first = second
                    else if (it.first.isSubsetOf(it.second))
                        println("${it.first.name} ⊂ ${it.second.name}")
                }
        }
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