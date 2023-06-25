package ge.giosan777.matutu.mbasemobile.sorting

import ge.giosan777.matutu.mbasemobile.models.Person

fun contactSorting2(unsortingList: MutableList<Person>): MutableList<Person> {
    val sortingList = mutableListOf<Person>()
    unsortingList.forEach {
        it.firstName = translateToGe(it.firstName)

    }
    return unsortingList
}


fun translateToGe(firstNameTMP: String): String {
    val stringBuilderGe = StringBuilder()
    firstNameTMP.forEach {
        stringBuilderGe.append(replaceEnToGe(it))
    }
    return stringBuilderGe.toString()
}


fun replaceEnToGe(char: Char): String {
    when (char) {
        'a' -> return "ა"
        'b' -> return "ბ"
        'g' -> return "გ"
        'd' -> return "დ"
        'e' -> return "ე"
        'v' -> return "ვ"
        'z' -> return "ზ"
        'T' -> return "თ"
        'i' -> return "ი"
        'k' -> return "კ"
        'l' -> return "ლ"
        'm' -> return "მ"
        'n' -> return "ნ"
        'o' -> return "ო"
        'p' -> return "პ"
        'J' -> return "ჟ"
        'r' -> return "რ"
        's' -> return "ს"
        't' -> return "ტ"
        'u' -> return "უ"
        'f' -> return "ფ"
        'R' -> return "ღ"
        'y' -> return "ყ"
        'S' -> return "შ"
        'C' -> return "ჩ"
        'c' -> return "ც"
        'Z' -> return "ძ"
        'W' -> return "წ"
        'Q' -> return "ჭ"
        'x' -> return "ხ"
        'j' -> return "ჯ"
        'q' -> return "ქ"
        'h' -> return "ჰ"
        'U' -> return "იუ"
        'A' -> return "ია"
        'X' -> return "ც"
        ' ' -> return " "
        else -> return "$char"
    }
}

















































