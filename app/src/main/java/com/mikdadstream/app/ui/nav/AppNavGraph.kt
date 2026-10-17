package com.mikdadstream.app.ui.nav

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
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
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.mikdadstream.app.data.firebase.AccountType
import com.mikdadstream.app.ui.components.AppHeader
import com.mikdadstream.app.ui.components.FloatingBottomNavBar
import com.mikdadstream.app.ui.components.FloatingNavItem
import com.mikdadstream.app.ui.components.SourceSheet
import com.mikdadstream.app.ui.recommend.RecommendScreen
import com.mikdadstream.app.ui.detail.DetailScreen
import com.mikdadstream.app.ui.detail.DetailViewModel
import com.mikdadstream.app.ui.detail.PlayRequest
import com.mikdadstream.app.data.remote.EpisodeData
import com.mikdadstream.app.ui.downloads.DownloadsScreen
import com.mikdadstream.app.ui.downloads.DownloadsViewModel
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

private object Routes {
    const val HOME = "home"
    const val RECOMMEND = "recommend"
    const val SEARCH = "search"
    const val LIBRARY = "library"
    const val HISTORY = "history"
    const val DOWNLOADS = "downloads"
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

private fun floatingNavItems(s: com.mikdadstream.app.ui.i18n.AppStrings) = listOf(
    // Persis mockup folder Theme: Home / Rekomendasi / Download / History
    FloatingNavItem(Routes.HOME, Icons.Rounded.Home, s.navHome, selectedIcon = Icons.Rounded.Home),
    FloatingNavItem(Routes.RECOMMEND, Icons.Rounded.AutoAwesome, s.navRecommend, selectedIcon = Icons.Rounded.AutoAwesome),
    FloatingNavItem(Routes.DOWNLOADS, Icons.Rounded.Download, s.navDownloads, selectedIcon = Icons.Rounded.Download),
    FloatingNavItem(Routes.HISTORY, Icons.Rounded.History, s.navLibrary, selectedIcon = Icons.Rounded.History),
)

private fun floatingNavIndex(route: String?): Int = when (route) {
    Routes.RECOMMEND -> 1
    Routes.DOWNLOADS -> 2
    Routes.HISTORY, Routes.LIBRARY -> 3
    else -> 0
}

@Composable
private inline fun <reified T : ViewModel> sharedViewModel(crossinline factory: () -> T): T =
    viewModel(factory = viewModelFactory { initializer { factory() } })

@Composable
fun AppNavGraph(isTv: Boolean = false) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val premiumScope = rememberCoroutineScope()

    val mainViewModel: MainViewModel = sharedViewModel { MainViewModel(AppGraph.repository, AppGraph.prefs) }
    val profile by mainViewModel.profile.collectAsState()
    val activeSource by mainViewModel.activeSource.collectAsState()
    val activeSourceId by mainViewModel.activeSourceId.collectAsState()
    val models by mainViewModel.models.collectAsState()
    val modelsLoading by mainViewModel.modelsLoading.collectAsState()
    val modelsError by mainViewModel.modelsError.collectAsState()
    val sheetVisible by mainViewModel.sheetVisible.collectAsState()
    val hiddenSources by AppGraph.prefs.hiddenSources.collectAsState()
    val firebaseAccount by AppGraph.users.account.collectAsState()
    val premiumUnlocked by AppGraph.prefs.premiumUnlocked.collectAsState()
    val isPremium = firebaseAccount?.premium == true ||
        premiumUnlocked ||
        firebaseAccount?.accountType == AccountType.DEVELOPERS
    val accountBadge = when {
        firebaseAccount?.accountType == AccountType.DEVELOPERS -> "Developers"
        isPremium -> "Premium"
        else -> "Normal"
    }

    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val showHeader = false
    val homeContentFocus = remember { FocusRequester() }
    val headerFocus = remember { FocusRequester() }
    val routeFocus = remember { FocusRequester() }
    val railFocus = remember { FocusRequester() }
    val showBottomBar = currentRoute == Routes.HOME || currentRoute == Routes.LIBRARY || currentRoute == Routes.RECOMMEND ||
        currentRoute == Routes.SETTINGS || currentRoute == Routes.SEARCH || currentRoute == Routes.DOWNLOADS ||
        currentRoute == Routes.HISTORY

