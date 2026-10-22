package com.mikdadstream.app

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.mikdadstream.app.data.firebase.FirebaseConnectivity
import com.mikdadstream.app.ui.auth.CreateNewPasswordScreen
import com.mikdadstream.app.ui.auth.ForgotPasswordScreen
import com.mikdadstream.app.ui.auth.OtpVerificationScreen
import com.mikdadstream.app.ui.auth.SignInScreen
import com.mikdadstream.app.ui.auth.SignUpScreen
import com.mikdadstream.app.ui.nav.AppNavGraph
import com.mikdadstream.app.ui.onboarding.OnboardingScreen
import com.mikdadstream.app.ui.theme.MikdadStreamTheme
import com.mikdadstream.app.ui.theme.brandBrush
import com.mikdadstream.app.ui.tv.TvPairingScreen
import com.mikdadstream.app.ui.tv.isTelevision
import com.mikdadstream.app.ui.update.UpdateScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val themeMode by AppGraph.prefs.themeMode.collectAsState()
            val accentIndex by AppGraph.prefs.accent.collectAsState()
            val accent = com.mikdadstream.app.ui.theme.AccentChoice.from(accentIndex)
            val language by AppGraph.prefs.language.collectAsState()
            val darkTheme = when (themeMode) {
                1 -> true
                2 -> false
                else -> isSystemInDarkTheme()
            }
            androidx.compose.runtime.SideEffect {
                val controller = WindowCompat.getInsetsController(window, window.decorView)
                controller.isAppearanceLightStatusBars = !darkTheme
                controller.isAppearanceLightNavigationBars = !darkTheme
            }
            MikdadStreamTheme(darkTheme = darkTheme, accent = accent) {
                androidx.compose.runtime.CompositionLocalProvider(
                    com.mikdadstream.app.ui.i18n.LocalStrings provides com.mikdadstream.app.ui.i18n.appStringsFor(language),
                ) {
                    AppRoot(isTv = applicationContext.isTelevision())
                }
            }
        }
    }
}

