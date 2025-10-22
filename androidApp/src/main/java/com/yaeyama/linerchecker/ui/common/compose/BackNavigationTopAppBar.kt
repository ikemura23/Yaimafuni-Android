package com.yaeyama.linerchecker.ui.common.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.yaeyama.linerchecker.ui.common.PreviewBox

/**
 * 戻るボタン付きのTopAppBar
 * 詳細画面などで使用する共通コンポーネント
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackNavigationTopAppBar(
    title: String,
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "戻る",
                    tint = Color.White,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@Preview
@Composable
private fun BackNavigationTopAppBarPreview() {
    PreviewBox {
        BackNavigationTopAppBar(
            title = "港名詳細画面",
            onBackPressed = {},
        )
    }
}
