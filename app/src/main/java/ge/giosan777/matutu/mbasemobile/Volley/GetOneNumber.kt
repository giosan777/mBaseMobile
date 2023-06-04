package ge.giosan777.matutu.mbasemobile.Volley

import android.content.Context
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.database.AppDatabase
import ge.giosan777.matutu.mbasemobile.models.Person

fun getOneContact(
    context: Context,
    personState: MutableState<MutableList<Person>>,
    phone: String,
) {
    val url = "http://162.55.141.130:1990/user_mobile_base/phone/$phone"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val jsonString = response.toString()
            val personArray: Array<Person> = Gson().fromJson(jsonString, Array<Person>::class.java)
            personState.value = personArray.toMutableList()
        },
        { _ ->
            val mainDb = AppDatabase.getDb(context)
            val personArray = mainDb.getDao().findByPhoneStartingWith(phone).orEmpty()
            personState.value = personArray.toMutableList()
//            val errorPerson: Person = Person(1, "ERROR", "ERROR_MESSAGE_ERROR", "ERROR")
//            val personArray = listOf<Person>(errorPerson)
//            Log.d("MyLog", error.toString())

        }
    )
    queue.add(stringRequest)
}