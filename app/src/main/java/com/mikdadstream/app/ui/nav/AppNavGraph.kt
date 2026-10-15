package com.mikdadstream.app.ui.nav

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.first
import androidx.navigation.navArgument
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.ui.components.AppHeader
import com.mikdadstream.app.ui.components.SourceSheet
import com.mikdadstream.app.ui.detail.DetailScreen
import com.mikdadstream.app.ui.detail.DetailViewModel
import com.mikdadstream.app.ui.home.HomeScreen
import com.mikdadstream.app.ui.home.HomeViewModel
import com.mikdadstream.app.ui.library.LibraryScreen
import com.mikdadstream.app.ui.library.LibraryViewModel
import com.mikdadstream.app.ui.player.PlayerActivity
import com.mikdadstream.app.ui.search.SearchScreen
import com.mikdadstream.app.ui.search.SearchViewModel
import com.mikdadstream.app.ui.settings.SettingsScreen
import com.mikdadstream.app.ui.settings.SettingsViewModel
import com.mikdadstream.app.ui.auth.SignInScreen
import com.mikdadstream.app.ui.auth.SignUpScreen
import com.mikdadstream.app.ui.auth.ForgotPasswordScreen
import com.mikdadstream.app.ui.auth.OtpVerificationScreen
import com.mikdadstream.app.ui.auth.CreateNewPasswordScreen
import com.mikdadstream.app.ui.auth.CreatePinScreen
import com.mikdadstream.app.ui.auth.CreateProfileScreen
import com.mikdadstream.app.ui.onboarding.OnboardingScreen
import com.mikdadstream.app.ui.account.EditProfileScreen
import com.mikdadstream.app.ui.account.ChangePasswordScreen
import com.mikdadstream.app.ui.account.LanguageScreen
import com.mikdadstream.app.ui.account.NotificationsScreen
import com.mikdadstream.app.ui.account.SecurityScreen
import com.mikdadstream.app.ui.account.HelpSupportScreen
import com.mikdadstream.app.ui.account.LegalPoliciesScreen
import com.mikdadstream.app.ui.account.LogoutScreen
import com.mikdadstream.app.ui.account.FaveCategoriesScreen
import com.mikdadstream.app.ui.account.FamilySharingScreen
import com.mikdadstream.app.ui.subscription.SubscriptionScreen
import com.mikdadstream.app.ui.subscription.AddCardScreen
import com.mikdadstream.app.ui.subscription.PaymentSuccessScreen
import com.mikdadstream.app.ui.kit.LikedMoviesScreen
import com.mikdadstream.app.ui.kit.WatchOnDeviceScreen
import com.mikdadstream.app.ui.kit.DownloadEmptyScreen
import com.mikdadstream.app.ui.kit.TopSearchesScreen
import com.mikdadstream.app.ui.kit.SearchNotFoundScreen
import com.mikdadstream.app.ui.kit.SeasonScreen
import com.mikdadstream.app.ui.kit.MoviePlayScreen
import com.mikdadstream.app.ui.kit.DesignKitScreen
import com.mikdadstream.app.ui.account.PairDeviceScreen
import com.mikdadstream.app.ui.tv.TvHomeScreen
import com.mikdadstream.app.ui.tv.TvTopBar

private object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val LIBRARY = "library"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
    const val DETAIL = "detail?modelId={modelId}&id={id}&source={source}&autoplay={autoplay}"
    const val KIT = "kit"
    const val ONBOARDING = "onboarding"
    const val SIGNIN = "signin"
    const val SIGNUP = "signup"
    const val FORGOT = "forgot"
    const val OTP = "otp"
    const val NEWPASS = "newpass"
    const val PIN = "pin"
    const val CREATEPROFILE = "createprofile"
    const val EDIT_PROFILE = "edit_profile"
    const val CHANGE_PASSWORD = "change_password"
    const val LANGUAGE = "language"
    const val NOTIFICATIONS = "notifications"
    const val SECURITY = "security"
    const val HELP = "help"
    const val LEGAL = "legal"
    const val LOGOUT = "logout"
    const val FAVE = "fave"
    const val FAMILY = "family"
    const val SUBSCRIPTION = "subscription"
    const val ADD_CARD = "add_card"
    const val PAYMENT_SUCCESS = "payment_success"
    const val KIT_LIKED = "kit_liked"
    const val KIT_WATCH = "kit_watch"
    const val KIT_DOWNLOAD = "kit_download"
    const val KIT_TOP = "kit_top"
    const val KIT_NOTFOUND = "kit_notfound"
    const val KIT_SEASON = "kit_season"
    const val KIT_PLAY = "kit_play"
    const val PAIR_DEVICE = "pair_device"
}

