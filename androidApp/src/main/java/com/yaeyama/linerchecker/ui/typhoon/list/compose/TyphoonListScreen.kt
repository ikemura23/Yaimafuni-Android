package com.yaeyama.linerchecker.ui.typhoon.list.compose

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.typhoon.Typhoon
import com.yaeyama.linerchecker.ui.common.PreviewBox
import com.yaeyama.linerchecker.ui.common.YaimafuniScaffold
import com.yaeyama.linerchecker.ui.typhoon.detail.TyphoonDetailActivity
import com.yaeyama.linerchecker.ui.typhoon.detail.toTyphoonDetailUiModel
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonListTopAppBar
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonListViewModel
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonUiState

/**
 * TyphoonListScreen for MainScreen integration
 * 既存TyphoonListViewModelを活用し、MainActivity統合用に実装
 */
@Composable
fun TyphoonListScreen(
    modifier: Modifier = Modifier,
    viewModel: TyphoonListViewModel? = null,
) {
    val context = LocalContext.current

    viewModel?.let { vm ->
        val uiState by vm.uiState.collectAsStateWithLifecycle()

        YaimafuniScaffold(
            modifier = modifier,
            topBar = {
                TyphoonListTopAppBar()
            },
            // MainScreenのMainScaffold(bottomBar)が下端のinsetを既に確保しているため、二重に確保しない
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            content = { paddingValues ->
                when (val currentState = uiState) {
                    is TyphoonUiState.Loading -> {
                        LoadingContent(modifier = Modifier.padding(paddingValues))
                    }

                    is TyphoonUiState.Error -> {
                        ErrorContent(modifier = Modifier.padding(paddingValues))
                    }

                    is TyphoonUiState.Data -> {
                        if (currentState.typhoons.isEmpty()) {
                            EmptyContent(modifier = Modifier.padding(paddingValues))
                        } else {
                            TyphoonListContent(
                                modifier = Modifier.padding(paddingValues),
                                typhoons = currentState.typhoons,
                                onItemClick = { typhoon ->
                                    // TyphoonDetailActivityへの遷移
                                    val intent = Intent(context, TyphoonDetailActivity::class.java).apply {
                                        putExtra(TyphoonDetailActivity.EXTRA_TYPHOON, typhoon.toTyphoonDetailUiModel())
                                    }
                                    context.startActivity(intent)
                                },
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.typhoon_list_fetch_failed),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun EmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.typhoon_list_empty),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun TyphoonListContent(
    modifier: Modifier = Modifier,
    typhoons: List<Typhoon>,
    onItemClick: (Typhoon) -> Unit,
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
    ) {
        items(typhoons) { typhoon ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp),
                onClick = {
                    onItemClick(typhoon)
                },
            ) {
                Text(
                    text = typhoon.name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TyphoonListContentPreview() {
    PreviewBox {
        TyphoonListContent(
            typhoons = listOf(
                Typhoon(name = "Typhoon 1"),
                Typhoon(name = "Typhoon 2"),
                Typhoon(name = "Typhoon 3"),
            ),
            onItemClick = {},
        )
    }
}

@Preview
@Composable
private fun LoadingContentPreview() {
    PreviewBox {
        LoadingContent()
    }
}

@Preview
@Composable
private fun ErrorContentPreview() {
    PreviewBox {
        ErrorContent()
    }
}

@Preview
@Composable
private fun EmptyContentPreview() {
    PreviewBox {
        EmptyContent()
    }
}
