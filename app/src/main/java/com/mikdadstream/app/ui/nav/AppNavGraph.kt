package com.mikdadstream.app.ui.nav

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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

private object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val LIBRARY = "library"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
    const val DETAIL = "detail?modelId={modelId}&id={id}&source={source}&autoplay={autoplay}"
}

private data class BottomTab(val route: String, val label: String, val icon: ImageVector)

private val bottomTabs = listOf(
    BottomTab(Routes.HOME, "Beranda", Icons.Filled.Home),
    BottomTab(Routes.SEARCH, "Cari", Icons.Filled.Search),
    BottomTab(Routes.LIBRARY, "Koleksi", Icons.Filled.Bookmark),
    BottomTab(Routes.SETTINGS, "Profil", Icons.Filled.Person),
)

@Composable
private inline fun <reified T : ViewModel> sharedViewModel(crossinline factory: () -> T): T =
    viewModel(factory = viewModelFactory { initializer { factory() } })

@Composable
fun AppNavGraph() {
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

    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val showHeader = currentRoute == Routes.HOME || currentRoute == Routes.LIBRARY || currentRoute == Routes.SETTINGS
    val showBottomBar = showHeader || currentRoute == Routes.SEARCH

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
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 10.dp,
                ) {
                    bottomTabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentRoute == tab.route,
                            onClick = { navController.navigateTop(tab.route) },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                            ),
                        )
                    }
                }
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
                HomeScreen(
                    viewModel = vm,
                    contentPadding = padding,
                    onOpenItem = { item, modelId -> openDetail(modelId, item.safeId, item.source) },
                    onContinue = { entry -> openDetail(entry.modelId, entry.contentId, null, autoplay = true) },
                    onSeeHistory = { navController.navigateTop(Routes.HISTORY) },
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
                )
            }

            detailDestination(
                padding = padding,
                onBack = { navController.popBackStack() },
                onPlay = ::playRequest,
                snackbarHostState = snackbarHostState,
            )
        }
    }

    if (sheetVisible) {
        SourceSheet(
            models = models,
            activeId = activeSourceId,
            loading = modelsLoading,
            error = modelsError,
            onSelect = { mainViewModel.selectSource(it) },
            onRetry = { mainViewModel.loadModels() },
            onDismiss = { mainViewModel.closeSheet() },
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
