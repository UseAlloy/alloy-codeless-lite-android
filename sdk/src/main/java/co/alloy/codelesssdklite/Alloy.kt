package co.alloy.codelesssdklite

import android.content.Context

object Alloy {
    interface Listener {
        fun onCancelled() {}
        fun onSuccess() {}
        fun onDenied() {}
        fun onManualReview() {}
        fun journeyApplicationTokenCreated(token: String) {}
    }

    var listener: Listener? = null
    internal var closeActivityListener: (() -> Unit)? = null

    internal var settings: AlloySettings? = null

    @JvmStatic
    fun start(
        context: Context,
        settings: AlloySettings,
    ) {
        this.settings = settings
        context.showAlloy(Function.START_ALLOY)
    }

    @JvmStatic
    fun createApplication(
        context: Context,
        settings: AlloySettings,
    ) {
        this.settings = settings
        context.showAlloy(Function.CREATE_JOURNEY_APPLICATION)
    }

    internal fun finishCancelled() {
        listener?.onCancelled()
        closeActivityListener?.invoke()
    }

    internal fun finishDenied() = listener?.onDenied()
    internal fun finishManualReview() = listener?.onManualReview()
    internal fun finishSuccess() = listener?.onSuccess()
    internal fun journeyApplicationTokenCreated(token: String) {
        listener?.journeyApplicationTokenCreated(token)
        closeActivityListener?.invoke()
    }
}
