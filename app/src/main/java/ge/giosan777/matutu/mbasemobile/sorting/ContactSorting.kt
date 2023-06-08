package ge.giosan777.matutu.mbasemobile.sorting

import androidx.core.text.isDigitsOnly
import ge.giosan777.matutu.mbasemobile.models.Person

fun contactSorting(unsortingList: MutableList<Person>): MutableList<Person> {
    val sortingList = mutableListOf<Person>()
    unsortingList.forEach {
        val phoneTMP = it.phone.removePrefix("+995").replace("[^\\w+]".toRegex(), "")
        var firstNameTMP = it.firstName.replace("[^\\W\\S+]".toRegex(), "")
        firstNameTMP= translateGe(firstNameTMP)
        firstNameTMP= translateRu(firstNameTMP)
        if (phoneTMP.count() in 9..9 && phoneTMP.isDigitsOnly()) {
            sortingList.add(
                Person(
                    null,
                    phoneTMP,
                    firstNameTMP,
                    "",
                    ""
                )
            )
        }
    }
    val setList=sortingList.toSet()
    return setList.toMutableList()
}



fun translateGe(firstNameTMP: String): String {
    val stringBuilderGe = StringBuilder()

    firstNameTMP.forEach {
        stringBuilderGe.append(replaceGeToEn(it))
    }
    return stringBuilderGe.toString()
}
fun translateRu(firstNameTMP: String): String {
    val stringBuilderGe = StringBuilder()

    firstNameTMP.forEach {
        stringBuilderGe.append(replaceRuToEn(it))
    }
    return stringBuilderGe.toString()
}

fun replaceGeToEn(char: Char): String {
    when (char) {
        'ა' -> return "a"
        'ბ' -> return "b"
        'გ' -> return "g"
        'დ' -> return "d"
        'ე' -> return "e"
        'ვ' -> return "v"
        'ზ' -> return "z"
        'თ' -> return "T"
        'ი' -> return "i"
        'კ' -> return "k"
        'ლ' -> return "l"
        'მ' -> return "m"
        'ნ' -> return "n"
        'ო' -> return "o"
        'პ' -> return "p"
        'ჟ' -> return "J"
        'რ' -> return "r"
        'ს' -> return "s"
        'ტ' -> return "t"
        'უ' -> return "u"
        'ფ' -> return "f"
        'ღ' -> return "R"
        'ყ' -> return "y"
        'შ' -> return "Sh"
        'ჩ' -> return "Ch"
        'ც' -> return "c"
        'ძ' -> return "Z"
        'წ' -> return "W"
        'ჭ' -> return "W"
        'ხ' -> return "x"
        'ჯ' -> return "j"
        'ქ' -> return "q"
        'ჰ' -> return "h"
        ' ' -> return " "
        else -> return "$char"
    }
}

fun replaceRuToEn(char: Char): String {
    val charTmp=char.lowercaseChar()
    when (charTmp) {
        'а' -> return "a"
        'б' -> return "b"
        'в' -> return "v"
        'г' -> return "g"
        'д' -> return "d"
        'е' -> return "e"
        'ё' -> return "e"
        'ж' -> return "j"
        'з' -> return "z"
        'и' -> return "i"
        'й' -> return "i"
        'к' -> return "k"
        'л' -> return "l"
        'м' -> return "m"
        'н' -> return "n"
        'о' -> return "o"
        'п' -> return "p"
        'р' -> return "r"
        'с' -> return "s"
        'т' -> return "t"
        'у' -> return "u"
        'ф' -> return "f"
        'х' -> return "x"
        'ц' -> return "c"
        'ч' -> return "Ch"
        'ш' -> return "Sh"
        'щ' -> return "Shc"
        'ъ' -> return ""
        'ы' -> return "i!"
        'ь' -> return ""
        'э' -> return "e"
        'ю' -> return "iu"
        'я' -> return "ia"
        ' ' -> return " "
        else -> return "$char"
    }
}








