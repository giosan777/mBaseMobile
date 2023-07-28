package ge.giosan777.matutu.mbasemobile.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun copyToClipboard(context: Context, text: String) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("password", text)
    clipboardManager.setPrimaryClip(clip)
}