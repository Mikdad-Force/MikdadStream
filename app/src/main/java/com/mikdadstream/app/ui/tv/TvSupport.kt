package com.mikdadstream.app.ui.tv

import android.app.UiModeManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration

fun Context.isTelevision(): Boolean {
    val uiMode = getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager
    val tvMode = uiMode?.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION
    val leanback = packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
    return tvMode || leanback
}
