package ge.giosan777.matutu.mbasemobile.database

import android.content.Context
import ge.giosan777.matutu.mbasemobile.models.Journal

lateinit var mainDbJournal : AppDatabaseJournal

fun getAllJournalFromLocalDbJournal(context: Context):List<Journal> {
    mainDbJournal=AppDatabaseJournal.getDb(context)
    return mainDbJournal.getDao().getAllJournal()
}

fun saveAllJournalToLocalDbJournal(context: Context,journalList:MutableList<Journal>) {
    mainDbJournal=AppDatabaseJournal.getDb(context)
    mainDbJournal.getDao().insertAllJournal(journalList)
}

fun deleteAllJournalFromLocalDBJournal(context: Context) {
    mainDbJournal=AppDatabaseJournal.getDb(context)
    mainDbJournal.getDao().deleteAll()
}
