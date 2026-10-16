# AGENTS.md — Peta Kode & Panduan AI (Mikdad Stream)

> Dokumen ini dibuat agar AI/agen kode cepat memahami struktur proyek dan tidak salah arah.
> Selalu baca ini dulu sebelum mengubah kode. Semua path relatif ke root repo.

---

## 1. Ringkasan Proyek

- **Aplikasi**: Mikdad Stream — streaming native Android (Kotlin + Jetpack Compose, Material 3).
- **Modul**: satu modul Gradle `:app` (tidak ada multi-module).
- **Package**: `com.mikdadstream.app`.
- **SDK**: `minSdk=24`, `targetSdk=34`, `compileSdk=36`, `applicationId=com.mikdadstream.app`.
- **Bahasa UI**: Indonesia. Tema **gelap** (default), aksen biru `#24A0ED`.
- **Backend**: "Mikdad API" (Cloudflare Workers, kontrak kompatibel CineFlow). Base URL di-set saat build via `STREAM_API_URL`
  (default `https://mikdad-api.mikdadtv.workers.dev`).
- **DI**: manual container `AppGraph` (object) — **bukan** Hilt/Dagger.
- **Pemutaran**: **WAJIB native** ExoPlayer/Media3. **TIDAK ADA WebView / hoster web** untuk memutar video (aturan tetap sejak v1.3.0).
- **Target perangkat**: Android TV (remote/D-pad) & ponsel.

## 2. Perintah Penting (jalankan dari root repo)

```sh
export ANDROID_HOME=/opt/android-sdk
./gradlew :app:assembleDebug :app:assembleRelease --console=plain
```

- Debug ≈ 30–40 s; Release ≈ 4–5 m (R8 + `lintVitalRelease`).
- Hasil: `app/build/outputs/apk/debug/app-debug.apk` dan `app/build/outputs/apk/release/app-release.apk`.
- Signing release otomatis dari `mikdadstream.keystore` (store/key pass & alias = `mikdadstream`), lihat `signingConfigs` di `app/build.gradle.kts`.
- Memaksa mode TV saat debug: `-PFORCE_TV=true` (dipakai `isTelevision()`).
- **Tidak ada test** (`app/src/test` dan `app/src/androidTest` kosong). Verifikasi = build sukses.
- Setelah perubahan, jalankan `assembleDebug` (cepat) untuk cek kompilasi; `assembleRelease` untuk validasi R8/lint.

## 3. Alur Aplikasi (startup)

```
MikdadStreamApp (Application) → AppGraph.init()
   └─ MainActivity.setContent → MikdadStreamTheme + LocalStrings
        └─ AppRoot(isTv)                         [MainActivity.kt]
             ├─ UpdateScreen        (jika ada update wajib; memblokir)
             ├─ SplashScreen        (saat booting)
             ├─ AuthFlow(isTv)      (belum login)
             │     ├─ TV   : Onboarding → TvPairingScreen (login via kode QR)
             │     └─ Ponsel: Onboarding → SignIn/SignUp/Forgot/OTP/NewPass/Pin/CreateProfile
             └─ AppNavGraph(isTv)   (sudah login)
                   ├─ TV   : TvTopBar + TvHomeScreen
                   └─ Ponsel: HomeScreen + FloatingBottomNavBar
```

`AppGraph` (di `MikdadStreamApp.kt`) menyediakan:
`prefs` (`PrefsStore`), `database` (`AppDatabase`), `apiClient` (`ApiClient`), `repository` (`StreamRepository`),
`downloads` (`DownloadManager`), `users` (`UserRepository`), `updater` (`AppUpdater`).

## 4. Peta Direktori & Tanggung Jawab File

Struktur: `app/src/main/java/com/mikdadstream/app/`

### Root
| File | Peran |
|---|---|
| `MikdadStreamApp.kt` | `Application` + container DI `AppGraph` (init prefs/Room/Retrofit/repo/downloads/firebase/updater). |
| `MainActivity.kt` | `setContent`, tema, `AppRoot` (splash/update/auth/app), `AuthFlow` ponsel (termasuk Google Sign-In). |

