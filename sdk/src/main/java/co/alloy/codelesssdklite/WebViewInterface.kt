package co.alloy.codelesssdklite

import android.webkit.JavascriptInterface
import org.json.JSONObject

class WebViewInterface {
    @JavascriptInterface
    fun callback(data: String) {
        logd(data)
        val result = JSONObject(data)
        when (result.getString("status").lowercase()) {
            "closed" -> Alloy.finishCancelled()
            "completed" -> {
                when (result.getString("outcome").lowercase()) {
                    "approved" -> Alloy.finishSuccess()
                    "manual review" -> Alloy.finishManualReview()
                    "denied" -> Alloy.finishDenied()
                }
            }
        }
    }
}
