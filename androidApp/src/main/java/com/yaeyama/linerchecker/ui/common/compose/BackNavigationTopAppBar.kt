package com.yaeyama.linerchecker.ui.common.compose

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * 戻るボタン付きのTopAppBar
 * 詳細画面などで使用する共通コンポーネント
 */
@Composable
fun BackNavigationTopAppBar(
    title: String,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "戻る",
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}