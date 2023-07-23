package ge.giosan777.matutu.mbasemobile.database

import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.models.Person

val mainDb = AppDatabase.getDb(APP_CONTEXT)

 fun getAllPeopleFromLocalDb(): List<Person> {
    return mainDb.getDao().getAllPeople()
}

 fun saveAllContactsToLocalDb(personList: MutableList<Person>) {
    mainDb.getDao().insertAllPeoples(personList)
}

 fun deleteAllContactsFromLocalDB() {
    mainDb.getDao().deleteAll()
}

fun getAllPeopleWithPhoneStartingWith(
    phone: String
): List<Person> {
    return mainDb.getDao().findByPhoneStartingWith(phone)
}

