
fun add(list: MutableList<Expense>) {
    println("Введите категорию")
    val userInput = readln()

    if(userInput.isBlank()){
        println("Ошибка: пустая категория")
        return
    }

    val category: Category = when(userInput.lowercase()) {
        "food" -> Category.FOOD
        "transport" -> Category.TRANSPORT
        "fun" -> Category.FUN
        else -> Category.OTHER
    }

    println("Введите сумму расходов")
    val amount = readln().toIntOrNull()

    if (amount == null || amount <= 0) {
        println("Ошибка: некорректная сумма расходов")
        return
    }

    list.add(Expense(category,amount))
    println("Добавлено: категория - $category, сумма расходов - $amount")

}

fun show(list: List<Expense>) {
    if (list.isEmpty()) {
        println("Список пока пуст.")
        return
    }

    list.forEach { println(it.describe()) }
}

fun sizeOf(amount: Int): String {
    return when {
        amount <= 1000 -> "Малая сумма"
        amount <= 10000 -> "Средняя сумма"
        else -> "Большая сумма"
    }
}

fun summary(list: List<Expense>) {
    if (list.isEmpty()) {
        println("Список пока пуст.")
        return
    }

    val groupedList = list.groupBy{it.category}

    for((category, items) in groupedList) {
        val sum = items.sumOf { it.sum }

        println("$category: $sum")
    }
//    list.filter { it.sum > 1000 }.forEach { println(it) }
//    list.map { "${it.category}: ${it.sum}" }.forEach { println(it) }
//    println(list.any { it.category == Category.TRANSPORT})
//    println(list.sortedByDescending { it.sum })

    println("Общий расход: ${list.sumOf { it.sum }}")
    println("Еда всего: ${list.filter { it.category == Category.FOOD }.sumOf { it.sum }}")
    println("Самый крупный расход: ${list.maxByOrNull { it.sum }?.describe()}")
}

fun deleteLast(list: MutableList<Expense>){
    val removed = list.removeLastOrNull()
    if (removed != null) println("Удалено: ${removed.category}, ${removed.sum}")
    else println("Список пока пуст")
}

data class Expense (val category: Category, val sum: Int): Describable {
    override fun describe(): String =
        ("Категория: $category. Сумма: $sum. ${sizeOf(sum)}")
}


enum class Category {
    FOOD, TRANSPORT, FUN, OTHER
}

interface Describable {
    fun describe(): String
}


fun main() {
    val expenses = mutableListOf<Expense>()

    while(true){
        println("Меню")
        println("1 - Добавить")
        println("2 - Показать")
        println("3 - Итоги по категориям")
        println("4 - Удалить последний расход")
        println("0 - Выход")
        val choice = readln()
        when (choice.trim()) {
            "1" -> add(expenses)
            "2" -> show(expenses)
            "3" -> summary(expenses)
            "4" -> deleteLast(expenses)
            "0" -> break
            else -> println("Нет такого пункта меню.")
        }


    }
}