
fun add(list: MutableList<Expense>) {
    println("Введите категорию")
    println("Возможные категории: ${Category.entries}")
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

    if (category == Category.OTHER && userInput.lowercase()!="other") {
        println("Ваш ввод не распознан. Расход будет добавлен в категорию $category.")
    }

    println("Введите сумму расходов")
    val amount = readln().toIntOrNull()

    if (amount == null || amount <= 0) {
        println("Ошибка: некорректная сумма расходов")
        return
    }

    val expense = Expense(category, amount)
    list.add(expense)
    println("Добавлено: ${expense.describe()}")

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

fun List<Expense>.totalSum(): Int = this.sumOf { it.sum }


fun summary(list: List<Expense>) {
    if (list.isEmpty()) {
        println("Список пока пуст.")
        return
    }

    val groupedList = list.groupBy{it.category}
    groupedList.forEach { (category, items) -> println("$category: ${items.totalSum()}")}
    println("Общий расход: ${list.sumOf { it.sum }}")
}

fun deleteLast(list: MutableList<Expense>){
    val removed = list.removeLastOrNull()
    if (removed != null) println("Удалено: ${removed.describe()}")
    else println("Список пока пуст")
}

data class Expense (val category: Category, val sum: Int): Describable {
    override fun describe(): String =
        ("Категория - $category. Сумма - $sum. ${sizeOf(sum)}")
}


enum class Category {
    FOOD, TRANSPORT, FUN, OTHER
}

interface Describable {
    fun describe(): String
}

sealed class MenuCommand {
    object Add: MenuCommand()
    object Show: MenuCommand()
    object Summary: MenuCommand()
    object DeleteLast: MenuCommand()
    object Exit: MenuCommand()
    object WrongInput: MenuCommand()
}

fun getUserInput():MenuCommand {
    val choice = readln()
    return when (choice.trim()) {
        "1" -> MenuCommand.Add
        "2" -> MenuCommand.Show
        "3" -> MenuCommand.Summary
        "4" -> MenuCommand.DeleteLast
        "0" -> MenuCommand.Exit
        else -> MenuCommand.WrongInput
    }
}


fun main() {
    val expenses = mutableListOf<Expense>()

    while(true){
        printMenu()

        val menuCommand = getUserInput()

        when (menuCommand) {
            MenuCommand.Add -> add(expenses)
            MenuCommand.Show -> show(expenses)
            MenuCommand.Summary-> summary(expenses)
            MenuCommand.DeleteLast -> deleteLast(expenses)
            MenuCommand.Exit -> break
            MenuCommand.WrongInput -> println("Нет такого пункта меню.")
        }


    }
}

private fun printMenu() {
    println("Меню")
    println("1 - Добавить")
    println("2 - Показать")
    println("3 - Итоги по категориям")
    println("4 - Удалить последний расход")
    println("0 - Выход")
}