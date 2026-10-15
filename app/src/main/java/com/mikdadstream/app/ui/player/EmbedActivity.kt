package com.mikdadstream.app.ui.player

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Pemutar untuk sumber bertipe hoster/embed (Blogger, Mega, Gofile, dll).
 * Halaman hostar dimuat di WebView — sama seperti aplikasi sumbernya.
 */
class EmbedActivity : ComponentActivity() {

    private lateinit var rootFrame: FrameLayout
    private lateinit var header: LinearLayout
    private lateinit var titleView: TextView
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private var chrome: WebChromeClient? = null

    private var customView: View? = null
    private var customCallback: WebChromeClient.CustomViewCallback? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        val url = intent.getStringExtra(EXTRA_URL)
        val initialTitle = intent.getStringExtra(EXTRA_TITLE) ?: "Pemutar Web"
        if (url.isNullOrBlank()) {
            finish()
            return
        }

        val density = resources.displayMetrics.density
        fun px(v: Int): Int = (v * density + 0.5f).toInt()

        rootFrame = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }

        header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(0xE6000000.toInt())
            setPadding(px(6), px(8), px(14), px(8))
        }
        val back = TextView(this).apply {
            text = "\u2190"
            setTextColor(Color.WHITE)
            textSize = 20f
            setPadding(px(14), px(6), px(18), px(6))
            setOnClickListener { handleBack() }
        }
        titleView = TextView(this).apply {
            text = initialTitle
            setTextColor(Color.WHITE)
            textSize = 15f
            maxLines = 1
            ellipsize = android.text.TextUtils.TruncateAt.END
        }
        header.addView(back)
        header.addView(
            titleView,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f),
        )

        webView = WebView(this)
        progressBar = ProgressBar(this).apply { visibility = View.GONE }

        val content = FrameLayout(this).apply {
            addView(
                webView,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT,
                ),
            )
            addView(
                progressBar,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER,
                ),
            )
        }

        val body = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(header, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            addView(content, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f))
        }
        rootFrame.addView(
            body,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
            ),
        )
        setContentView(rootFrame)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            mediaPlaybackRequiresUserGesture = false
            loadsImagesAutomatically = true
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(false)
            builtInZoomControls = false
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            userAgentString =
                "Mozilla/5.0 (Linux; Android 14; SM-S918B) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/126.0.0.0 Mobile Safari/537.36"
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val scheme = request.url.scheme?.lowercase()
                return if (scheme == "http" || scheme == "https") {
                    false
                } else {
                    runCatching { startActivity(Intent(Intent.ACTION_VIEW, request.url)) }
                    true
                }
            }
        }

        chrome = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.visibility = if (newProgress in 1..99) View.VISIBLE else View.GONE
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                if (!title.isNullOrBlank()) titleView.text = title
            }

            override fun onShowCustomView(view: View?, callback: WebChromeClient.CustomViewCallback?) {
                if (view == null || customView != null) {
                    callback?.onCustomViewHidden()
                    return
                }
                (view.parent as? ViewGroup)?.removeView(view)
                customView = view
                customCallback = callback
                webView.visibility = View.GONE
                header.visibility = View.GONE
                rootFrame.addView(
                    view,
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                    ),
                )
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }

            override fun onHideCustomView() {
                exitFullscreen()
            }
        }
        webView.webChromeClient = chrome

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBack()
            }
        })

        webView.loadUrl(url)
    }

    private fun handleBack() {
        when {
            customView != null -> exitFullscreen()
            webView.canGoBack() -> webView.goBack()
            else -> finish()
        }
    }

    private fun exitFullscreen() {
        val view = customView ?: return
        (view.parent as? ViewGroup)?.removeView(view)
        customCallback?.onCustomViewHidden()
        customView = null
        customCallback = null
        webView.visibility = View.VISIBLE
        header.visibility = View.VISIBLE
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onDestroy() {
        if (::webView.isInitialized) {
            runCatching { webView.destroy() }
        }
        super.onDestroy()
    }

    companion object {
        private const val EXTRA_URL = "url"
        private const val EXTRA_TITLE = "title"

        fun intent(context: Context, url: String, title: String): Intent =
            Intent(context, EmbedActivity::class.java).apply {
                putExtra(EXTRA_URL, url)
                putExtra(EXTRA_TITLE, title)
            }
    }
}
