package com.mikdadstream.app.data.notify

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

/**
 * Penjadwal pengecekan film/episode baru memakai WorkManager.
 *
 * Dipanggil sekali saat aplikasi start ([schedule]) — WorkManager menyimpan
 * jadwalnya sendiri sehingga pengecekan tetap berjalan walau aplikasi tertutup.
 * Interval minimum WorkManager adalah 15 menit; dipakai 6 jam agar hemat
 * baterai dan kuota, karena konten baru tidak perlu diketahui secepat itu.
 */
object NewContentScheduler {

    private const val WORK_NAME = "new_content_check"

    fun schedule(context: Context) {
        val request = PeriodicWorkRequestBuilder<NewContentWorker>(6, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build(),
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            // KEEP: jangan buat ulang jadwal setiap aplikasi dibuka, agar
            // pengecekan tidak menumpuk dan jadwal tetap stabil.
            ExistingPeriodicWorkPolicy.KEEP,
            request,
        )
    }

    fun cancel(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}
