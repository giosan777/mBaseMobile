package ge.giosan777.matutu.mbasemobile.Volley

import android.content.Context
import ge.giosan777.matutu.mbasemobile.database.AppDatabase

fun deleteAllContacts(context: Context) {
    val mainDb = AppDatabase.getDb(context)
    mainDb.getDao().deleteAll()
}