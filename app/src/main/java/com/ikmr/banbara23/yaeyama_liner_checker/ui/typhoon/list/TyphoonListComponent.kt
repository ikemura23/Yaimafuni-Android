package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ikemura.shared.model.tyhoon.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

@Composable
fun TyphoonListContent(
    typhoonListViewModel: TyphoonListViewModel = viewModel(),
    onItemClick: (Typhoon) -> Unit
) {
    val data = typhoonListViewModel.getTyphoonList().collectAsState(initial = null)
    data.value?.let {
        LazyColumn() {
            items(it.size) { index ->
                TyphoonListItemComponent(it[index], onItemClick)
            }
        }
    }
}

/**
 * 空の表示
 */
@Composable
fun TyphoonListEmptyContent() {
    Text(text = "空です")
}

@Preview
@Composable
fun TyphoonListContentPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonListContent { typhoon: com.ikemura.shared.model.tyhoon.Typhoon -> /* 処理 */ }
        }
    }
}

@Preview
@Composable
fun TyphoonListEmptyContentPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonListEmptyContent()
        }
    }
}
