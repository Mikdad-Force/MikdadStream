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

private object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val LIBRARY = "library"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
    const val DETAIL = "detail?modelId={modelId}&id={id}&source={source}&autoplay={autoplay}"
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
    val hiddenSources by AppGraph.prefs.hiddenSources.collectAsState()

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
    val pillBrush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.22f),
        )
    )
    val selectedBrush = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(horizontal = 14.dp, vertical = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(pillBrush)
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEach { tab ->
                val selected = currentRoute == tab.route
                val scale by animateFloatAsState(
                    targetValue = if (selected) 1.06f else 1f,
                    animationSpec = tween(220),
                    label = "navScale",
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .scale(scale)
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (selected) selectedBrush else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { onSelect(tab) }
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = tab.label,
                        tint = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(22.dp),
                    )
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
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
