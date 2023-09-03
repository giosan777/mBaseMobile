package ge.giosan777.matutu.mbasemobile.other_permissions

import android.app.AppOpsManager
import android.content.Context.APP_OPS_SERVICE
import android.os.Binder
import ge.giosan777.matutu.mbasemobile.APP_CONTEXT
import java.lang.reflect.Method

fun isShowOnLockScreenPermissionEnable(): Boolean {
    return try {
        val manager = APP_CONTEXT.getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val method: Method = AppOpsManager::class.java.getDeclaredMethod(
            "checkOpNoThrow",
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType,
            String::class.java
        )
        val result =
            method.invoke(manager, 10020, Binder.getCallingUid(), APP_CONTEXT.packageName) as Int
        AppOpsManager.MODE_ALLOWED == result
    } catch (e: Exception) {
        false
    }
}
//        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
//        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
//        intent.putExtra("extra_pkgname", APP_CONTEXT.packageName)
//        APP_CONTEXT.startActivity(intent)