package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

@Composable
fun TyphoonListContent(
    typhoonListViewModel: TyphoonListViewModel = viewModel()
) {
    val data = typhoonListViewModel.getTyphoonList().collectAsState(initial = null)
    data.value?.let {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            LazyColumn() {
                items(it.size) { index ->
                    TyphoonListItemComponent(it[index])
                }
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
            TyphoonListContent()
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
