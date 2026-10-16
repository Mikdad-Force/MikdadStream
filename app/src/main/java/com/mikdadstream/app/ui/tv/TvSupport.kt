package com.mikdadstream.app.ui.tv

import android.app.UiModeManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import com.mikdadstream.app.BuildConfig
import kotlinx.coroutines.delay

fun Context.isTelevision(): Boolean {
    if (BuildConfig.DEBUG && BuildConfig.FORCE_TV) return true
    val uiMode = getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager
    val tvMode = uiMode?.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION
    val leanback = packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
    val noTouch = !packageManager.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
    return tvMode || leanback || noTouch
}

/**
 * Minta fokus ke [target] saat sebuah layar/overlay/dialog tampil, tetapi
 * HANYA di Android TV. Di ponsel tidak ada perubahan agar desain tetap sama.
 */
@Composable
fun TvRequestFocusOnShow(
    target: FocusRequester,
    enabled: Boolean = true,
    vararg keys: Any?,
) {
    val isTv = LocalContext.current.isTelevision()
    LaunchedEffect(isTv, enabled, *keys) {
        if (!isTv || !enabled) return@LaunchedEffect
        delay(140)
        runCatching { target.requestFocus() }
    }
}