### `data/`
| File | Peran |
|---|---|
| `remote/ApiClient.kt` | Retrofit/OkHttp, `ApiConfig`, `AuthGateway` (nonce→login, refresh token, sesi), `ApiError`, `api` (`MikdadApi`). |
| `remote/MikdadApi.kt` | Interface endpoint REST (lihat §5). |
| `remote/Dtos.kt` | Semua model JSON (envelope, model, kategori, video, detail, source, search, auth, pairing, akun). |
| `local/PrefsStore.kt` | Penyimpanan preferensi (DataStore) — sesi, sumber aktif, tema, bahasa, premium, dsb. |
| `local/AppDatabase.kt` | Room DB (`mikdad_stream.db`): DAO watchlist, history, search history, downloads + entity. |
| `repository/StreamRepository.kt` | Gabungkan API + Room (models, kategori, video, detail, source, search, watchlist, history). |
| `firebase/UserRepository.kt` | Firebase Auth + Firestore (akun, premium, tipe akun Developers/Premium/Normal). |
| `download/DownloadManager.kt` | Unduh file (unduhan offline) + status/progress. |
| `update/AppUpdater.kt` | Cek update dari GitHub release, unduh APK, install (REQUIRE_INSTALL_PACKAGES). |

### `ui/`
| File | Peran |
|---|---|
| `nav/AppNavGraph.kt` | `NavHost` + `Scaffold`, `Routes` (lihat §6), `MainViewModel`, fokus awal TV (`routeFocus`). |
| `nav/MainViewModel.kt` | State bersama: profil, sumber aktif, daftar model, sheet sumber. |
| `home/HomeScreen.kt` / `HomeViewModel.kt` | Beranda ponsel (hero karusel, section, riwayat). |
| `tv/TvHomeScreen.kt` | Beranda TV: hero + baris daftar, `TvTopBar`/`TvNavItem`, fokus tombol **Putar**. |
| `tv/TvPairingScreen.kt` | Login TV via kode QR/device pairing. |
| `tv/TvSupport.kt` | `Context.isTelevision()` + helper `TvRequestFocusOnShow()` (fokus khusus TV). |
| `search/SearchScreen.kt` / `SearchViewModel.kt` | Pencarian (TextField + grid hasil + riwayat). |
| `library/LibraryScreen.kt` | Koleksi: tab Watchlist/Riwayat + grid. |
| `detail/DetailScreen.kt` / `DetailViewModel.kt` | Detail judul, episode, tombol putar, unduh, dialog premium/unduh. |
| `player/PlayerActivity.kt` | Activity pemutar (orientation landscape, PiP) + `dispatchKeyEvent` untuk remote. |
| `player/PlayerScreen.kt` | UI pemutar Compose: kontrol, gestur, subtitle, sheet sumber/episode, `OptionMenu`, `PlayerRemoteController`. |
| `downloads/DownloadsScreen.kt` | Daftar unduhan + putar offline. |
| `settings/SettingsScreen.kt` / `SettingsViewModel.kt` | Profil & pengaturan + dialog (nama, cek sumber, konfirmasi). |
| `onboarding/OnboardingScreens.kt` | Onboarding 3 halaman (ponsel & TV pre-login). |
| `update/UpdateScreen.kt` | Layar update wajib. |
| `auth/AuthScreens.kt` | SignIn, SignUp, Forgot, OTP, NewPassword, CreatePin, CreateProfile, Google Sign-In flow. |
| `account/AccountScreens.kt` | Edit profil, ubah sandi, bahasa, notifikasi, keamanan, bantuan, legal, logout, kategori favorit, berbagi keluarga. |
| `account/PairDeviceScreen.kt` | Pairing perangkat dari sisi ponsel (QR). |
| `subscription/SubscriptionScreens.kt` | Langganan, tambah kartu, sukses bayar (Premium Rp10.000). |
| `kit/KitScreens.kt` | Design-kit / layar pratinjau (bukan alur utama). |
| `components/Common.kt` | `ShimmerBox`, `PosterImage`, `VideoCard`, `SectionHeader`, `FilterChipPill`, `FullScreenState`, `LoadingGrid`. |
| `components/Header.kt` | `AppHeader` (ponsel) + `SourceSheet` (bottom sheet pilih sumber video). |
| `components/FloatingBottomNavBar.kt` | Navbar bawah ponsel. |
| `components/Stream.kt` | Tombol/field gaya Streamview (`StreamPrimaryButton`, `StreamOutlineButton`, `StreamTextField`). |
| `components/Modifiers.kt` | `pressScale`, `softShadow` (pakai `LocalIndication`). |
| `components/TvFocusIndication.kt` | Indikator fokus remote (border biru) saat fokus/tekan. |
| `theme/Theme.kt` | `MikdadStreamTheme`, skema warna; menyediakan `LocalIndication = TvFocusIndication`. |
| `theme/Fonts.kt` | Definisi FontFamily (Montserrat, Mulish, Plus Jakarta). |
| `i18n/AppStrings.kt` | `AppStrings`, `LocalStrings`, `appStringsFor(language)`. |

