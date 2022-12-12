package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikmr.banbara23.yaeyama_liner_checker.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherListItem(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onItemClick
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            CardHeader()
            CardContent()
        }
    }
}

@Composable
fun CardHeader() {
    Text(
        text = "今日の天気 MM月DD日 (E)",
        modifier = Modifier.background(color = colorResource(id = R.color.primary)).fillMaxWidth()
            .padding(16.dp),
        style = TextStyle.Default.copy(color = Color.White)
    )
}

@Composable
fun CardContent() {
    // 天気情報（天気・気温・風・波）
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("天気：曇り")
        Spacer(modifier = Modifier.height(16.dp))
        Text("気温 最高: N℃ 最低: 19℃")
        Spacer(modifier = Modifier.height(16.dp))
        Text("風：")
        Spacer(modifier = Modifier.height(16.dp))
        Text("波：")
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherListItemPreview() {
    WeatherListItem(
        onItemClick = {}
    )
}

@Preview
@Composable
fun CardHeaderPreview() {
    CardHeader()
}

@Preview(showBackground = true)
@Composable
fun CardContentPreview() {
    CardContent()
}
