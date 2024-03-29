package ge.giosan777.matutu.mbasemobile.Volley.orgBase

import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.Volley.MyHurlStack
import ge.giosan777.matutu.mbasemobile.database.AppDatabaseOrg
import ge.giosan777.matutu.mbasemobile.database.findByOrganizationNameIsContainingIgnoreCase
import ge.giosan777.matutu.mbasemobile.database.getAllOrganizationsWithNameOrg
import ge.giosan777.matutu.mbasemobile.models.Organization


fun getStartingNameWithOrg(
    mutableState: MutableState<MutableList<Organization>>,
    orgName: String,
) {
    val url = "https://mbase.ge/user_organization_base/StartingWith/$orgName"
    val queue = Volley.newRequestQueue(APP_CONTEXT, MyHurlStack())
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val jsonString = response.toString()
            val orgArray: Array<Organization> = Gson().fromJson(jsonString, Array<Organization>::class.java)
            mutableState.value = orgArray.toMutableList()
        },
        { _ ->
            val orgList = getAllOrganizationsWithNameOrg(orgName)
            mutableState.value = orgList.toMutableList()
        }
    )
    queue.add(stringRequest)
}

fun findByOrganizationNameIsContaining(
    mutableState: MutableState<MutableList<Organization>>,
    orgNameContains: String,
) {
    val url = "https://mbase.ge/user_organization_base/findByOrganizationNameIsContainingIgnoreCase/$orgNameContains"
    val queue = Volley.newRequestQueue(APP_CONTEXT, MyHurlStack())
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val jsonString = response.toString()
            val orgArray: Array<Organization> = Gson().fromJson(jsonString, Array<Organization>::class.java)
            mutableState.value = orgArray.toMutableList()
            Log.d("MyLog",orgArray.size.toString())
        },
        { _ ->
            val orgList = findByOrganizationNameIsContainingIgnoreCase(orgNameContains)
            mutableState.value = orgList.toMutableList()
            Log.d("MyLog",orgList.size.toString())

        }
    )
    queue.add(stringRequest)
}




fun getCategoryStartingNameWithOrg(
    mutableState: MutableState<MutableList<Organization>>,
    orgCategory: String,
) {
    val url = "https://mbase.ge/user_organization_base/StartingCategoryWith/$orgCategory"
    val queue = Volley.newRequestQueue(APP_CONTEXT)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val jsonString = response.toString()
            val orgArray: Array<Organization> = Gson().fromJson(jsonString, Array<Organization>::class.java)
            mutableState.value = orgArray.toMutableList()
        },
        { _ ->
            val mainDb = AppDatabaseOrg.getDb(APP_CONTEXT)
            val orgArray = mainDb.getDao().findByCategoryStartingWith(orgCategory).orEmpty()
            mutableState.value = orgArray.toMutableList()
        }
    )
    queue.add(stringRequest)
}

