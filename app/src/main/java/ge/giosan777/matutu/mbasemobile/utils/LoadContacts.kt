package ge.giosan777.matutu.mbasemobile.utils

import android.content.Context
import android.provider.ContactsContract


fun loadContacts(context:Context):MutableList<String> {
    val contentResolver = context.getContentResolver()
    val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
    val contacts = mutableListOf<String>()

    if (cursor != null) {
        while (cursor.moveToNext()) {
            // получаем каждый контакт
            val contact = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactsContract.Contacts.NAME_RAW_CONTACT_ID)
            )
            // добавляем контакт в список
            contacts.add(contact)
        }
        cursor.close()
    }
    return contacts
}