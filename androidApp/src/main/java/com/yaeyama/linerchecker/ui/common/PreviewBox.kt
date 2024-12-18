package com.yaeyama.linerchecker.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.yaeyama.linerchecker.ui.theme.Purple700
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

/**
 * 共通プレビュー用
 * プレビューでthemeを忘れたりcolorが自動で指定される
 */
@Composable
fun PreviewBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Purple700,
    content: @Composable () -> Unit,
) {
    YaimafuniAndroidTheme {
        Box(
            modifier = modifier.background(backgroundColor)
        ) { content() }
    }
}

@Composable
@Preview
private fun PreviewBoxPreview() {
    PreviewBox {
        Text(text = "hello")
    }
}