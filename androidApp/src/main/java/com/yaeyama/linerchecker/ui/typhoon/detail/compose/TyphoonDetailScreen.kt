package com.yaeyama.linerchecker.ui.typhoon.detail.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.common.compose.BackNavigationTopAppBar
import com.yaeyama.linerchecker.ui.main.compose.MainScaffold
import com.yaeyama.linerchecker.ui.typhoon.detail.TyphoonDetailUiModel

/**
 * 台風詳細画面のCompose実装
 * 既存のTyphoonDetailFragmentと同等の機能を提供
 */
@Composable
fun TyphoonDetailScreen(
    typhoon: TyphoonDetailUiModel?,
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current

    MainScaffold(
        topBar = {
            BackNavigationTopAppBar(
                title = typhoon?.name ?: "台風詳細",
                onBackPressed = onBackPressed,
            )
        },
        // backgroundColor = Color.Transparent,
    ) { paddingValues ->
        if (typhoon != null) {
            TyphoonDetailContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                typhoon = typhoon,
                onWebButtonClick = {
                    // Webブラウザで天気サイトを開く
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TYPHOON_WEB_URL))
                    context.startActivity(intent)
                },
            )
        } else {
            ErrorContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            )
        }
    }
}

@Composable
private fun TyphoonDetailContent(
    modifier: Modifier = Modifier,
    typhoon: TyphoonDetailUiModel,
    onWebButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // 台風画像
        AsyncImage(
            model = typhoon.img,
            contentDescription = "${typhoon.name}の台風画像",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop,
        )

        // 台風情報カード
        Card(
            modifier = Modifier.fillMaxWidth(),
            // elevation = 2.dp,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TyphoonInfoItem(
                    label = "更新日",
                    value = typhoon.dateTime,
                )

                TyphoonInfoItem(
                    label = "大きさ",
                    value = typhoon.scale,
                )

                TyphoonInfoItem(
                    label = "強さ",
                    value = typhoon.intensity,
                )

                TyphoonInfoItem(
                    label = "存在地域",
                    value = typhoon.area,
                )

                TyphoonInfoItem(
                    label = "中心気圧",
                    value = typhoon.pressure,
                )

                TyphoonInfoItem(
                    label = "中心最大風速",
                    value = typhoon.maxWindSpeedNearCenter,
                )
            }
        }

        // Webブラウザで見るボタン
        Button(
            onClick = onWebButtonClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange3),
            ),
        ) {
            Text(
                text = "Webブラウザでみる",
                color = Color.White,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TyphoonInfoItem(
    label: String,
    value: String,
) {
    Text(
        text = "$label: $value",
        fontSize = 16.sp,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "台風データの読み込みに失敗しました",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

/** 台風 Webページ URL */
private const val TYPHOON_WEB_URL = "https://tenki.jp/lite/bousai/typhoon/"
