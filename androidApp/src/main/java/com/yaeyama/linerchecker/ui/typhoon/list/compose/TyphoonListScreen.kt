package com.yaeyama.linerchecker.ui.typhoon.list.compose

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.common.PreviewBox
import com.yaeyama.linerchecker.ui.typhoon.detail.TyphoonDetailActivity
import com.yaeyama.linerchecker.ui.typhoon.detail.toTyphoonDetailUiModel
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonListTopAppBar
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonListViewModel
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonUiState
import com.yaeyama_liner_checker.domain.tyhoon.Typhoon

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
        val uiState by vm.uiState.collectAsState()

        Scaffold(
            modifier = modifier,
            backgroundColor = Color.Transparent,
            topBar = {
                TyphoonListTopAppBar()
            },
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
            text = "エラーが発生しました",
            color = Color.White,
            style = MaterialTheme.typography.h6,
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
            text = "台風は発生していません。",
            color = Color.White,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
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
                onClick = { onItemClick(typhoon) },
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f),
            ) {
                Text(
                    text = typhoon.name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.body1,
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