    LaunchedEffect(currentRoute, isTv) {
        if (!isTv) return@LaunchedEffect
        runCatching { routeFocus.requestFocus() }
        delay(80)
        runCatching { routeFocus.requestFocus() }
    }

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
        modifier = if (isTv) Modifier.focusRequester(routeFocus).focusGroup() else Modifier,
        topBar = {
            if (showHeader) {
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
                val navItems = floatingNavItems(com.mikdadstream.app.ui.i18n.LocalStrings.current)
                FloatingBottomNavBar(
                    items = navItems,
                    selectedIndex = floatingNavIndex(currentRoute),
                    onSelect = { index -> navController.navigateTop(navItems[index].id) },
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
                        initialFocus = homeContentFocus,
                        headerFocus = headerFocus,
                        currentRoute = currentRoute,
                        activeSourceName = activeSource?.safeName,
                        onSourceClick = { mainViewModel.openSheet() },
                        onOpenItem = { item, modelId -> openDetail(modelId, item.safeId, item.source) },
                        onContinue = { entry -> openDetail(entry.modelId, entry.contentId, null, autoplay = true) },
                        onNavigate = { route -> navController.navigateTop(route) },
                    )
                } else {
                    HomeScreen(
                        viewModel = vm,
                        contentPadding = padding,
                        profile = profile,
                        accountBadge = accountBadge,
                        isPremium = isPremium,
                        activeSourceName = activeSource?.safeName,
                        onSourceClick = { mainViewModel.openSheet() },
                        onProfileClick = { navController.navigateTop(Routes.SETTINGS) },
                        onSearchClick = { navController.navigateTop(Routes.SEARCH) },
                        onNotificationClick = { navController.navigate(Routes.NOTIFICATIONS) },
                        onOpenItem = { item, modelId -> openDetail(modelId, item.safeId, item.source) },
                        onContinue = { entry -> openDetail(entry.modelId, entry.contentId, null, autoplay = true) },
                        onSeeHistory = { navController.navigateTop(Routes.HISTORY) },
                    )
                }
            }

            composable(Routes.RECOMMEND) {
                val vm: HomeViewModel = sharedViewModel { HomeViewModel(AppGraph.repository, AppGraph.prefs) }
                val recState by vm.state.collectAsState()
                val recHistory by vm.continueWatching.collectAsState()
                RecommendScreen(
                    items = recState.items,
                    history = recHistory,
                    activeSourceName = activeSource?.safeName,
                    onSourceClick = { mainViewModel.openSheet() },
                    onOpenItem = { item -> openDetail(null, item.safeId, item.source) },
                )
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

            composable(Routes.DOWNLOADS) {
                val vm: DownloadsViewModel = sharedViewModel { DownloadsViewModel(AppGraph.downloads) }
                DownloadsScreen(
                    viewModel = vm,
                    contentPadding = padding,
                    isPremium = isPremium,
                    onBack = { navController.navigateTop(Routes.HOME) },
                    onOpenPremium = { navController.navigate(Routes.SUBSCRIPTION) },
                    onPlay = { dl ->
                        val path = dl.filePath
                        if (!path.isNullOrBlank()) {
                            val url = Uri.fromFile(java.io.File(path)).toString()
                            val episode = EpisodeData(
                                episodeId = dl.episodeId.orEmpty(),
                                episodeNumber = dl.episodeNumber ?: 1,
                                thumbnailUrl = dl.cover,
                            )
                            context.startActivity(
                                PlayerActivity.intent(
                                    context,
                                    PlayRequest(
                                        modelId = dl.modelId,
                                        contentId = dl.contentId,
                                        episodeId = dl.episodeId.orEmpty(),
                                        episodeNumber = dl.episodeNumber ?: 1,
                                        title = dl.title,
                                        cover = dl.cover,
                                        episodes = listOf(episode),
                                        localUrl = url,
                                    ),
                                )
                            )
                        }
                    },
                )
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
                    isPremium = isPremium,
                    onOpenSources = { mainViewModel.openSheet() },
                    onOpenLanguage = { navController.navigate(Routes.LANGUAGE) },
                    onOpenPremium = { navController.navigate(Routes.SUBSCRIPTION) },
                    onPairDevice = { navController.navigate(Routes.PAIR_DEVICE) },
                    onOpenDesignKit = { navController.navigate(Routes.KIT) },
                )
            }

            detailDestination(
                padding = padding,
                onBack = { navController.popBackStack() },
                onPlay = ::playRequest,
                onOpenPremium = { navController.navigate(Routes.SUBSCRIPTION) },
                snackbarHostState = snackbarHostState,
            )

            val back: () -> Unit = { navController.popBackStack() }
            composable(Routes.ONBOARDING) { OnboardingScreen(onFinish = { navController.navigateTop(Routes.HOME) }) }
            composable(Routes.SIGNIN) { SignInScreen(onBack = back, onGoogleSignIn = { navController.navigateTop(Routes.HOME) }) }
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
                AppGraph.prefs.setPremiumUnlocked(false)
                premiumScope.launch { AppGraph.users.signOut() }
            }) }
            composable(Routes.FAVE) { FaveCategoriesScreen(back) }
            composable(Routes.FAMILY) { FamilySharingScreen(back) }
            composable(Routes.SUBSCRIPTION) { SubscriptionScreen(back, onSubscribe = { navController.navigate(Routes.ADD_CARD) }, alreadyPremium = isPremium) }
            composable(Routes.ADD_CARD) { AddCardScreen(back, onPay = {
                AppGraph.prefs.setPremiumUnlocked(true)
                premiumScope.launch { AppGraph.users.activatePremium() }
                navController.navigate(Routes.PAYMENT_SUCCESS)
            }) }
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
    onOpenPremium: () -> Unit,
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
                    if (request != null) vm.preparePlay(request) { ready -> onPlay(ready) }
                    else snackbarHostState.showSnackbar("Belum ada episode untuk diputar.")
                }
            }
        }
        DetailScreen(
            viewModel = vm,
            contentPadding = padding,
            onBack = onBack,
            onOpenPremium = onOpenPremium,
            onPlay = onPlay,
        )
    }
}
