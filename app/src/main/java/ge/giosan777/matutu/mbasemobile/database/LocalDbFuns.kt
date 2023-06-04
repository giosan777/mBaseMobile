package ge.giosan777.matutu.mbasemobile.database

import android.content.Context
import androidx.compose.runtime.MutableState
import ge.giosan777.matutu.mbasemobile.models.Person

fun getAllPeopleFromLocalDb(context: Context):List<Person> {
    val mainDb = AppDatabase.getDb(context)
    return mainDb.getDao().getAllPeople()
}

fun saveAllContactsToLocalDb(context: Context,personList:MutableList<Person>) {
    val mainDb = AppDatabase.getDb(context)
    mainDb.getDao().insertAllPeoples(personList)
}

fun deleteAllContactsFromLocalDB(context: Context) {
    val mainDb = AppDatabase.getDb(context)
    mainDb.getDao().deleteAll()
}

fun getAllPeopleWithPhone(
    context: Context,
    mutableState: MutableState<MutableList<Person>>,
    phone: String
) {
    val mainDb = AppDatabase.getDb(context)
    val list = mainDb.getDao().findByPhoneStartingWith(phone)
    mutableState.value = list.toMutableList()
}