package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

/**
 * Composeの台風一覧画面
 */
@Composable
fun TyphoonListScreen() {
    Column {
        TyphoonListTopAppBar()
    }
}

@Preview
@Composable
fun TyphoonListScreenPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonListScreen()
        }
    }
}