private data class BottomTab(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

private val bottomTabs = listOf(
    BottomTab(Routes.HOME, "Beranda", Icons.Filled.Home, Icons.Outlined.Home),
    BottomTab(Routes.SEARCH, "Cari", Icons.Filled.Search, Icons.Outlined.Search),
    BottomTab(Routes.LIBRARY, "Koleksi", Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder),
    BottomTab(Routes.SETTINGS, "Profil", Icons.Filled.Person, Icons.Outlined.Person),
)

@Composable
private inline fun <reified T : ViewModel> sharedViewModel(crossinline factory: () -> T): T =
    viewModel(factory = viewModelFactory { initializer { factory() } })

@Composable
fun AppNavGraph(isTv: Boolean = false) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val mainViewModel: MainViewModel = sharedViewModel { MainViewModel(AppGraph.repository, AppGraph.prefs) }
    val profile by mainViewModel.profile.collectAsState()
    val activeSource by mainViewModel.activeSource.collectAsState()
    val activeSourceId by mainViewModel.activeSourceId.collectAsState()
    val models by mainViewModel.models.collectAsState()
    val modelsLoading by mainViewModel.modelsLoading.collectAsState()
    val modelsError by mainViewModel.modelsError.collectAsState()
    val sheetVisible by mainViewModel.sheetVisible.collectAsState()
    val hiddenSources by AppGraph.prefs.hiddenSources.collectAsState()

    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val showHeader = false
    val showBottomBar = currentRoute == Routes.HOME || currentRoute == Routes.LIBRARY ||
        currentRoute == Routes.SETTINGS || currentRoute == Routes.SEARCH

    fun openDetail(modelId: String?, id: String, source: String?, autoplay: Boolean = false) {
        val route = "detail?modelId=${Uri.encode(modelId.orEmpty())}" +
            "&id=${Uri.encode(id)}" +
            "&source=${Uri.encode(source.orEmpty())}" +
            "&autoplay=$autoplay"
        navController.navigate(route)
    }

    fun playRequest(request: com.mikdadstream.app.ui.detail.PlayRequest) {
        context.startActivity(PlayerActivity.intent(context, request))
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (isTv && showBottomBar) {
                TvTopBar(
                    profile = profile,
                    activeSourceName = activeSource?.safeName ?: AppGraph.prefs.activeSource.collectAsState().value,
                    currentRoute = currentRoute,
                    onNavigate = { route -> navController.navigateTop(route) },
                    onSourceClick = { mainViewModel.openSheet() },
                    onProfileClick = { navController.navigateTop(Routes.SETTINGS) },
                )
            } else if (showHeader) {
                AppHeader(
                    profile = profile,
                    activeSource = activeSource,
                    onProfileClick = { navController.navigateTop(Routes.SETTINGS) },
                    onSearchClick = { navController.navigateTop(Routes.SEARCH) },
                    onSourceClick = { mainViewModel.openSheet() },
                    modifier = Modifier.statusBarsPadding(),
                )
            }
        },
        bottomBar = {
            if (!isTv && showBottomBar) {
                AppBottomBar(
                    tabs = bottomTabs,
                    currentRoute = currentRoute,
                    onSelect = { tab -> navController.navigateTop(tab.route) },
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable(Routes.HOME) {
                val vm: HomeViewModel = sharedViewModel { HomeViewModel(AppGraph.repository, AppGraph.prefs) }
                if (isTv) {
                    TvHomeScreen(
                        viewModel = vm,
                        contentPadding = padding,
                        profile = profile,
                        onOpenItem = { item, modelId -> openDetail(modelId, item.safeId, item.source) },
                        onContinue = { entry -> openDetail(entry.modelId, entry.contentId, null, autoplay = true) },
                    )
                } else {
                    HomeScreen(
                        viewModel = vm,
                        contentPadding = padding,
                        profile = profile,
                        onProfileClick = { navController.navigateTop(Routes.SETTINGS) },
                        onSearchClick = { navController.navigateTop(Routes.SEARCH) },
                        onNotificationClick = { navController.navigate(Routes.KIT) },
                        onOpenItem = { item, modelId -> openDetail(modelId, item.safeId, item.source) },
                        onContinue = { entry -> openDetail(entry.modelId, entry.contentId, null, autoplay = true) },
                        onSeeHistory = { navController.navigateTop(Routes.HISTORY) },
                    )
                }
            }

            composable(Routes.SEARCH) {
                val vm: SearchViewModel = sharedViewModel { SearchViewModel(AppGraph.repository) }
                SearchScreen(
                    viewModel = vm,
                    contentPadding = padding,
                    onBack = { navController.popBackStack() },
                    onOpenItem = { item -> openDetail(null, item.safeId, item.source) },
                )
            }

            composable(Routes.LIBRARY) {
                LibraryRoute(initialTab = 0, padding = padding, navController = navController)
            }

            composable(Routes.HISTORY) {
                LibraryRoute(initialTab = 1, padding = padding, navController = navController)
            }

            composable(Routes.SETTINGS) {
                val vm: SettingsViewModel = sharedViewModel {
                    SettingsViewModel(AppGraph.repository, AppGraph.prefs, AppGraph.apiClient)
                }
                SettingsScreen(
                    viewModel = vm,
                    contentPadding = padding,
                    activeSourceName = activeSource?.safeName
                        ?: AppGraph.prefs.activeSource.value,
                    onOpenSources = { mainViewModel.openSheet() },
                    onPairDevice = { navController.navigate(Routes.PAIR_DEVICE) },
                )
            }

            detailDestination(
                padding = padding,
                onBack = { navController.popBackStack() },
                onPlay = ::playRequest,
                snackbarHostState = snackbarHostState,
            )

            val back: () -> Unit = { navController.popBackStack() }
            composable(Routes.ONBOARDING) { OnboardingScreen(onFinish = { navController.navigateTop(Routes.HOME) }) }
            composable(Routes.SIGNIN) { SignInScreen(back, onSignIn = { _, _ -> navController.navigateTop(Routes.HOME) }, onSignUp = { navController.navigate(Routes.SIGNUP) }, onForgot = { navController.navigate(Routes.FORGOT) }) }
            composable(Routes.SIGNUP) { SignUpScreen(back, onSignUp = { navController.navigateTop(Routes.HOME) }, onSignIn = back) }
            composable(Routes.FORGOT) { ForgotPasswordScreen(back, onSend = { navController.navigate(Routes.OTP) }, onSignIn = back) }
            composable(Routes.OTP) { OtpVerificationScreen(back, onVerify = { navController.navigate(Routes.NEWPASS) }) }
            composable(Routes.NEWPASS) { CreateNewPasswordScreen(back, onDone = { navController.navigateTop(Routes.HOME) }) }
            composable(Routes.PIN) { CreatePinScreen(back, onDone = { navController.navigateTop(Routes.HOME) }) }
            composable(Routes.CREATEPROFILE) { CreateProfileScreen(back, onDone = { navController.navigateTop(Routes.HOME) }) }
            composable(Routes.EDIT_PROFILE) { EditProfileScreen(back) }
            composable(Routes.CHANGE_PASSWORD) { ChangePasswordScreen(back) }
            composable(Routes.LANGUAGE) { LanguageScreen(back) }
            composable(Routes.NOTIFICATIONS) { NotificationsScreen(back) }
            composable(Routes.SECURITY) { SecurityScreen(back) }
            composable(Routes.HELP) { HelpSupportScreen(back) }
            composable(Routes.LEGAL) { LegalPoliciesScreen(back) }
            composable(Routes.LOGOUT) { LogoutScreen(back, onConfirm = {
                AppGraph.prefs.clearTokens()
                AppGraph.prefs.setLoggedIn(false)
            }) }
            composable(Routes.FAVE) { FaveCategoriesScreen(back) }
            composable(Routes.FAMILY) { FamilySharingScreen(back) }
            composable(Routes.SUBSCRIPTION) { SubscriptionScreen(back, onSubscribe = { navController.navigate(Routes.ADD_CARD) }) }
            composable(Routes.ADD_CARD) { AddCardScreen(back, onPay = { navController.navigate(Routes.PAYMENT_SUCCESS) }) }
            composable(Routes.PAYMENT_SUCCESS) { PaymentSuccessScreen(onBack = { navController.navigateTop(Routes.SUBSCRIPTION) }, onHome = { navController.navigateTop(Routes.HOME) }) }
            composable(Routes.KIT_LIKED) { LikedMoviesScreen(back) { } }
            composable(Routes.KIT_WATCH) { WatchOnDeviceScreen(back) }
            composable(Routes.KIT_DOWNLOAD) { DownloadEmptyScreen(back) }
            composable(Routes.KIT_TOP) { TopSearchesScreen(back) { } }
            composable(Routes.KIT_NOTFOUND) { SearchNotFoundScreen(back) }
            composable(Routes.KIT_SEASON) { SeasonScreen(back) { } }
            composable(Routes.KIT_PLAY) { MoviePlayScreen(back, title = "Film Pilihan") }
            composable(Routes.PAIR_DEVICE) { PairDeviceScreen(back) }
            composable(Routes.KIT) {
                DesignKitScreen(
                    onBack = back,
                    onOpen = { name ->
                        val route = when (name) {
                            "Onboarding" -> Routes.ONBOARDING
                            "Masuk" -> Routes.SIGNIN
                            "Daftar" -> Routes.SIGNUP
                            "OTP" -> Routes.OTP
                            "Lupa Sandi" -> Routes.FORGOT
                            "Langganan" -> Routes.SUBSCRIPTION
                            "Tambah Kartu" -> Routes.ADD_CARD
                            "Pembayaran" -> Routes.PAYMENT_SUCCESS
                            "Film Disukai" -> Routes.KIT_LIKED
                            "Pencarian Populer" -> Routes.KIT_TOP
                            "Unduhan" -> Routes.KIT_DOWNLOAD
                            "Musim" -> Routes.KIT_SEASON
                            "Watch on Device" -> Routes.KIT_WATCH
                            "Ubah Profil" -> Routes.EDIT_PROFILE
                            "Ubah Sandi" -> Routes.CHANGE_PASSWORD
                            "Bahasa" -> Routes.LANGUAGE
                            "Notifikasi" -> Routes.NOTIFICATIONS
                            "Keamanan" -> Routes.SECURITY
                            "Bantuan" -> Routes.HELP
                            "Legal" -> Routes.LEGAL
                            "Keluar" -> Routes.LOGOUT
                            "Kategori Favorit" -> Routes.FAVE
                            "Berbagi Keluarga" -> Routes.FAMILY
                            "Putar Film" -> Routes.KIT_PLAY
                            else -> Routes.KIT
                        }
                        navController.navigate(route)
                    },
                )
            }
        }
    }

    if (sheetVisible) {
        val visibleModels = models.filter { model ->
            !hiddenSources.contains(model.safeId.lowercase()) || model.safeId.equals(activeSourceId, true)
        }
        SourceSheet(
            models = visibleModels,
            activeId = activeSourceId,
            loading = modelsLoading,
            error = modelsError,
            onSelect = { mainViewModel.selectSource(it) },
            onRetry = { mainViewModel.loadModels() },
            onDismiss = { mainViewModel.closeSheet() },
            onHide = { model ->
                AppGraph.prefs.hideSource(model.safeId)
                if (model.safeId.equals(activeSourceId, true)) {
                    visibleModels.firstOrNull { it.safeId != model.safeId }?.let {
                        mainViewModel.selectSource(it)
                    }
                }
            },
        )
    }
}

@Composable
private fun AppBottomBar(
    tabs: List<BottomTab>,
    currentRoute: String?,
    onSelect: (BottomTab) -> Unit,
) {
    val primary = MaterialTheme.colorScheme.primary
    val inactive = MaterialTheme.colorScheme.onSurfaceVariant
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF1B1D26)),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEach { tab ->
                val selected = currentRoute == tab.route
                val scale by animateFloatAsState(
                    targetValue = if (selected) 1.04f else 1f,
                    animationSpec = tween(200),
                    label = "navScale",
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .scale(scale)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { onSelect(tab) }
                        .padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = tab.label,
                        tint = if (selected) primary else inactive,
                        modifier = Modifier.size(23.dp),
                    )
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selected) primary else inactive,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@Composable
private fun LibraryRoute(
    initialTab: Int,
    padding: androidx.compose.foundation.layout.PaddingValues,
    navController: NavHostController,
) {
    val vm: LibraryViewModel = sharedViewModel { LibraryViewModel(AppGraph.repository) }
    LibraryScreen(
        viewModel = vm,
        initialTab = initialTab,
        contentPadding = padding,
        onOpenItem = { modelId, contentId -> openDetailExternally(navController, modelId, contentId) },
        onOpenHistory = { entry -> openDetailExternally(navController, entry.modelId, entry.contentId, autoplay = true) },
    )
}

