package ge.giosan777.matutu.mbasemobile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getOneContactExcept
import ge.giosan777.matutu.mbasemobile.database.getAllPeopleWithPhoneExcept
import ge.giosan777.matutu.mbasemobile.utils.hasConnection

class StateChangedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val phoneState = intent!!.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (phoneState == TelephonyManager.EXTRA_STATE_RINGING) {
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            if (hasConnection(context!!)) {
                if (incomingNumber != null) {
                    Log.d("MyLog","$incomingNumber aqaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")

                    getOneContactExcept(incomingNumber,){

                        Toast.makeText(context, "rekavs $it", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                if (incomingNumber != null) {
                    getAllPeopleWithPhoneExcept(incomingNumber){
                        Toast.makeText(context, "rekavs $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
//                val i = Intent()
//                i.setClassName(
//                    "ge.giosan777.matutu.mbasemobile",
//                    "ge.giosan777.matutu.mbasemobile.MainActivity2"
//                )
//                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                context.startActivity(i)
        }
    }
}
