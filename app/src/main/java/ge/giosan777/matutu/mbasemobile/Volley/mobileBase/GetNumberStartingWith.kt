package ge.giosan777.matutu.mbasemobile.Volley.mobileBase

import android.content.Context
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.database.AppDatabase
import ge.giosan777.matutu.mbasemobile.database.getAllPeopleWithPhoneStartingWith
import ge.giosan777.matutu.mbasemobile.models.Person

suspend fun getNumberStartingWith(
    context: Context,
    mutableState: MutableState<MutableList<Person>>,
    phone: String,
) {
    val mainDb = AppDatabase.getDb(context)
    val url = "http://162.55.141.130:1990/user_mobile_base/StartingWith/$phone"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val jsonString = response.toString()
            val personArray: Array<Person> = Gson().fromJson(jsonString, Array<Person>::class.java)
            val sortedList=personArray.sortedDescending()
            mutableState.value = sortedList.toMutableList()
        },
        { _ ->
            val personArray = getAllPeopleWithPhoneStartingWith(phone = phone)
            mutableState.value = personArray.toMutableList()
        }
    )
    queue.add(stringRequest)
}