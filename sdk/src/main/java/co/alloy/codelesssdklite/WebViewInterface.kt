package co.alloy.codelesssdklite

import android.webkit.JavascriptInterface
import org.json.JSONObject

class WebViewInterface {
    @JavascriptInterface
    fun startAlloy(data: String) {
        logd(data)
        Alloy.finishCancelled()
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
