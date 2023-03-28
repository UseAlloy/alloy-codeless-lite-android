package co.alloy.codelesssdklite

import android.util.Log

val Any.className: String
    get() {
        val name = javaClass.simpleName

        return if (name.isBlank()) {
            "{AnonClass}"
        } else {
            name
        }
    }

fun Any.logd(msg: String) {
    Log.d(className, msg)
}

fun Any.loge(msg: String) {
    Log.e(className, msg)
}

fun Any.loge(msg: String, t: Throwable) {
    Log.e(className, msg, t)
}
