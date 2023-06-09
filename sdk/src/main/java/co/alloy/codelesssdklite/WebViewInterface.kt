package co.alloy.codelesssdklite

import android.webkit.JavascriptInterface
import org.json.JSONObject

class WebViewInterface {
    @JavascriptInterface
    fun startAlloy(data: String) {
        logd(data)
        val result = JSONObject(data)
        when (result.getString("status").lowercase()) {
            "closed" -> Alloy.finishCancelled()
            "pending_step_up" -> Alloy.finishCancelled()
            "completed" -> {
                when (result.getString("outcome").lowercase()) {
                    "approved" -> Alloy.finishSuccess()
                    "manual review" -> Alloy.finishManualReview()
                    "denied" -> Alloy.finishDenied()
                }
            }
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
