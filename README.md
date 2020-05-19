# Различные вспомогательные штуки для Реляционной Алгебры
### Логические операции:

|       Операция       |      Запись в коде       |
|---------------------|--------------------------|
|        x ∨ y        |```x or y (или \|\|)```   |
|        x & y        |```x and y (или &&)```    |
|        ¬x           |```!x (или not)```        |
|        x \ y        |```x differs y```         |
|        x ∆ y        |```x symDiffers y```      |
|        x → y        |```x implies y```         |
|        x ← y        |```x converseImplies y``` |
|        x ↑ y        |```x nand y```            |
|        x ↓ y        |```x nor y```             |
|        x ⊕ y       |```x xor y```              |
|        x ~ y        |```x == y```

### Создание таблицы истинности из логической функции (для 2х, 3х и 4х переменных)
```kotlin
// для 2х переменных
val truthTableE = TruthTable.build(name = 'E', varNames = listOf('x', 'y')) { x, y ->
    // (x ∆ y) ↑ ¬(x & y)
    (x symDiffers y) nand !(x and y)
}

// для 3х переменных
val truthTableD = TruthTable.build(name = 'D', varNames = listOf('a', 'b', 'c')) { a, b, c ->
    // (a \ b) → c
    (a differs b) implies c
}

// для 4х переменных
val truthTableF = TruthTable.build(name = 'F', varNames = listOf('a', 'b', 'c', 'd')) { a, b, c, d ->
    // (a ↓ b) ← (1 ∨ (c & d))
    (a nor b) converseImplies (true or (c and d))
}

// для 3х переменных (при этом функция использует две переменные)
val truthTableA = TruthTable.build(name = 'A', varNames = listOf('a', 'b', 'c')) { a, b, c ->
    // a → c
    a implies c
}
```

### Создание таблицы истинности по заданному булевому вектору:
```kotlin
val truthTableC = TruthTable.build(name = 'C', varNames = listOf('x', 'y', 'z'), vector = "11111111")
```
### Красивый вывод таблицы истинности:
```kotlin
truthTableD.printTable()
```
Вывод:
```
+---+---+---+---+
| a | b | c | D |
+---+---+---+---+
| 0 | 0 | 0 | 1 |
| 0 | 0 | 1 | 1 |
| 0 | 1 | 0 | 1 |
| 0 | 1 | 1 | 1 |
| 1 | 0 | 0 | 0 |
| 1 | 0 | 1 | 1 |
| 1 | 1 | 0 | 1 |
| 1 | 1 | 1 | 1 |
+---+---+---+---+
```

### Красивый вывод булевого вектора:
```kotlin
truthTableD.printVector()
truthTableE.printVector()
truthTableF.printVector()
truthTableC.printVector()
```
Вывод:
```
D = (1, 1, 1, 1, 0, 1, 1, 1)
E = (1, 0, 0, 1)
F = (1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
C = (1, 1, 1, 1, 1, 1, 1, 1)
```

### СДНФ, СКНФ и полином Жегалкина 

```kotlin
truthTableD.perfectDisjunctiveNormalForm() // СДНФ
truthTableD.perfectConjunctiveNormalForm() // СКНФ
truthTableD.polynomZhegalkin() // полином Жегалкина
```
Вывод
```
СДНФ: (¬a & ¬b & ¬c) ⋁ (¬a & ¬b & c) ⋁ (¬a & b & ¬c) ⋁ (¬a & b & c) ⋁ (a & ¬b & c) ⋁ (a & b & ¬c) ⋁ (a & b & c) 
СКНФ: (¬a ⋁ b ⋁ c) 
Полином Жегалкина: 1 ⊕ (a) ⊕ (a & c) ⊕ (a & b) ⊕ (a & b & c)
```

### Проверка на взаимное расположения булевых векторов (равно, входит/не входит в подмножество):

```kotlin
TruthTable.printSubsetsEquality(
    listOf(
        truthTableD, // (1, 1, 1, 1, 0, 1, 1, 1)
        truthTableC, // (1, 1, 1, 1, 1, 1, 1, 1)
        truthTableV // (1, 1, 1, 1, 1, 1, 1, 1) Еще один для проверки равенства множеств
    )
)
```
Вывод:
```
D ⊂ C
D ⊂ V
C ⊆ V
V ⊆ C
```

### Нахождение соседних и противоположных наборов в множестве:
Записывать можно в любом удобном формате
Главное, чтобы **наборы были разделены запятыми**.
```kotlin
val subset = "{(01100),(10100),(11110),(10011),(00011)}"
println(subset)
findOppositeSubsets(subset)
findNeighboursSubsets(subset)
```

Вывод:
```
{(01100),(10100),(11110),(10011),(00011)};
Противоположные наборы:
(01100), (10011)
Соседние наборы:
(10011), (00011)
```

# Сборка проекта
### IntelliJ Idea
Если вы импоритруете проект с помощью Intellij Idea, то IDE должна подхватить все сама. Вам ничего не нужно делать :)

### Для других IDE
##### Windows 
```
gradlew run
```

##### Linux и macOS
```
./gradlew run
```


