package com.yaeyama.linerchecker.ui.typhoon.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.common.PreviewBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TyphoonListTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.typhoon_list_screen_title),
                color = Color.White,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@Preview
@Composable
private fun TyphoonListTopAppBarPreview() {
    PreviewBox {
        TyphoonListTopAppBar()
    }
}
