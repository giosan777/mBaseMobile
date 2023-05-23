package ge.giosan777.matutu.mbasemobile.Volley

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

fun GetResult(context: Context, state: MutableState<String>) {
    val url = "http://162.55.141.130:1990/user_mobile_base/phone/+995425254235"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
                response ->
                Log.d("MyLog",response)
                state.value=response
        },
        {
                error ->
                Log.d("MyLog",error.toString())
        }
    )
    queue.add(stringRequest)
}