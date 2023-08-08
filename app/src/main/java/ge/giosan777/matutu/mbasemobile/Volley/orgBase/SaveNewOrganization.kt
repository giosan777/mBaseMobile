package ge.giosan777.matutu.mbasemobile.Volley.orgBase

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ge.giosan777.matutu.mbasemobile.Volley.MyHurlStack
import ge.giosan777.matutu.mbasemobile.models.Organization
import java.nio.charset.Charset
import kotlin.concurrent.thread

fun saveNewOrganization(
    context: Context,
    organizationList: List<Organization>
) {
    thread {
        val queue = Volley.newRequestQueue(context, MyHurlStack())
        val url = "https://mbase.ge/user_organization_base/saveAll"

        val personListString = Gson().toJson(organizationList)
        val stringReq: StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    // response
                    val strResp = response.toString()
                    Log.d("API", strResp)
                },
                Response.ErrorListener { error ->
                    Log.d("API", "error => $error")
                }
            ) {
                override fun getBody(): ByteArray {
                    return personListString.toByteArray(Charset.defaultCharset())
                }

                override fun getParamsEncoding(): String {
                    return personListString
                }

                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
        queue.add(stringReq)
    }

}