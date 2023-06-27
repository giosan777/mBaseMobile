package ge.giosan777.matutu.mbasemobile.contacts

import android.content.Context
import android.provider.ContactsContract
import ge.giosan777.matutu.mbasemobile.models.Person

fun getAllContactsFromPhoneMy(context: Context): MutableList<Person> {
    val arrayContacts = mutableListOf<Person>()
    val cursor = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        null
    )
    cursor?.let {
        while (it.moveToNext()) {

            val fullName =
                it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
            val phone =
                it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            if (fullName != null && phone != null) {
                val personTmp = Person(null, phone, fullName, "",1)
                arrayContacts.add(personTmp)
            }
        }
    }
    cursor?.close()
    return arrayContacts
}