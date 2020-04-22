# Различные вспомогательные штуки для Реляционной Алгебры
### Логические операции:

| Операция            | Запись в коде            |
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

### Создание таблицы истинности из логической функции (для 2х, 3х и 4х переменных)
```kotlin
val truthTableD = TruthTable.from { a, b, c ->
    // (a \ b) → c
    (a differs b) implies c
}

val truthTableE = TruthTable.from { a, b, c ->
    // (a ∆ b) ↑ ¬(a & b)
    (a symDiffers b) nand !(a and b)
}

val truthTableF = TruthTable.from { a, b, c ->
    // (a ↓ b) ← 1
    (a nor b) converseImplies true
}

val truthTableA = TruthTable.from { a, b, c ->
    // a → c (при этом таблица будет построена для 3 переменных)
    a implies c
}
```

### Создание таблицы истинности по заданному булевому вектору:
```kotlin
val truthTableC = TruthTable("11111111")
```
### Красивый вывод таблицы истинности:
```kotlin
truthTableD.printTable()
```
Вывод:
```
+---+---+---+---+
| a | b | c | F |
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
```
Вывод:
```
(1, 1, 1, 1, 0, 1, 1, 1)
```

### Проверка на взаимное расположения булевых векторов (равно, входит/не входит в подмножество):

```kotlin
// TODO: По хорошему нужно задавать имя таблице истинности/булевому вектору при ее создании. В будущем сделаю =)
TruthTable.printSubsetsEquality(
    arrayListOf(truthTableD, truthTableE, truthTableF, truthTableC), // созданнные таблицы
    arrayListOf("D", "E", "F", "C") // имена таблиц для вывода
)
```
Вывод:
```
D не является подмножеством E
D не является подмножеством F
D является подмножеством C
E не является подмножеством F
E является подмножеством C
F является подмножеством C
```

# Сборка проекта
### Intellij Idea
Если вы импоритруете проект с помощью Intellij Idea, то IDE должна подхватить все сама. Вам ничего не нужно делать :)

### Windows 
1. Собираете проект с помощью Gradle:
```
gradle build
```
2. В папке ```relational_algebra\build\bin\mingw\releaseExecutable\``` появится файл ```relational_algebra.exe```
3. Запускаете файл в cmd / powershell

### Linux и macOS
1. Открываете файл ```build.gradle``` и меняете значение под свою систему:
```groovy
kotlin {
    // mingwX64 заменить на linuxX64, если Linux; на macosX64, если macOS.
    mingwX64("mingw") {
        //...
    }
}
```
2. Собираете проект с помощью Gradle:
```
gradle build
```
3. В папке ```relational_algebra\build\bin\mingw\releaseExecutable\``` появится файл ```relational_algebra.kexe```
4. Запускаете файл в терминале 


