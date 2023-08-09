package com.yaeyama.linerchecker.ui.portstatusdetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun PortStatusDetailTabScreen() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("安栄観光", "八重山\n観光フェリー")
    Column {
        TabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabs.forEachIndexed { index, s ->
                // Tab()
            }
        }
    }
}

@Preview
@Composable
private fun PortStatusDetailTabRowPreview() {
    YaimafuniAndroidTheme {
        PortStatusDetailTabScreen()
    }
}
