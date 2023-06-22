package ge.giosan777.matutu.mbasemobile.Volley.mobileBase

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.database.AppDatabase
import ge.giosan777.matutu.mbasemobile.models.Person

fun getOneContactExcept(
    context: Context,
    phone: String,
    result: (user:String) -> Unit
) {
    val url = "http://162.55.141.130:1990/user_mobile_base/phone/$phone"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val jsonString = response.toString()
            val personArray: Array<Person> = Gson().fromJson(jsonString, Array<Person>::class.java)
            if (personArray.isNotEmpty()) {
                result(personArray[0].firstName.toString())
            }
        },
        { _ ->
            val mainDb = AppDatabase.getDb(context)
            val personArray = mainDb.getDao().findByPhoneStartingWith(phone).orEmpty()
//            val errorPerson: Person = Person(1, "ERROR", "ERROR_MESSAGE_ERROR", "ERROR")
//            val personArray = listOf<Person>(errorPerson)
//            Log.d("MyLog", error.toString())

        }
    )
    queue.add(stringRequest)
}