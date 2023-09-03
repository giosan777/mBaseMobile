package ge.giosan777.matutu.mbasemobile.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import ge.giosan777.matutu.mbasemobile.APP_PREFERENCES
import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.getOneContactExcept
import ge.giosan777.matutu.mbasemobile.database.AppDatabase
import ge.giosan777.matutu.mbasemobile.database.saveAllJournalToLocalDbJournal
import ge.giosan777.matutu.mbasemobile.models.Journal
import ge.giosan777.matutu.mbasemobile.utils.hasConnectionWiFi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.util.Calendar


class ServiceTest : BroadcastReceiver() {

    private var countCall = 0

    override fun onReceive(context: Context, intent: Intent) {

        val mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val i = Intent()
        i.setClassName(
            "ge.giosan777.matutu.mbasemobile",
            "ge.giosan777.matutu.mbasemobile.DialogActivity"
        )
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val bus: EventBus = EventBus.getDefault()

        if (mSettings.getBoolean("canDrawOverlays", false)) {
            val action = intent.action
            if (action == "android.intent.action.PHONE_STATE") {
                // Обработка изменения состояния звонка
                val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                when (state) {
                    TelephonyManager.EXTRA_STATE_RINGING -> {
                        // Звонок входящий
                        val incomingNumber =
                            intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                        incomingNumber?.let {
                            val sortedNumber =
                                it.removePrefix("+995").replace("[^\\w+]".toRegex(), "")
                            val calendar = Calendar.getInstance()
                            val currentDateTime = calendar.timeInMillis
                            val journal = Journal(null, sortedNumber, 1, currentDateTime)
                            val journalList = mutableListOf(journal)
                            saveAllJournalToLocalDbJournal(context, journalList)

//                            i.putExtra("user", "Search..." )
//                            context.startActivity(i)

                            if(hasConnectionWiFi(context)){
                                GlobalScope.launch(Dispatchers.IO) {
                                    getOneContactExcept(sortedNumber, context) {
                                        Toast.makeText(context, "Call $it", Toast.LENGTH_LONG).show()
//                                        bus.post(it)
                                        i.putExtra("user", it )
                                        context.startActivity(i)
                                    }
                                }
                            }else{
                                val mainDb = AppDatabase.getDb(context)
                                val personArray = mainDb.getDao().findByPhoneStartingWith(sortedNumber).orEmpty()
                                val sortedList=personArray.sortedDescending()
                                if (sortedList.isNotEmpty()) {
                                    Toast.makeText(context, "Call ${sortedList[0].firstName}", Toast.LENGTH_LONG).show()
                                    i.putExtra("user", sortedList[0].firstName )
                                    context.startActivity(i)
//                                    bus.post(sortedList[0].firstName.toString())
                                }else{
                                    i.putExtra("user", "User is not in the database")
                                    context.startActivity(i)
//                                    bus.post("User is not in the database")
                                }
                            }


                        }
                    }

                    TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                        Log.d("MyLog", "Звонок начат")

                        val outGoingCall =
                            intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                        outGoingCall?.let {
                            val sortedNumber =
                                it.removePrefix("+995").replace("[^\\w+]".toRegex(), "")

                            val calendar = Calendar.getInstance()
                            val currentDateTime = calendar.timeInMillis

                            val journal = Journal(null, sortedNumber, 2, currentDateTime)
                            val journalList = mutableListOf(journal)

                            saveAllJournalToLocalDbJournal(context, journalList)

//                            i.putExtra("user", "Search..." )
//                            context.startActivity(i)

                            if(hasConnectionWiFi(context)){
                                GlobalScope.launch(Dispatchers.IO) {
                                    getOneContactExcept(sortedNumber, context) {
                                        Toast.makeText(context, "Call $it", Toast.LENGTH_LONG).show()
//                                        bus.post(it)
                                        i.putExtra("user", it )
                                        context.startActivity(i)
                                    }
                                }
                            }else{
                                val mainDb = AppDatabase.getDb(context)
                                val personArray = mainDb.getDao().findByPhoneStartingWith(sortedNumber).orEmpty()
                                val sortedList=personArray.sortedDescending()
                                if (sortedList.isNotEmpty()) {
                                    Toast.makeText(context, "Call ${sortedList[0].firstName}", Toast.LENGTH_LONG).show()
                                    i.putExtra("user", sortedList[0].firstName )
                                    context.startActivity(i)
//                                    bus.post(sortedList[0].firstName.toString())
                                }else{
                                    i.putExtra("user", "User is not in the database")
                                    context.startActivity(i)
//                                    bus.post("User is not in the database")
                                }
                            }

                        }

                    }

                    TelephonyManager.EXTRA_STATE_IDLE -> {
                        if (countCall % 2 == 0) {
                            val intent1 = Intent("ACTION_DAKETVA_ACTIVITY")
                            context.sendBroadcast(intent1)
                        }
                        countCall++
                    }
                }
            } else if (action == "android.intent.action.NEW_OUTGOING_CALL") {
                Log.d("MyLog", "Обработка исходящего звонка")
                val phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
                phoneNumber?.let {
//                    OpenDialog(it)
                }
            }
        }


    }
}

