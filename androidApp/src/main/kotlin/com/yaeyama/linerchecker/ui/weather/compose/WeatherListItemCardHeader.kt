package com.yaeyama.linerchecker.ui.weather.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.R

@Composable
fun WeatherListItemCardHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier.background(color = colorResource(id = R.color.primary)).fillMaxWidth()
            .padding(16.dp),
        style = TextStyle.Default.copy(color = Color.White),
    )
}

@Preview
@Composable
fun WeatherListItemCardHeaderPreview() {
    WeatherListItemCardHeader("タイトル")
}