### Resource & konfigurasi lain
- `app/src/main/res/`: `values/{colors,strings,themes}.xml`, `drawable/` (mis. `tv_banner`), `font/` (ttf), ikon launcher, `xml/file_paths.xml` (FileProvider update).
- `app/src/main/AndroidManifest.xml`: activity `MainActivity` (LAUNCHER + **LEANBACK_LAUNCHER**), `PlayerActivity` (PiP, landscape), FileProvider `*.updates`.
- `app/build.gradle.kts`: konfigurasi app, `versionCode/versionName`, `BuildConfigField` (`API_BASE_URL`, `GOOGLE_WEB_CLIENT_ID`, `FORCE_TV`, `UPDATE_REPO`), signing.
- `app/google-services.json`: konfigurasi Firebase (ada).
- `settings.gradle.kts`, `build.gradle.kts`, `gradle.properties`, `gradle/wrapper/`.

## 5. Endpoint API (`MikdadApi.kt`)

Katalog/streaming:
- `GET api/modelles/models`
- `GET api/modelles/categories?model_id=`
- `GET api/modelles/videos?model_id=&category_id=&page=`
- `GET api/modelles/detail?model_id=&id=`
- `GET api/modelles/source?model_id=&episode_id=&id=`
- `GET api/modelles/download?model_id=&episode_id=&id=`
- `POST api/modelles/search`

Akun/auth (via `AuthGateway`):
- `GET api/app/account/status`
- `POST api/app/auth/nonce`
- `POST api/app/auth/login/google-account`
- `POST api/app/auth/refresh-token`
- `POST api/app/auth/device/pairing`
- `GET api/app/auth/device/status?device_code=&app_instance_id=`
- `POST api/app/auth/device/approve`
- `POST api/app/auth/device/exchange`

Token disimpan di `PrefsStore`; `AuthGateway` mengurus nonce→login, refresh, dan pemulihan sesi (OkHttp blocking, panggil dari thread IO).

## 6. Rute Navigasi (`Routes` di `AppNavGraph.kt`)

`home`, `search`, `library`, `history`, `downloads`, `settings`, `detail?modelId&id&source&autoplay`,
`kit`, `onboarding`, `signin`, `signup`, `forgot`, `otp`, `newpass`, `pin`, `createprofile`,
`edit_profile`, `change_password`, `language`, `notifications`, `security`, `help`, `legal`,
`logout`, `fave`, `family`, `subscription`, `add_card`, `payment_success`, `pair_device`,
`kit_liked`, `kit_watch`, `kit_download`, `kit_top`, `kit_notfound`, `kit_season`, `kit_play`.

- Bottom bar ponsel tampil di: `home`, `library`, `settings`, `search`, `downloads`.
- `navigateTop(route)` = navigasi dengan `popUpTo(home){saveState}` + `launchSingleTop` + `restoreState`.

## 7. Android TV & Fokus Remote (KRITIS)

Baca aturan ini sebelum menyentuh UI apa pun — mudah menimbulkan bug fokus.

