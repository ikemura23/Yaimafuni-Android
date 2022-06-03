package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

/**
 * Composeの台風一覧画面
 */
@Composable
fun TyphoonListScreen() {
    Scaffold(
        modifier = Modifier,
        topBar = {
            TyphoonListTopAppBar()
        },
        content = { _ ->
            TyphoonListContent { typhoon: com.ikemura.shared.model.tyhoon.Typhoon -> /* 処理 */ }
//            TyphoonListEmptyContent()
        }
    )
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