private fun openDetailExternally(
    navController: NavHostController,
    modelId: String,
    contentId: String,
    autoplay: Boolean = false,
) {
    val route = "detail?modelId=${Uri.encode(modelId)}" +
        "&id=${Uri.encode(contentId)}" +
        "&source=${Uri.encode("")}" +
        "&autoplay=$autoplay"
    navController.navigate(route)
}

private fun NavHostController.navigateTop(route: String) {
    navigate(route) {
        popUpTo(Routes.HOME) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

private fun NavGraphBuilder.detailDestination(
    padding: androidx.compose.foundation.layout.PaddingValues,
    onBack: () -> Unit,
    onPlay: (com.mikdadstream.app.ui.detail.PlayRequest) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    composable(
        route = Routes.DETAIL,
        arguments = listOf(
            navArgument("modelId") { type = NavType.StringType; defaultValue = "" },
            navArgument("id") { type = NavType.StringType },
            navArgument("source") { type = NavType.StringType; defaultValue = "" },
            navArgument("autoplay") { type = NavType.BoolType; defaultValue = false },
        ),
    ) { entry ->
        val modelId = entry.arguments?.getString("modelId").orEmpty().ifBlank { null }
        val id = entry.arguments?.getString("id").orEmpty()
        val source = entry.arguments?.getString("source").orEmpty().ifBlank { null }
        val autoplay = entry.arguments?.getBoolean("autoplay") ?: false

        val vm: DetailViewModel = sharedViewModel { DetailViewModel(AppGraph.repository) }
        androidx.compose.runtime.LaunchedEffect(id, modelId) {
            vm.load(id = id, modelId = modelId, sourceName = source)
        }
        androidx.compose.runtime.LaunchedEffect(autoplay, id) {
            if (autoplay) {
                val ready = vm.state.first { !it.loading }
                if (ready.error == null && ready.detail != null) {
                    val request = vm.resumeHistory() ?: vm.playRequest()
                    if (request != null) onPlay(request)
                    else snackbarHostState.showSnackbar("Belum ada episode untuk diputar.")
                }
            }
        }
        DetailScreen(
            viewModel = vm,
            contentPadding = padding,
            onBack = onBack,
            onPlay = onPlay,
        )
    }
}
