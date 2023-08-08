package ge.giosan777.matutu.mbasemobile.Volley.orgBase

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.Volley.MyHurlStack
import ge.giosan777.matutu.mbasemobile.models.Organization
import kotlin.concurrent.thread

fun getAllContactsFromServerOrg(context: Context, callbacks: (MutableList<Organization>) -> Unit ) {
    thread {
        val url = "https://mbase.ge/user_organization_base/all"
        val queue = Volley.newRequestQueue(context, MyHurlStack())
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val jsonString = response.toString()
                val orgArray: Array<Organization> = Gson().fromJson(jsonString, Array<Organization>::class.java)
                val orgList = mutableListOf<Organization>()
                orgArray.forEach {
                    orgList.add(Organization(null, it.phone, it.organizationName, it.description,it.category,it.address,it.webSite,it.img))
                }
                callbacks.invoke(orgList)

            },
            { error ->
                Log.d("MyLog", error.toString())
            }
        )
        queue.add(stringRequest)
    }

}

