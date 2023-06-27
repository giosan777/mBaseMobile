package ge.giosan777.matutu.mbasemobile.contacts

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.CallLog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import ge.giosan777.matutu.mbasemobile.models.Journal


fun getAllJournal(context: Context): MutableSet<Journal> {
    val CALL_LOG_PERMISSION_REQUEST_CODE = 1

    if (ContextCompat.checkSelfPermission(
            APP_CONTEXT!!,
            Manifest.permission.READ_CALL_LOG
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            APP_CONTEXT!!,
            arrayOf(Manifest.permission.READ_CALL_LOG),
            CALL_LOG_PERMISSION_REQUEST_CODE
        )
    } else {
        return getCallLog(context)
    }
    return mutableSetOf(Journal("",0,0,0))
}

fun getCallLog(context: Context): MutableSet<Journal> {
    val journalList = mutableSetOf<Journal>()
    val cursor = context.contentResolver.query(
        CallLog.Calls.CONTENT_URI,
        null,
        null,
        null,
        null
    )

    cursor?.let {
        if (it.moveToFirst()) {
            do {
                val number = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                val type = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))
                val date = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE))
                val duration = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DURATION))

//                Log.d("MyLog", "Number: $number")
//                Log.d("MyLog", "Type: $type")
//                Log.d("MyLog", "Date: $date")
//                Log.d("MyLog", "Duration: $duration")

                journalList.add(Journal(number, type, date, duration))
            } while (it.moveToNext())
        }
        it.close()
    }
    return journalList
}