package ge.giosan777.matutu.mbasemobile.validation

import androidx.core.text.isDigitsOnly

fun organizationNameValidation(name: String): String {
    if (name.isEmpty()) {
        return "aucilebeli veli"
    }else if (name.isBlank()) {
        return "aucilebeli veli is blank"
    }
    return "ok"
}

fun organizationPhoneValidation(name: String): String {
    if (name.isEmpty()) {
        return "aucilebeli veli"
    }else if (name.isBlank()) {
        return "aucilebeli veli is blank"
    }else if (!name.isDigitsOnly()) {
        return "mxolod cifrebi"
    }
    return "ok"
}

fun organizationCategoryValidation(name: String): String {
    if (name=="Category") {
        return "aucilebeli veli"
    }
    return "ok"
}