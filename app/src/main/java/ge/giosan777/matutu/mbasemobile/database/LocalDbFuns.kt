package ge.giosan777.matutu.mbasemobile.database

import androidx.compose.runtime.MutableState
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.models.Person

val mainDb = AppDatabase.getDb(APP_CONTEXT!!)

 fun getAllPeopleFromLocalDb(): List<Person> {
    return mainDb.getDao().getAllPeople()
}

 fun saveAllContactsToLocalDb(personList: MutableList<Person>) {
    mainDb.getDao().insertAllPeoples(personList)
}

 fun deleteAllContactsFromLocalDB() {
    mainDb.getDao().deleteAll()
}

 fun getAllPeopleWithPhone(
    mutableState: MutableState<MutableList<Person>>,
    phone: String
) {
    val list = mainDb.getDao().findByPhoneStartingWith(phone)
    mutableState.value = list.toMutableList()
}

 fun getAllPeopleWithPhoneExcept(
    phone: String,
    result: (user: String) -> Unit
) {
    val list = mainDb.getDao().findByPhoneStartingWith(phone)
    if (list.isNotEmpty()) {
        result(list[0].firstName)
    } else {
        result("User not found")
    }
}