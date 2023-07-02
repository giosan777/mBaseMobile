package ge.giosan777.matutu.mbasemobile.database

import androidx.compose.runtime.MutableState
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.models.Organization

val mainDbOrg = AppDatabaseOrg.getDb(APP_CONTEXT!!)

fun getAllOrganizationFromLocalDbOrg():List<Organization> {
    return mainDbOrg.getDao().getAllOrganizations()
}

fun saveAllContactsToLocalDbOrg(organizationList:MutableList<Organization>) {
    mainDbOrg.getDao().insertAllOrganizations(organizationList)
}

fun deleteAllOrganizationsFromLocalDBOrg() {
    mainDbOrg.getDao().deleteAll()
}

fun getAllOrganizationsWithPhoneOrg(
    mutableState: MutableState<MutableList<Organization>>,
    phone: String
) {
    val list = mainDbOrg.getDao().findByPhoneStartingWith(phone)
    mutableState.value = list.toMutableList()
}

fun getAllOrganizationsWithPhoneExceptOrg(
    phone: String,
    result :(user:String) -> Unit
) {
    val list = mainDbOrg.getDao().findByPhoneStartingWith(phone)
    if (list.isNotEmpty()) {
        result(list[0].organizationName)
    } else {
        result("Organization not found")
    }
}