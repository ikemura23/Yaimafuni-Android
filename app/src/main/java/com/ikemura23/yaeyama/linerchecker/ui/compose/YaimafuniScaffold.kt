package com.ikemura23.yaeyama.linerchecker.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.ikemura23.yaeyama.linerchecker.ui.theme.BackgroundSkyColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun YaimafuniScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = BackgroundSkyColor,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier.semantics { testTagsAsResourceId = true },
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        content = content,
    )
}