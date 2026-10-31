package com.mikdadstream.app.data.local

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Perekam log subtitle di memori, agar pengguna bisa melihat sendiri apa yang
 * terjadi saat memilih subtitle tanpa perlu `adb logcat`.
 *
 * Sengaja TIDAK memakai file: log hanya untuk diagnosa sesi ini, dibatasi
 * [MAX_LINES] baris supaya tidak membebani memori saat pemutaran panjang.
 *
 * Ditulis dari pemain (thread mana pun) dan dibaca dari layar Pengaturan.
 */
object SubtitleLog {

    private const val MAX_LINES = 300
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
    private val lock = Any()

    private val _lines = MutableStateFlow<List<String>>(emptyList())
    val lines: StateFlow<List<String>> = _lines

    /** Tambah satu baris log; baris terlama dibuang bila melewati batas. */
    fun add(message: String) {
        synchronized(lock) {
            // SimpleDateFormat tidak thread-safe: format di dalam lock.
            val line = "${timeFormat.format(Date())}  $message"
            val next = _lines.value + line
            _lines.value = if (next.size > MAX_LINES) next.takeLast(MAX_LINES) else next
        }
    }

    fun clear() {
        synchronized(lock) { _lines.value = emptyList() }
    }

    /** Teks siap salin; urut dari yang paling lama. */
    fun asText(): String =
        if (_lines.value.isEmpty()) "Belum ada catatan subtitle."
        else _lines.value.joinToString("\n")
}
