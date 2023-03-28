package co.alloy.codelesssdklite

import android.content.Context

object Alloy {
    interface Listener {
        fun onCancelled() {}
        fun onSuccess() {}
        fun onDenied() {}
        fun onManualReview() {}
    }

    var listener: Listener? = null

    internal var settings: AlloySettings? = null

    @JvmStatic
    fun start(
        context: Context,
        settings: AlloySettings,
    ) {
        this.settings = settings
        context.showAlloy()
    }

    internal fun finishCancelled() = listener?.onCancelled()
    internal fun finishDenied() = listener?.onDenied()
    internal fun finishManualReview() = listener?.onManualReview()
    internal fun finishSuccess() = listener?.onSuccess()
}
