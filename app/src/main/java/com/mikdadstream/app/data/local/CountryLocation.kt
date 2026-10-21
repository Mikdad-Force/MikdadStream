package com.mikdadstream.app.data.local

import java.util.Locale

/**
 * Lokasi negara yang dipakai bersama oleh Moviebox (region X-Client-Info)
 * dan playlist Live TV / Sport TV. Kode mengikuti ISO 3166-1 alpha-2,
 * kecuali playlist iptv-org yang memakai kode khusus (Inggris = "uk").
 */
data class CountryLocation(
    val code: String,
    val name: String,
    val timezone: String,
    val spCode: String,
    val language: String,
    val playlist: String = code.lowercase(Locale.ROOT),
) {
    val flag: String
        get() = code.uppercase(Locale.ROOT).map { c ->
            Character.toChars(0x1F1E6 + (c - 'A')).concatToString()
        }.joinToString("")
}

object CountryLocations {
    val all: List<CountryLocation> = listOf(
        CountryLocation("ID", "Indonesia", "Asia/Jakarta", "51010", "id"),
        CountryLocation("MY", "Malaysia", "Asia/Kuala_Lumpur", "50202", "ms"),
        CountryLocation("SG", "Singapura", "Asia/Singapore", "40401", "en"),
        CountryLocation("TH", "Thailand", "Asia/Bangkok", "40401", "th"),
        CountryLocation("PH", "Filipina", "Asia/Manila", "40401", "en"),
        CountryLocation("VN", "Vietnam", "Asia/Ho_Chi_Minh", "40401", "vi"),
        CountryLocation("US", "Amerika Serikat", "America/New_York", "40401", "en"),
        CountryLocation("GB", "Inggris", "Europe/London", "40401", "en", playlist = "uk"),
        CountryLocation("AU", "Australia", "Australia/Sydney", "40401", "en"),
        CountryLocation("CA", "Kanada", "America/Toronto", "40401", "en"),
        CountryLocation("JP", "Jepang", "Asia/Tokyo", "40401", "ja"),
        CountryLocation("KR", "Korea Selatan", "Asia/Seoul", "40401", "ko"),
        CountryLocation("CN", "Tiongkok", "Asia/Shanghai", "40401", "zh"),
        CountryLocation("TW", "Taiwan", "Asia/Taipei", "40401", "zh"),
        CountryLocation("HK", "Hong Kong", "Asia/Hong_Kong", "40401", "zh"),
        CountryLocation("IN", "India", "Asia/Kolkata", "40401", "en"),
        CountryLocation("DE", "Jerman", "Europe/Berlin", "40401", "de"),
        CountryLocation("FR", "Prancis", "Europe/Paris", "40401", "fr"),
        CountryLocation("NL", "Belanda", "Europe/Amsterdam", "40401", "nl"),
        CountryLocation("BR", "Brasil", "America/Sao_Paulo", "40401", "pt"),
        CountryLocation("SA", "Arab Saudi", "Asia/Riyadh", "40401", "ar"),
        CountryLocation("AE", "Uni Emirat Arab", "Asia/Dubai", "40401", "ar"),
        CountryLocation("TR", "Turki", "Europe/Istanbul", "40401", "tr"),
        CountryLocation("NG", "Nigeria", "Africa/Lagos", "40401", "en"),
        CountryLocation("ZA", "Afrika Selatan", "Africa/Johannesburg", "40401", "en"),
    )

    fun byCode(code: String?): CountryLocation {
        val normalized = code?.trim()?.uppercase(Locale.ROOT)
        return all.firstOrNull { it.code == normalized } ?: all.first()
    }
}

/** Kode lokasi aktif; diperbarui oleh [PrefsStore.setRegion]. */
object CountrySelection {
    @Volatile
    var code: String = "ID"

    fun current(): CountryLocation = CountryLocations.byCode(code)
}
