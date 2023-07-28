package ge.giosan777.matutu.mbasemobile.sorting

import ge.giosan777.matutu.mbasemobile.models.Person

fun contactSorting1(unsortingList: MutableList<Person>): MutableList<Person> {
    val sortingList = mutableListOf<Person>()
    unsortingList.forEach {
        val phoneTMP = it.phone.removePrefix("+995").replace("[^\\w+]".toRegex(), "")
        val firstNameTMP = it.firstName.replace("[^\\W\\S+]".toRegex(), "")
//        firstNameTMP= translateFromGe(firstNameTMP)
//        firstNameTMP= translateFromRu(firstNameTMP)
//        firstNameTMP= translateEnToEn(firstNameTMP)
        if (phoneTMP.count() in 9..16 ) {
            sortingList.add(
                Person(
                    null,
                    phoneTMP,
                    firstNameTMP,
                    "",
                    1
                )
            )
        }
    }
    val setList=sortingList.toSet()
    return setList.toMutableList()
}



fun translateFromGe(firstNameTMP: String): String {
    val stringBuilderGe = StringBuilder()

    firstNameTMP.forEach {
        stringBuilderGe.append(replaceGeToEn(it))
    }
    return stringBuilderGe.toString()
}
fun translateFromRu(firstNameTMP: String): String {
    val stringBuilderGe = StringBuilder()

    firstNameTMP.forEach {
        stringBuilderGe.append(replaceRuToEn(it))
    }
    return stringBuilderGe.toString()
}
fun translateEnToEn(firstNameTMP: String): String {
    val stringBuilderGe = StringBuilder()

    firstNameTMP.forEach {
        stringBuilderGe.append(replaceEntoEn(it))
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
        'შ' -> return "S"
        'ჩ' -> return "C"
        'ც' -> return "c"
        'ძ' -> return "Z"
        'წ' -> return "w"
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
        'щ' -> return "Sh"
        'ъ' -> return ""
        'ы' -> return "i"
        'ь' -> return ""
        'э' -> return "e"
        'ю' -> return "iu"
        'я' -> return "ia"
        ' ' -> return " "
        else -> return "$char"
    }
}




fun replaceEntoEn(char: Char): String {
    val charTmp=char.lowercaseChar().toString()
//    when (charTmp) {
//        'a' -> return "a"
//        'b' -> return "b"
//        'c' -> return "v"
//        'd' -> return "g"
//        'e' -> return "d"
//        'f' -> return "e"
//        'g' -> return "e"
//        'h' -> return "j"
//        'i' -> return "z"
//        'j' -> return "i"
//        'k' -> return "i"
//        'l' -> return "k"
//        'm' -> return "l"
//        'n' -> return "m"
//        'o' -> return "n"
//        'p' -> return "o"
//        'q' -> return "p"
//        'r' -> return "r"
//        's' -> return "s"
//        't' -> return "t"
//        'у' -> return "u"
//        'u' -> return "f"
//        'v' -> return "x"
//        'w' -> return "c"
//        'x' -> return "Ch"
//        'y' -> return "Sh"
//        'z' -> return "Sh"
//        ' ' -> return " "
//        else -> return "$charTmp"
//    }
    return charTmp
}




