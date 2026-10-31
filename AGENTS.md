# AGENTS.md

Native Android streaming app (Kotlin + Compose + Media3). Modul Gradle tunggal `:app`. Nama aplikasi `AkashaStream`, tapi package/applicationId tetap `com.mikdadstream.app` — jangan rename package.

## Aturan kerja (wajib)

1. **Pahami dulu, jangan tanya ulang.** Pakai error/log/stack trace/konteks yang sudah ada. Periksa struktur hanya bila perlu.
2. **Investigasi terarah.** Telusuri hanya file & dependensi terkait; cari akar penyebab sebelum ubah kode. Jangan baca ulang file yang sama, jangan scan seluruh repo untuk bug lokal.
3. **Langsung perbaiki.** Jika akar penyebab jelas, ubah kode dengan patch terkecil yang benar. Jangan berhenti di saran, jangan ubah file tak terkait.
4. **Hindari pengulangan.** Jangan ulangi analisis/perintah yang sama; jika gagal, baca error terbaru dan ganti strategi. Jika tak ada kemajuan, berhenti dan laporkan hambatan nyata. Jangan buat rencana panjang untuk bug sederhana.
5. **Uji yang paling relevan dulu,** periksa hasilnya sebelum menyatakan selesai. Jangan perbaiki error tak terkait.
6. **Efisien token.** Pakai konteks percakapan yang ada; pakai pencarian terarah, bukan baca file besar; batasi output terminal; laporan akhir singkat. Ketepatan jangan dikorbankan demi token.
7. **Keamanan.** Pertahankan perubahan pengguna yang tak terkait. Tanpa persetujuan: jangan hapus data/perintah destruktif, jangan commit/push/deploy. Jangan tampilkan rahasia/API key.

**Format laporan bug (singkat untuk bug sederhana):** `Penyebab:` / `Perbaikan:` / `File diubah:` / `Pengujian:` / `Status:`

## Commands

```sh
./gradlew :app:compileDebugKotlin   # verifikasi tercepat (tak ada test/lint di repo)
./gradlew assembleDebug
./gradlew assembleRelease           # minified; APK di app/build/outputs/apk/release/
```

- **Tidak ada test suite** (tanpa `src/test`/`src/androidTest`) dan **tidak ada task lint** — jangan mengarang perintah test/lint. Verifikasi = kompilasi + baca kode.
- **Di working copy ini `java` & Android SDK tidak tersedia** (`ANDROID_HOME` kosong), jadi Gradle tak bisa jalan. Build gagal ≠ kode rusak; verifikasi lewat baca kode/config atau siapkan toolchain (JDK 17 + SDK, sdk.dir via `local.properties`/`ANDROID_HOME`).
- Tidak ada `@Preview`; verifikasi UI dengan membaca kode.
- Wrapper Gradle 8.9 ter-track; jangan regenerate. `.gradle/`/`build/` di-ignore.
- **Dua file raksasa — jangan dibaca utuh.** `ui/player/PlayerScreen.kt` (~3000 baris) dan `data/moviebox/LocalMovieBoxClient.kt` (~2000 baris). Pakai `grep -n` lalu baca rentang baris sempit (mis. subtitle: `grep -n "subtitle" PlayerScreen.kt`, range `270–600`).

## Arsitektur

