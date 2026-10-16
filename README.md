# Mikdad Stream

Aplikasi streaming native Android (Kotlin + Jetpack Compose) yang terhubung ke server API sendiri (Mikdad API) dengan kontrak API kompatibel CineFlow.

## Fitur

- Katalog 12 sumber (moviebox, samehadaku, youku, anichin, dll)
- Detail judul + daftar episode
- Pemutar video lengkap:
  - Kecepatan 0.5×–2×
  - Rasio layar Muat / Penuh / Zoom
  - Subtitle otomatis dari API + pilih manual
  - Gestur kecerahan (kiri), volume (kanan), geser waktu (tengah)
  - Tap = tampil/sembunyi kontrol, tap ganda = ±10 detik
  - Picture-in-Picture
  - Timer tidur 15/30/45/60 menit
  - Maju/mundur otomatis antar episode
- Pemutaran **native** (ExoPlayer/Media3) — tanpa WebView / hoster embed
- Riwayat tontonan + posisi terakhir

> Peta kode lengkap untuk kontributor/AI ada di [`AGENTS.md`](AGENTS.md).

## Bangun APK

```sh
export ANDROID_HOME=/path/to/android-sdk
./gradlew assembleDebug assembleRelease
```

Hasil build ada di `app/build/outputs/apk/`.

Untuk merilis, isi konfigurasi signing di `app/build.gradle.kts` (saat ini signing release dilakukan manual via `apksigner`).

## Struktur

- `app/src/main/java/com/mikdadstream/app/` — kode sumber Kotlin
- `app/src/main/res/` — resource Android
- `gradle/` — wrapper Gradle

## Lisensi

Kode aplikasi ini milik sendiri. Konten streaming mengikuti ketentuan sumber API yang dipakai.
