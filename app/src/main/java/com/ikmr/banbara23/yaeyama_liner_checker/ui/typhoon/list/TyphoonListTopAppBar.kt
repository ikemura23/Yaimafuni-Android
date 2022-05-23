package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

@Composable
fun TyphoonListTopAppBar() {
    TopAppBar(title = { Text(text = "台風情報") })
}

@Preview
@Composable
fun TyphoonListTopAppBarPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonListTopAppBar()
        }
    }
}
