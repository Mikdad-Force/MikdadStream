# MovieBox Source (decompiled)

Sumber diperoleh dari APK `Genga World` (`com.gengaworld.app`, version 2.5), khususnya `classes3.dex`, package `com.example.data.network`.

## Lokasi sumber
- Smali decompiled: `smali/com/example/data/network/`
- Dokumentasi API (HTML playground): `docs/MovieBox_API_Playground.html`

## Endpoint resmi yang dipakai
- `https://api.inmoviebox.com/`
- `https://api6.aoneroom.com/`
- WebSocket chat: `wss://chat-api.aoneroom.com/chat-api/v1/room/sync`

## Cara kerja
1. App menjalankan `LocalMovieBoxServer` (HTTP server lokal) yang menerima request dari UI (Compose) pada endpoint lokal seperti `/trending`, `/discovery`, `/search`, `/detail/{id}`, `/episodes/{id}`, `/stream/{id}`, `/user-info`, auth, dll.
2. `routeRequest(...)` memetakan path lokal ke request resmi.
3. `makeOfficialRequest(...)` membangun HTTP request ke API resmi dengan header:
   - `User-Agent: MovieBoxPro/16.2.1 (Android 12; Pixel 6)`
   - `Referer: https://api6.aoneroom.com/`
   - `x-tr-signature`: HMAC-MD5 menggunakan secret `76iRl07s0xSN9jqmEWAt79EBJZulIQIsV64FZr2O`
4. `bootstrapGuestToken()` mengambil guest token via `/wefeed-mobile-bff/tab-operating?host=api.inmoviebox.com&page=1&pageSize=24&tabId=1`.
5. `getDeviceId()` menghasilkan/mengambil device id dari preferensi `moviebox_prefs`.
6. Respons disimpan di cache in-memory (`responseCache`) dan preferensi disk (`diskCachePrefs` / `moviebox_prefs`) untuk reuse.
7. `DocsHtml.getHtml(port)` menyediakan halaman dokumentasi/playground API lokal.

## Secret penting (dari decompiled)
- Gateway Sign Secret: `76iRl07s0xSN9jqmEWAt79EBJZulIQIsV64FZr2O`
- WeFeed App Secret: `df70dbad6215444ca9e87ee1078cc681`
- Easter Egg Bypass Code: `or666`

## Catatan
Ini adalah hasil decompile smali, bukan source Kotlin asli. Nama file dan struktur package berasal dari metadata debug Kotlin.
