package ge.giosan777.matutu.mbasemobile.Volley.mobileBase

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.database.AppDatabase
import ge.giosan777.matutu.mbasemobile.models.Person
import kotlin.concurrent.thread

suspend fun getOneContactExcept(
    phone: String,
    context: Context,
    result: (user:String) -> Unit
) {
    thread {
        val url = "http://162.55.141.130:1990/user_mobile_base/phone/$phone"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val jsonString = response.toString()
                val personArray: Array<Person> = Gson().fromJson(jsonString, Array<Person>::class.java)
                val sortedList=personArray.sortedDescending()
                if (sortedList.isNotEmpty()) {
                    result(sortedList[0].firstName.toString())
                }else{
                    result("User not found")
                }
            },
            { _ ->
                val mainDb = AppDatabase.getDb(context)
                val personArray = mainDb.getDao().findByPhoneStartingWith(phone).orEmpty()
                val sortedList=personArray.sortedDescending()
                if (sortedList.isNotEmpty()) {
                    result(sortedList[0].firstName.toString())
                }else{
                    result("User is not in the database")
                }
            }
        )
        queue.add(stringRequest)
    }

}