@Composable
private fun AppRoot(isTv: Boolean) {        val loggedIn by AppGraph.prefs.loggedIn.collectAsState()
    var booting by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val updater = AppGraph.updater
    val downloading by updater.downloading.collectAsState()
    val progress by updater.progress.collectAsState()
    var updateInfo by remember { mutableStateOf<com.mikdadstream.app.data.update.UpdateInfo?>(null) }
    var updateFailed by remember { mutableStateOf(false) }
    var installing by remember { mutableStateOf(false) }
    var permissionNeeded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (AppGraph.prefs.loggedIn.value) {
            val ok = runCatching { AppGraph.apiClient.auth.ensureSession() }.getOrDefault(false)
            if (!ok && AppGraph.apiClient.auth.sessionRejected) {
                AppGraph.prefs.clearTokens()
                AppGraph.prefs.setLoggedIn(false)
            }
            if (!isTv && FirebaseConnectivity.isReachable()) {
                runCatching { AppGraph.users.syncNow() }
                if (AppGraph.users.available && AppGraph.users.currentUid == null) {
                    AppGraph.prefs.setLoggedIn(false)
                }
            }
        }
        booting = false
    }

    if (isTv) {
        LaunchedEffect(loggedIn) {
            while (loggedIn) {
                delay(30_000L)
                runCatching { AppGraph.apiClient.api.accountStatus() }.onFailure { err ->
                    if (err is HttpException && err.code() == 401) {
                        AppGraph.prefs.clearTokens()
                        AppGraph.prefs.setLoggedIn(false)
                    }
                }
            }
        }
    }

    // Cek update otomatis: saat aplikasi dibuka, saat kembali ke depan
    // (ON_RESUME), dan berkala tiap 10 menit selama aplikasi berjalan.
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    androidx.compose.runtime.DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME && updateInfo == null) {
                scope.launch {
                    runCatching { updater.check() }.getOrNull()?.let { updateInfo = it }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    LaunchedEffect(Unit) {
        while (true) {
            if (updateInfo == null) {
                runCatching { updater.check() }.getOrNull()?.let { updateInfo = it }
            }
            delay(10 * 60_000L)
        }
    }

    val info = updateInfo
    if (info != null) {
        UpdateScreen(
            info = info,
            downloading = downloading,
            progress = progress,
            permissionNeeded = permissionNeeded,
            installing = installing,
            failed = updateFailed,
            onUpdate = {
                scope.launch {
                    updateFailed = false
                    val file = updater.download(info)
                    if (file == null) {
                        updateFailed = true
                        return@launch
                    }
                    if (!updater.canInstall()) {
                        permissionNeeded = true
                        return@launch
                    }
                    installing = true
                    runCatching { updater.install(file) }.onFailure { updateFailed = true }
                    installing = false
                }
            },
            onOpenPermission = { updater.openInstallPermission() },
        )
        return
    }

    when {
        booting -> SplashScreen()
        !loggedIn -> AuthFlow(isTv = isTv)
        else -> AppNavGraph(isTv = isTv)
    }
}

private enum class AuthStep { Onboarding, SignIn, SignUp, Forgot, Otp, NewPass, TvPairing }

@Composable
private fun AuthFlow(isTv: Boolean) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val prefs = AppGraph.prefs
    var step by remember {
        mutableStateOf(if (prefs.loggedIn.value) AuthStep.SignIn else AuthStep.Onboarding)
    }
    var authBusy by remember { mutableStateOf(false) }
    var authMessage by remember { mutableStateOf<String?>(null) }

    fun finishLogin() {
        prefs.setLoggedIn(true)
    }

    if (isTv) {
        when (step) {
            AuthStep.TvPairing -> TvPairingScreen(onPaired = { finishLogin() })
            else -> OnboardingScreen(onFinish = { step = AuthStep.TvPairing })
        }
        return
    }

    fun deviceLogin(email: String = "") {
        scope.launch {
            val ok = runCatching {
                AppGraph.apiClient.auth.loginWithEmailAsync(email)
            }.getOrDefault(false)
            if (ok || email.isBlank()) finishLogin()
        }
    }

    val googleLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val claims = parseGoogleIdToken(account.idToken)
            val email = claims["email"] ?: account.email ?: ""
            val name = claims["name"] ?: account.displayName
            val avatar = claims["picture"] ?: account.photoUrl?.toString()
            val sub = claims["sub"]
            scope.launch {
                val firebaseUser = AppGraph.users.signInWithGoogle(account.idToken.orEmpty())
                if (firebaseUser == null) {
                    AppGraph.users.lastError.value?.let {
                        authMessage = "Gagal masuk: $it"
                        android.widget.Toast.makeText(context, "Firebase: $it", android.widget.Toast.LENGTH_LONG).show()
                    }
                }
                val ok = runCatching {
                    AppGraph.apiClient.auth.loginWithGoogleAsync(email, name, avatar, sub, account.idToken)
                }.getOrDefault(false)
                if (ok) finishLogin() else deviceLogin(email)
                authBusy = false
            }
        } catch (e: ApiException) {
            authBusy = false
            val detail = when (e.statusCode) {
                7 -> "Kesalahan jaringan"
                10 -> "Konfigurasi salah (client ID/SHA-1 tidak terdaftar)"
                12500, 12501 -> "Gangguan server Google"
                8 -> "Kesalahan internal"
                else -> "Kode ${e.statusCode}"
            }
            authMessage = "Google Sign-In gagal: $detail"
            android.widget.Toast.makeText(context, authMessage, android.widget.Toast.LENGTH_LONG).show()
            deviceLogin()
        }
    }

    fun launchGoogleSignIn() {
        if (authBusy) return
        scope.launch {
            authBusy = true
            authMessage = null
            if (!FirebaseConnectivity.isReachable()) {
                authMessage = "Jaringan ini sepertinya memblokir server Google (Firebase). Matikan WiFi dan pakai data seluler, atau nyalakan VPN (mis. 1.1.1.1/WARP), lalu coba lagi."
                authBusy = false
                return@launch
            }
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .requestEmail()
                .build()
            val client = GoogleSignIn.getClient(context, options)
            googleLauncher.launch(client.signInIntent)
        }
    }

    when (step) {
        AuthStep.Onboarding -> OnboardingScreen(onFinish = { step = AuthStep.SignIn })
        AuthStep.SignIn -> SignInScreen(
            onBack = { step = AuthStep.Onboarding },
            onGoogleSignIn = { launchGoogleSignIn() },
            loading = authBusy,
            message = authMessage,
        )
        AuthStep.SignUp -> SignUpScreen(
            onBack = { step = AuthStep.SignIn },
            onSignUp = { deviceLogin() },
            onSignIn = { step = AuthStep.SignIn },
        )
        AuthStep.Forgot -> ForgotPasswordScreen(
            onBack = { step = AuthStep.SignIn },
            onSend = { step = AuthStep.Otp },
            onSignIn = { step = AuthStep.SignIn },
        )
        AuthStep.Otp -> OtpVerificationScreen(
            onBack = { step = AuthStep.Forgot },
            onVerify = { step = AuthStep.NewPass },
        )
        AuthStep.NewPass -> CreateNewPasswordScreen(
            onBack = { step = AuthStep.Otp },
            onDone = { deviceLogin() },
        )
        AuthStep.TvPairing -> Unit
    }
}

private fun parseGoogleIdToken(idToken: String?): Map<String, String> {
    if (idToken.isNullOrBlank()) return emptyMap()
    return runCatching {
        val payload = idToken.split(".").getOrNull(1) ?: return emptyMap()
        val decoded = String(Base64.decode(payload, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING))
        val json = JSONObject(decoded)
        val map = mutableMapOf<String, String>()
        listOf("email", "name", "picture", "sub").forEach { key ->
            if (json.has(key) && !json.isNull(key)) map[key] = json.getString(key)
        }
        map
    }.getOrDefault(emptyMap())
}

@Composable
private fun SplashScreen() {
    val transition = rememberInfiniteTransition(label = "splash")
    val pulse by transition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "pulse",
    )

    val accent = com.mikdadstream.app.ui.theme.LocalAccent.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(listOf(accent.copy(alpha = 0.22f), Color(0xFF0B0B12)))),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .scale(pulse)
                    .clip(CircleShape)
                    .background(brandBrush()),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_ghost),
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                )
            }
            Text(
                text = "AkashaStream",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Menyiapkan sumber video…",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.65f),
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}
