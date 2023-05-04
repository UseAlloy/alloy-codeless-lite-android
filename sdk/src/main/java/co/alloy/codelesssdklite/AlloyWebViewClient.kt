package co.alloy.codelesssdklite

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri
import androidx.webkit.WebViewAssetLoader
import com.google.gson.Gson

class AlloyWebViewClient(
    private val host: String,
    private val initialUrl: String,
    private val assetLoader: WebViewAssetLoader,
    private val alloySettings: AlloySettings,
    private val function: Function,
) : WebViewClient() {
    private fun shouldIntercept(uri: Uri): WebResourceResponse? {
        if (host.equals(uri.host, ignoreCase = true)) {
            return assetLoader.shouldInterceptRequest(uri)
        }
        return null
    }

    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
        return shouldIntercept(request.url)
    }

    @Deprecated("Deprecated in Java")
    override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
        return shouldIntercept(url.toUri())
    }

    override fun onPageFinished(view: WebView, url: String?) {
        super.onPageFinished(view, url)
        logd("onPageFinished $url")
        if (initialUrl.equals(url, ignoreCase = true)) {
            when (function) {
                Function.START_ALLOY -> {
                    val parameters = Gson().toJson(alloySettings)

                    logd("Init parameters $parameters")

                    view.loadUrl(
                        """
                        javascript:(function() {
                            window.StartAlloy($parameters);
                        })()
                    """
                    )
                }
                Function.CREATE_JOURNEY_APPLICATION -> {
                    val settings = Gson().toJson(alloySettings.copy(journeyData = null))
                    val journeyData = Gson().toJson(alloySettings.journeyData)

                    view.loadUrl(
                        """
                        javascript:(function() {
                            window.CreateJourneyApplication($settings, $journeyData);
                        })()
                    """
                    )
                }
            }
        }
    }
}