- `isTelevision()` (`TvSupport.kt`): heuristik `UI_MODE_TYPE_TELEVISION` || `FEATURE_LEANBACK` || tanpa touchscreen, plus override `FORCE_TV` saat debug.
- **Fokus awal tiap layar TV**: `AppNavGraph` memasang `Modifier.focusRequester(routeFocus).focusGroup()` pada `Scaffold` dan memanggil `routeFocus.requestFocus()` setiap ganti rute.
- **Helper `TvRequestFocusOnShow(target, enabled, keys...)`** (`TvSupport.kt`): meminta fokus **hanya di TV**, dengan delay kecil. Selalu pakai ini untuk overlay/dialog/sheet agar UI ponsel tidak berubah.
- **Aturan anti focus-trap**: JANGAN membuat container full-screen yang `focusable`/`clickable` (mis. `Box(fillMaxSize).clickable{}`) yang bisa menyerap fokus. Untuk memfokuskan item pertama, taruh `Modifier.focusRequester(fr)` pada **container non-focusable** → `requestFocus()` akan fokus ke anak focusable pertama (perilaku `visitChildren(Nodes.FocusTarget)`).
- **Menu overlay** (`OptionMenu` di `PlayerScreen.kt`) contoh benar: scrim & wadah memakai `pointerInput { detectTapGestures {...} }` (bukan `clickable`) supaya tidak focusable; item baris tetap `clickable`, `focusGroup()` menjaga D-pad di dalam menu.
- **Indikator fokus**: `LocalIndication` global = `TvFocusIndication` (border biru) via `Theme.kt`. Untuk elemen yang bisa difokus, gunakan `clickable` default atau `indication = LocalIndication.current` — **hindari `indication = null`** pada item yang perlu terlihat fokusnya.
- **Player remote**: `PlayerActivity.dispatchKeyEvent` menangani tombol media (play/pause/rewind/forward) **selalu**; D-pad ditangani langsung **hanya saat kontrol tersembunyi** (OK=play/pause, ←/→=±10s, ↑/↓=tampilkan kontrol). Saat kontrol tampil, D-pad diteruskan ke Compose.
  `PlayerRemoteController` (object di `PlayerScreen.kt`) menjembatani remote → state Compose. Fokus kontrol dipegang `controlFocus` (tombol play/pause); saat `OptionMenu`/sheet terbuka, fokus pindah ke item pertama (via `TvRequestFocusOnShow`).

## 8. Konvensi & Aturan Tetap

- **Tanpa WebView / hoster embed** untuk memutar video — gunakan Media3/ExoPlayer.
- Tema gelap; jaga desain yang ada. **Jangan menambah emoji** di teks UI.
- UI pakai Jetpack Compose (tidak ada layout XML, hanya `res/values` + drawable/ikon).
- Teks UI berbahasa Indonesia; sebagian layar (settings) memakai `LocalStrings`.
- **Jangan menambah komentar** berlebihan pada kode.
- Jangan hardcode secret baru di kode/commit. Base URL & client id lewat `BuildConfig`.
- Perubahan fokus/remote harus dijaga agar **tidak mengubah tampilan ponsel** (gunakan helper khusus TV).

## 9. Versi & Rilis (proses yang dipakai)

- Versi di `app/build.gradle.kts` (`versionCode` integer naik, `versionName` semver).
- Rilis = commit + tag `vX.Y.Z` + push ke GitHub (`Mikdad-Force/MikdadStream`) + buat GitHub Release + upload `MikdadStream-vX.Y.Z-release.apk`.
- Mirror lokal biasanya disalin: APK debug+release, source tree, dan `MikdadStream-source.zip`.

## 10. Catatan Status

- `README.md` lama bisa tidak akurat (menyebut hoster web/WebView) — **abaikan**, ikuti aturan di §8.
- Tidak ada test otomatis; verifikasi mengandalkan build sukses.
- Fitur Firebase/RTDB dan beberapa jalur hoster tidak dapat diuji di sandbox tanpa perangkat/kredensial.
