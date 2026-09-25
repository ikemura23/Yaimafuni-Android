package com.yaeyama.linerchecker.ui.common.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.ui.common.PreviewBox

/**
 * 画面中央にローディングを表示する（全画面共通）
 */
@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

/**
 * エラーメッセージと再試行ボタンを表示する（全画面共通）
 *
 * @param messageRes 表示するエラーメッセージ
 * @param onRetry 再試行の処理。null の場合は再試行ボタンを表示しない
 */
@Composable
fun ErrorContent(
    @StringRes messageRes: Int,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(messageRes),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        if (onRetry != null) {
            Text(
                text = stringResource(R.string.error_retry_hint),
                modifier = Modifier.padding(top = 8.dp),
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
            )
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onRetry,
            ) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

/**
 * [ErrorContent] を画面中央に表示する
 */
@Composable
fun FullScreenErrorContent(
    @StringRes messageRes: Int,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ErrorContent(messageRes = messageRes, onRetry = onRetry)
    }
}

@Preview
@Composable
private fun LoadingContentPreview() {
    PreviewBox {
        LoadingContent()
    }
}

@Preview
@Composable
private fun FullScreenErrorContentPreview() {
    PreviewBox {
        FullScreenErrorContent(messageRes = R.string.dashboard_fetch_failed, onRetry = {})
    }
}
