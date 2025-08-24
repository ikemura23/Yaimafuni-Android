package com.yaeyama.linerchecker.ui.typhoon.list

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun TyphoonListTopAppBar() {
    TopAppBar(title = { Text(text = "台風情報") })
}

@Preview
@Composable
private fun TyphoonListTopAppBarPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonListTopAppBar()
        }
    }
}
