package ge.giosan777.matutu.mbasemobile.Volley

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.database.AppDatabase
import ge.giosan777.matutu.mbasemobile.models.Person

fun getAllContacts(context: Context) {
    val url = "http://162.55.141.130:1990/user_mobile_base/all"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
                response ->
            val jsonString = response.toString()
            val personArray: Array<Person> = Gson().fromJson(jsonString, Array<Person>::class.java)
            val personList=personArray.toList()
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "m_base"
            ).allowMainThreadQueries().build()
            val userDao = db.getPersonDao()
//            userDao.insertAll(personList.get(0))
            val allPerson=userDao.getAllPeople()
            Log.d("MyLog",allPerson.toString())

        },
        {
                error ->
            Log.d("MyLog",error.toString())
        }
    )
    queue.add(stringRequest)
}