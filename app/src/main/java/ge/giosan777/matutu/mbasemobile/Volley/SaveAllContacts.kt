package ge.giosan777.matutu.mbasemobile.Volley

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

fun SaveAllContacts(context: Context) {
    val url = "http://162.55.141.130:1990/user_mobile_base/phone/"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
                response ->
        },
        {
                error ->
        }
    )
    queue.add(stringRequest)
}