package ge.giosan777.matutu.mbasemobile.Volley

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.database.AppDatabase
import ge.giosan777.matutu.mbasemobile.models.Person

fun getAndSaveAllContacts(context: Context) {
    val url = "http://162.55.141.130:1990/user_mobile_base/all"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val jsonString = response.toString()
            val personArray: Array<Person> = Gson().fromJson(jsonString, Array<Person>::class.java)
            val personList = mutableListOf<Person>()
            personArray.forEach {
                personList.add(Person(null, it.phone, it.firstName, it.lastName))
            }
            val mainDb = AppDatabase.getDb(context)
            mainDb.getDao().insertAllPeoples(personList)

            Log.d("MyLog", "KVELA KONTAKTI" +mainDb.getDao().getAllPeople().toString())

        },
        { error ->
            Log.d("MyLog", error.toString())
        }
    )
    queue.add(stringRequest)
}