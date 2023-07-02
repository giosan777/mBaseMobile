package ge.giosan777.matutu.mbasemobile.Volley.mobileBase

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.database.AppDatabase
import ge.giosan777.matutu.mbasemobile.models.Person
import kotlin.concurrent.thread

fun getOneContactExcept(
    phone: String,
    result: (user:String) -> Unit
) {
    thread {
        val url = "http://162.55.141.130:1990/user_mobile_base/phone/$phone"
        val queue = Volley.newRequestQueue(APP_CONTEXT!!)
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
                val mainDb = AppDatabase.getDb(APP_CONTEXT!!)
                val personArray = mainDb.getDao().findByPhoneStartingWith(phone).orEmpty()
                if (personArray.isNotEmpty()) {
                    result(personArray[0].firstName.toString())
                }

            }
        )
        queue.add(stringRequest)
    }

}