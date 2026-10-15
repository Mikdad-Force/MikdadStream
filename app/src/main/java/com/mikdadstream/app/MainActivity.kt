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
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
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
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val themeMode by AppGraph.prefs.themeMode.collectAsState()
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
            MikdadStreamTheme(darkTheme = darkTheme) {
                AppRoot(isTv = applicationContext.isTelevision())
            }
        }
    }
}

@Composable
private fun AppRoot(isTv: Boolean) {
    val loggedIn by AppGraph.prefs.loggedIn.collectAsState()
    var booting by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (AppGraph.prefs.loggedIn.value) {
            runCatching { AppGraph.apiClient.auth.ensureSession() }
        }
        booting = false
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
                val ok = runCatching {
                    AppGraph.apiClient.auth.loginWithGoogleAsync(email, name, avatar, sub)
                }.getOrDefault(false)
                if (ok) finishLogin() else deviceLogin(email)
            }
        } catch (e: ApiException) {
            deviceLogin()
        }
    }

    fun launchGoogleSignIn() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(context, options)
        googleLauncher.launch(client.signInIntent)
    }

    when (step) {
        AuthStep.Onboarding -> OnboardingScreen(onFinish = { step = AuthStep.SignIn })
        AuthStep.SignIn -> SignInScreen(
            onBack = { step = AuthStep.Onboarding },
            onSignIn = { email, _ -> deviceLogin(email) },
            onSignUp = { step = AuthStep.SignUp },
            onForgot = { step = AuthStep.Forgot },
            onGoogleSignIn = { launchGoogleSignIn() },
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(listOf(Color(0xFF141B33), Color(0xFF070A13)))),
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
                Box(
                    modifier = Modifier
                        .size(86.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0B0F1C)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(44.dp),
                    )
                }
            }
            Text(
                text = "Mikdad Stream",
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
