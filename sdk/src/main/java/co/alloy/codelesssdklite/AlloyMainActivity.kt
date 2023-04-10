package co.alloy.codelesssdklite

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.BuildConfig
import androidx.webkit.WebViewAssetLoader
import co.alloy.codelesssdklite.databinding.AlloyActivityMainBinding

internal fun Context.showAlloy() {
    startActivity(
        Intent(this, AlloyMainActivity::class.java)
    )
}

class AlloyMainActivity : AppCompatActivity() {
    private lateinit var binding: AlloyActivityMainBinding

    private var permissionRequest: PermissionRequest? = null
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { granted ->
        logd("Camera permission request result: $granted ${permissionRequest?.resources?.joinToString()}")
        if (granted.all { it.value }) {
            permissionRequest?.let {
                it.grant(it.resources)
            }
        }
        permissionRequest = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AlloyActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureWebView()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    Alloy.finishCancelled()
                    finish()
                }
            }
        })
    }

    private val host = "appassets.androidplatform.net"
    private val initialUrl = "https://$host/index.html"
    private val alloySettings: AlloySettings by lazy { Alloy.settings!! }

    private fun configureWebView() = with(binding.webView) {
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)

        settings.apply {
            useWideViewPort = false
            setSupportZoom(false)
            javaScriptCanOpenWindowsAutomatically = true
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
        }
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        addJavascriptInterface(WebViewInterface(), "CallbackObject")

        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler(
                "/",
                WebViewAssetLoader.AssetsPathHandler(context),
            )
            .build()
        webViewClient = AlloyWebViewClient(host, initialUrl, assetLoader, alloySettings)

        webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                this@AlloyMainActivity.filePathCallback = filePathCallback
                pickFile.launch(fileChooserParams?.acceptTypes?.firstOrNull() ?: "image/*")

                return true
            }

            override fun onPermissionRequest(request: PermissionRequest) {
                permissionRequest = request
                logd("onPermissionRequest ${request.resources.joinToString()}")
                requestCameraPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.BLUETOOTH,
                    )
                )
            }
        }
        binding.webView.loadUrl(initialUrl)
    }

    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private val pickFile = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (null == it) {
            filePathCallback?.onReceiveValue(null)
        } else {
            filePathCallback?.onReceiveValue(arrayOf(it))
        }
    }
}
