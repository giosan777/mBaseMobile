package ge.giosan777.matutu.mbasemobile.database

import android.content.Context
import androidx.compose.runtime.MutableState
import ge.giosan777.matutu.mbasemobile.models.Organization

fun getAllOrganizationFromLocalDb(context: Context):List<Organization> {
    val mainDb = AppDatabaseOrg.getDb(context)
    return mainDb.getDao().getAllOrganizations()
}

fun saveAllContactsToLocalDb(context: Context,organizationList:MutableList<Organization>) {
    val mainDb = AppDatabaseOrg.getDb(context)
    mainDb.getDao().insertAllOrganizations(organizationList)
}

fun deleteAllOrganizationsFromLocalDB(context: Context) {
    val mainDb = AppDatabaseOrg.getDb(context)
    mainDb.getDao().deleteAll()
}

fun getAllOrganizationsWithPhone(
    context: Context,
    mutableState: MutableState<MutableList<Organization>>,
    phone: String
) {
    val mainDb = AppDatabaseOrg.getDb(context)
    val list = mainDb.getDao().findByPhoneStartingWith(phone)
    mutableState.value = list.toMutableList()
}

fun getAllOrganizationsWithPhoneExcept(
    context: Context,
    phone: String,
    result :(user:String) -> Unit
) {
    val mainDb = AppDatabaseOrg.getDb(context)
    val list = mainDb.getDao().findByPhoneStartingWith(phone)
    if (list.isNotEmpty()) {
        result(list[0].orgName)
    } else {
        result("Organization not found")
    }
}