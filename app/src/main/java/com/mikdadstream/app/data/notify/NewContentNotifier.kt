package com.mikdadstream.app.data.notify

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.mikdadstream.app.R

/**
 * Notifikasi "Film Baru" dan "Episode Baru".
 *
 * Dua channel terpisah supaya pengguna bisa mematikan salah satu dari setelan
 * Android. Di Android 13+ izin POST_NOTIFICATIONS harus sudah diberikan;
 * [canNotify] memeriksa itu sebelum benar-benar menampilkan.
 */
object NewContentNotifier {

    const val CHANNEL_MOVIE = "new_movie"
    const val CHANNEL_EPISODE = "new_episode"

    fun ensureChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val nm = context.getSystemService(NotificationManager::class.java) ?: return
        nm.createNotificationChannel(
            NotificationChannel(
                CHANNEL_MOVIE,
                "Film Baru",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply { description = "Judul baru di katalog" },
        )
        nm.createNotificationChannel(
            NotificationChannel(
                CHANNEL_EPISODE,
                "Episode Baru",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply { description = "Episode baru dari judul yang Anda simpan" },
        )
    }

    fun canNotify(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) return false
        }
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    /**
     * Tampilkan satu notifikasi. [id] harus stabil per judul agar notifikasi
     * judul yang sama menimpa versi lama, bukan menumpuk.
     */
    fun show(
        context: Context,
        channel: String,
        id: Int,
        title: String,
        text: String,
    ) {
        if (!canNotify(context)) return
        ensureChannels(context)

        val launch = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pi = launch?.let {
            PendingIntent.getActivity(
                context,
                id,
                it,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        }
        val notification = NotificationCompat.Builder(context, channel)
            // Small icon wajib siluet putih; adaptive launcher icon tidak boleh.
            .setSmallIcon(R.drawable.ic_notify_new)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .apply { if (pi != null) setContentIntent(pi) }
            .build()

        runCatching {
            NotificationManagerCompat.from(context).notify(id, notification)
        }
    }
}
