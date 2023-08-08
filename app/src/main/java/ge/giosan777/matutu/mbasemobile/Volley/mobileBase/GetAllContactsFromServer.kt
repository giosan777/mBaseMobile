package ge.giosan777.matutu.mbasemobile.Volley.mobileBase

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.Volley.MyHurlStack
import ge.giosan777.matutu.mbasemobile.models.Person
import kotlin.concurrent.thread

fun getAllContactsFromServer(context: Context, callbacks: (MutableList<Person>) -> Unit ) {
    thread {
        val url = "https://mbase.ge/user_mobile_base/all"
        val queue = Volley.newRequestQueue(context, MyHurlStack())
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val jsonString = response.toString()
                val personArray: Array<Person> = Gson().fromJson(jsonString, Array<Person>::class.java)
                val personList = mutableListOf<Person>()
                personArray.forEach {
                    personList.add(Person(null, it.phone, it.firstName, it.lastName,it.duplicateInt))
                }
                callbacks.invoke(personList)

            },
            { error ->
                Log.d("MyLog", "${error.toString()}   aqaaaaaaaaaaaa")
            }
        )
        queue.add(stringRequest)
    }

}