- `MikdadStreamApp.kt` → `object AppGraph`: DI manual (tanpa Hilt). Singleton baru didaftarkan & di-init di `AppGraph.init()`.
- `ui/nav/AppNavGraph.kt` + `MainViewModel`: navigasi Compose. Player adalah **Activity terpisah** (`ui/player/PlayerActivity`, `sensorLandscape`, PiP) via `PlayerActivity.intent(...)`, bukan tujuan nav.
- `data/remote/` — Retrofit `MikdadApi` + `ApiClient`/`AuthGateway` (refresh token/nonce). `StreamRepository` = facade + cache in-memory; panggil di `Dispatchers.IO` (Retrofit blocking).
- `data/moviebox/` — `LocalMovieBoxClient` memanggil langsung `api*.aoneroom.com` dengan HMAC-MD5 `x-tr-signature` (`SECRET_DEFAULT`); `MovieboxResolver` memetakan JSON ke DTO. Ini sumber default aktif (`PrefsStore.DEFAULT_SOURCE = "moviebox-local"`, `PrefsStore.init` memaksa reset ke itu).
- Daftar sumber **tidak hardcoded**: `StreamRepository.models()` ambil dari remote `GET api/modelles/models` lalu menambah entri sintetis `moviebox-local` ("Utama"). "12 sumber" di README = katalog server, bukan kode lokal. Hanya MovieBox yang punya client lokal.
- `data/local/` — Room `AppDatabase` v2, `exportSchema = false`, `.fallbackToDestructiveMigration()`.
- `data/firebase/` — Auth + Firestore (`mikdad-a7a8a`, `google-services.json` ter-commit). `data/update/` — `AppUpdater` cek release GitHub `UPDATE_REPO` (default `Mikdad-Force/MikdadStream`) → APK via FileProvider.

## Bukan source Kotlin

- `moviebox-source/` & `app/src/main/assets/moviebox-source/`: smali hasil decompile `com.gengaworld.app` v2.5 + `docs/`. Hanya referensi, **bukan input build** — jangan dikompilasi/diedit sebagai bagian build.

## Konvensi

- Teks UI, `Log.*`, dan subject commit berbahasa Indonesia (istilah teknis tetap Inggris).
- String UI di `ui/i18n/AppStrings.kt` (`Id`/`En`/`Su`, via `LocalStrings.current`/`appStringsFor`). Tambah string di **ketiga** tabel — bukan di `res/values/strings.xml` (hanya berisi `app_name`).
- Warna/tipografi di `ui/theme/Theme.kt` (`StreamBg`, `AccentChoice`/`LocalAccent`) — jangan taruh warna UI di `colors.xml`.
- Konfigurasi lewat Gradle property → `BuildConfig`: `STREAM_API_URL`, `VERSION_CODE`, `VERSION_NAME`, `GOOGLE_CLIENT_ID`, `FORCE_TV`, `UPDATE_REPO`, `STREAM_*_PASSWORD/ALIAS`. Jangan hardcode di tempat lain.
- TV first-class: `ui/tv/TvSupport.kt` + `FORCE_TV=true` (override debug) untuk pratinjau TV di HP; manifest pakai `LEANBACK_LAUNCHER`. Sebutkan bila perubahan menyentuh jalur TV.
- Release signing: bila `mikdadstream.keystore` tak ada, build **auto-generate keystore sekali pakai** via `keytool`. Jangan commit keystore.
- Proguard: `data.remote.**` di-keep; sinkronkan bila tambah DTO di luar paket itu.
- CI (`.github/workflows/main.yml`): push ke `main`/`master` → build release, `VERSION_CODE=github.run_number`, release tag `v<appVersionName>`. Rilis baru = naikkan `appVersionName` **dan** `appVersionCode` (default saat ini `2.1.66`/`96`).

## Jebakan

- **Area bug berulang: subtitle & pemilihan episode.** Isi subtitle berasal dari `LocalMovieBoxClient.subtitleInfo()`/`buildStreams()` lalu dinormalisasi & digeser di `PlayerScreen.kt` (`normalizeSubtitleText`, `shiftedSubtitleUrl`, `subtitleMime`). Bug "teks dialog rusak" / "subtitle tidak sinkron" / "episode salah" nyaris selalu di sini — mulai dari sana, bukan dari UI umum.
- Room `fallbackToDestructiveMigration`: naikkan versi tanpa `Migration` = data download/riwayat hilang diam-diam.
- `ApiError` (`data/remote/ApiClient.kt`) adalah error yang diharapkan; repository memetakan `HttpException`/IO ke sana — jangan lempar exception mentah di UI/repository.
- `LocalMovieBoxClient` butuh guest token (`boot()` di `AppGraph.init`); setelah `onRegionChanged()` mungkin perlu boot ulang.
- `usesCleartextTraffic="true"` disengaja untuk endpoint/redirect HTTP — jangan dimatikan tanpa cek playback.
