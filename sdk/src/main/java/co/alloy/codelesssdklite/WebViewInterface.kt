package co.alloy.codelesssdklite

import android.webkit.JavascriptInterface
import org.json.JSONObject

class WebViewInterface {
    @JavascriptInterface
    fun startAlloy(data: String) {
        logd(data)
        val result = JSONObject(data)
        when (result.getString("status").lowercase()) {
            "closed" -> Alloy.finish()
            "pending_step_up" -> Alloy.finish()
            "expired" -> Alloy.finish()
            "waiting_review" -> Alloy.finish()
            "completed" -> Alloy.finish()
            else -> Alloy.finish()
        }
    }

    @JavascriptInterface
    fun journeyApplicationTokenCreated(data: String) {
        logd(data)
        Alloy.journeyApplicationTokenCreated(data)
    }

    @JavascriptInterface
    fun gotError(error: String) {
        logd(error)
        Alloy.gotError(error)
    }
}
