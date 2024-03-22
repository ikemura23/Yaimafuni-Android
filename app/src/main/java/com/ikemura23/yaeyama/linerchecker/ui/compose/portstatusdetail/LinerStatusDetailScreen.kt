package com.ikemura23.yaeyama.linerchecker.ui.compose.portstatusdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 運行ステータス詳細画面
 */
@Composable
fun LinerStatusDetailScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "運行詳細スクリーン")
    }
}
