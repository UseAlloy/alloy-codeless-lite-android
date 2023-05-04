package co.alloy.codelesssdklite

import android.webkit.JavascriptInterface

class WebViewInterfaceCreateJourneyApplicationResponse {
    @JavascriptInterface
    fun callback(data: String) {
        logd(data)
        Alloy.journeyApplicationTokenCreated(data)
    }
}
