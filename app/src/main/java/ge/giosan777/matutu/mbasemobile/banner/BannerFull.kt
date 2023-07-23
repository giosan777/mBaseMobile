package ge.giosan777.matutu.mbasemobile.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerFull() {
    AndroidView(factory = {context ->
        AdView(context).apply {
            setAdSize(AdSize.FULL_BANNER)
//            adUnitId =context.getString(R.string.add_mob_giigle)
            adUnitId ="ca-app-pub-3940256099942544/6300978111"
            val addRequest=AdRequest.Builder().build()
//            adListener = object : AdListener() {
//                override fun onAdLoaded() {
//                    super.onAdLoaded()
//                }
//            }
            loadAd(addRequest)
        }
    })
}