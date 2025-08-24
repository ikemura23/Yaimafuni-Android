package com.yaeyama.linerchecker.ui.typhoon.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

/**
 * Composeの台風一覧画面
 */
@Composable
fun TyphoonListScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TyphoonListTopAppBar()
        },
        content = { padding ->
            TyphoonListContent(
                modifier = Modifier.padding(padding),
                onItemClick = { /* 処理 */ },
            )
        },
    )
}

@Preview
@Composable
private fun TyphoonListScreenPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonListScreen()
        }
    }
}
