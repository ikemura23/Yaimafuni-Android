package com.ikemura23.yaeyama.linerchecker.ui.compose.typhoon

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikemura23.yaeyama.linerchecker.R
import com.ikemura23.yaeyama.linerchecker.ui.compose.common.TitleAppBar
import com.ikemura23.yaeyama.linerchecker.ui.theme.BackgroundSkyColor
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun TyphoonScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TitleAppBar(
                stringResource(R.string.title_typhoon)
            )
        },
        containerColor = BackgroundSkyColor,
    ) { contentPadding ->
        Text(
            text = "TODO",
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
        )
    }
}

@Preview
@Composable
private fun TyphoonScreenPreview() {
    YaimafuniAndroidTheme {
        TyphoonScreen()
    }
}