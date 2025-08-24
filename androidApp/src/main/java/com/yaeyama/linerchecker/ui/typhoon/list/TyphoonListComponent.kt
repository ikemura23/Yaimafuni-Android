package com.yaeyama.linerchecker.ui.typhoon.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.tyhoon.Typhoon

@Composable
fun TyphoonListContent(
    modifier: Modifier = Modifier,
    typhoonListViewModel: TyphoonListViewModel = viewModel(),
    onItemClick: (Typhoon) -> Unit,
) {
    // 作りかけ
//    val uiState = typhoonListViewModel.uiState.collectAsState()
//    when (uiState.value) {
//        is TyphoonUiState.Data -> {
//            // TODO: asでの変換をやめたい
//            val typhoons = (uiState.value as TyphoonUiState.Data).typhoons
//            if (typhoons.isEmpty()) {
//                TyphoonListEmptyContent()
//            } else {
//                LazyColumn() {
//                    items(typhoons.size) { index ->
//                        TyphoonListItemComponent(typhoons[index], onItemClick)
//                    }
//                }
//            }
//        }
//        else -> TyphoonListEmptyContent()
//    }
    val data = typhoonListViewModel.getTyphoonList().collectAsState(initial = null)
    if (data.value == null) {
        // loading中
    } else if (data.value!!.isEmpty()) {
        TyphoonListEmptyContent()
    } else {
        LazyColumn(
            modifier = modifier,
        ) {
            items(data.value!!.size) { index ->
                TyphoonListItemComponent(data.value!![index], onItemClick)
            }
        }
    }
}

/**
 * 空の表示
 */
@Composable
fun TyphoonListEmptyContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(id = R.string.typhoon_list_empty),
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun TyphoonListContentPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonListContent { /* 処理 */ }
        }
    }
}

@Preview
@Composable
private fun TyphoonListEmptyContentPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonListEmptyContent()
        }
    }
}
