package ge.giosan777.matutu.mbasemobile.Volley.mobileBase

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.Volley.MyHurlStack
import ge.giosan777.matutu.mbasemobile.models.Uuid
import kotlin.concurrent.thread

fun uuidSave(
    context: Context,
    uuid: String,
) {
    val url = "https://mbase.ge/uuid/save/$uuid"
    val queue = Volley.newRequestQueue(context, MyHurlStack())
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { _ ->

        },
        { _ ->
        }
    )
    queue.add(stringRequest)
}


fun uuidGetAll(context: Context, callbacks: (List<Uuid>) -> Unit) {
    thread {
        val url = "http://162.55.141.130:1990/uuid/all"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val jsonString = response.toString()
                val uuidArray: Array<Uuid> = Gson().fromJson(jsonString, Array<Uuid>::class.java)
                val uuidList = mutableListOf<Uuid>()
                uuidList.forEach {
                    uuidList.add(Uuid(null, it.uuid))
                }
                callbacks.invoke(uuidList)

            },
            { error ->
                Log.d("MyLog", error.toString())
            }
        )
        queue.add(stringRequest)
    }
}