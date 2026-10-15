package com.mikdadstream.app.ui.tv

import android.app.UiModeManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import com.mikdadstream.app.BuildConfig

fun Context.isTelevision(): Boolean {
    if (BuildConfig.DEBUG && BuildConfig.FORCE_TV) return true
    val uiMode = getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager
    val tvMode = uiMode?.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION
    val leanback = packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
    return tvMode || leanback
}
