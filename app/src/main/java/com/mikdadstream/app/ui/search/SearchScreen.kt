package com.mikdadstream.app.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.ui.components.FullScreenState
import com.mikdadstream.app.ui.components.LoadingGrid
import com.mikdadstream.app.ui.components.SectionHeader
import com.mikdadstream.app.ui.components.VideoCard

@OptIn(ExperimentalLayoutApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    contentPadding: PaddingValues,
    onBack: () -> Unit,
    onOpenItem: (MediaItem) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val history by viewModel.history.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(gridState, state.hasMore, state.loading, state.loadingMore) {
        snapshotFlow {
            val info = gridState.layoutInfo
            (info.visibleItemsInfo.lastOrNull()?.index ?: 0) to info.totalItemsCount
        }.collect { (last, total) ->
            if (state.hasMore && !state.loading && !state.loadingMore && total > 0 && last >= total - 6) {
                viewModel.loadMore()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        // ---- Kolom pencarian ----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
            OutlinedTextField(
                value = state.query,
                onValueChange = { viewModel.onQueryChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                placeholder = {
                    Text("Cari judul, anime, drama…", color = MaterialTheme.colorScheme.onSurfaceVariant)
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                },
                trailingIcon = {
                    if (state.query.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onQueryChange("") }) {
                            Icon(Icons.Filled.Close, contentDescription = "Bersihkan")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(18.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.submit()
                    keyboard?.hide()
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
            )
            if (state.query.isNotEmpty()) {
                Text(
                    text = "Cari",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            viewModel.submit()
                            keyboard?.hide()
                        }
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                )
            }
        }

        when {
            state.query.isBlank() -> HistorySection(
                queries = history.map { it.query },
                onPick = { viewModel.searchFrom(it) },
                onClear = { viewModel.clearHistory() },
                onRemove = { viewModel.removeHistory(it) },
            )

            state.loading && state.results.isEmpty() -> Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
            ) {
                LoadingGrid()
            }

            state.error != null && state.results.isEmpty() -> FullScreenState(
                icon = Icons.Filled.SearchOff,
                title = "Pencarian gagal",
                subtitle = state.error.orEmpty(),
                actionLabel = "Coba Lagi",
                onAction = { viewModel.retry() },
            )

            state.results.isEmpty() && !state.loading -> FullScreenState(
                icon = Icons.Filled.Search,
                title = "Tidak ada hasil",
                subtitle = "Untuk \"${state.query}\". Coba kata kunci lain atau periksa ejaan.",
            )

            else -> LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(minSize = 118.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 4.dp,
                    bottom = contentPadding.calculateBottomPadding() + 24.dp,
                ),
                horizontalArrangement = Arrangement.spacedBy(13.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SectionHeader("Hasil untuk \"${state.query}\"")
                }
                items(state.results, key = { (it.safeId + it.source + it.hashCode()) }) { item ->
                    VideoCard(
                        item = item,
                        onClick = { onOpenItem(item) },
                        badge = item.source,
                        badgeColor = MaterialTheme.colorScheme.tertiary,
                    )
                }
                if (state.loadingMore) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            LinearProgressIndicator(
                                modifier = Modifier.width(140.dp),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HistorySection(
    queries: List<String>,
    onPick: (String) -> Unit,
    onClear: () -> Unit,
    onRemove: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        if (queries.isNotEmpty()) {
            SectionHeader("Riwayat Pencarian", actionLabel = "Hapus Semua", onAction = onClear)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                queries.forEach { query ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { onPick(query) }
                            .padding(start = 14.dp, end = 6.dp, top = 8.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Icon(
                            Icons.Filled.History,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(15.dp),
                        )
                        Text(
                            text = query,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .clickable { onRemove(query) }
                                .padding(4.dp),
                        ) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Hapus",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(14.dp),
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .background(
                        androidx.compose.ui.graphics.Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.18f),
                            )
                        )
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.Movie,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(34.dp),
                )
            }
            Text(
                text = "Mau nonton apa hari ini?",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Cari dari seluruh sumber: film, serial TV, anime, donghua, hingga TV langsung.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(Modifier.height(1.dp))
    }
}
