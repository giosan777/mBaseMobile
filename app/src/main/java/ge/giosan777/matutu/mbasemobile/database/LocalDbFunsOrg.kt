package ge.giosan777.matutu.mbasemobile.database

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

fun getAllOrganizationsWithNameOrg(
    orgName: String
): List<Organization> {
    return mainDbOrg.getDao().findByNameStartingWith(orgName)
}

fun getAllOrganizationsWithPhoneExceptOrg(
    category: String,
): List<Organization> {
    return mainDbOrg.getDao().findByCategoryStartingWith(category)
}