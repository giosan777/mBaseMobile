package ge.giosan777.matutu.mbasemobile.Volley.orgBase

import android.content.Context
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.database.AppDatabaseOrg
import ge.giosan777.matutu.mbasemobile.models.Organization


suspend fun getStartingNameWithOrg(
    context: Context,
    mutableState: MutableState<MutableList<Organization>>,
    orgName: String,
) {
    val url = "http://162.55.141.130:1990/user_organization_base/StartingWith/$orgName"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val jsonString = response.toString()
            val orgArray: Array<Organization> = Gson().fromJson(jsonString, Array<Organization>::class.java)
            mutableState.value = orgArray.toMutableList()
        },
        { _ ->
            val mainDb = AppDatabaseOrg.getDb(context)
            val orgArray = mainDb.getDao().findByPhoneStartingWith(orgName).orEmpty()
            mutableState.value = orgArray.toMutableList()

//            val errorPerson: Person = Person(1, "ERROR", "ERROR_MESSAGE_ERROR", "ERROR")
//            val personArray = listOf<Person>(errorPerson)
//            Log.d("MyLog", error.toString())

        }
    )
    queue.add(stringRequest)
}

