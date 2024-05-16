package co.alloy.codelesssdklite

import android.webkit.JavascriptInterface
import org.json.JSONObject

class WebViewInterface {
    @JavascriptInterface
    fun startAlloy(data: String) {
        logd(data)
        val result = JSONObject(data)
        when (result.getString("status").lowercase()) {
            "closed" -> Alloy.finish(result)
            "pending_step_up" -> Alloy.finish(result)
            "expired" -> Alloy.finish(result)
            "waiting_review" -> Alloy.finish(result)
            "completed" -> Alloy.finish(result)
            else -> Alloy.finish(result)
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
