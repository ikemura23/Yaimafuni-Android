package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

@Composable
fun TyphoonListItemComponent() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TyphoonName("台風18号(ミートク)")
            UpdatedTime("2022/05/24 現在")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TyphoonImage("")
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .height(120.dp)
                    .weight(1f)
            ) {
                TyphoonScale("あああ")
                TyphoonIntensity("あああ")
                TyphoonPressure("あああ")
                TyphoonMaxWindSpeedNearCenter("あああ")
            }
            ArrowImage()
        }
    }
}

@Preview
@Composable
private fun TyphoonListItemComponentPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonListItemComponent()
        }
    }
}

/**
 * 台風の名前
 */
@Composable
private fun TyphoonName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.subtitle1.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        ),
    )
}

@Preview
@Composable
private fun TyphoonNameScreen() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonName("台風18号(ミートク)")
        }
    }
}

/**
 * 更新日時
 */
@Composable
private fun UpdatedTime(value: String) {
    Text(
        text = value,
        style = MaterialTheme.typography.body2,
    )
}

@Preview
@Composable
private fun UpdatedTimePreview() {
    YaimafuniAndroidTheme {
        Surface {
            UpdatedTime("2022/05/24 現在")
        }
    }
}

/**
 * 台風の画像
 */
@Composable
private fun TyphoonImage(url: String) {
    Image(
        painter = painterResource(id = R.drawable.ship),
        contentDescription = null,
        modifier = Modifier
            .size(120.dp)
            .padding(top = 8.dp, end = 8.dp),
    )
}

@Preview
@Composable
private fun TyphoonImagePreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonImage("2022/05/24 現在")
        }
    }
}

/**
 * 台風の大きさ、強さ、中心気圧、中心の最大気圧
 */
@Composable
private fun TyphoonTextContent(value: String) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.height(120.dp)
    ) {
        TyphoonScale("あああ")
        TyphoonIntensity("あああ")
        TyphoonPressure("あああ")
        TyphoonMaxWindSpeedNearCenter("あああ")
    }
}

/**
 * 大きさ
 */
@Composable
private fun TyphoonScale(value: String) {
    Text(
        text = "大きさ : $value",
        style = MaterialTheme.typography.caption,
    )
}

/**
 * 台風の強さ
 */
@Composable
private fun TyphoonIntensity(value: String) {
    Text(
        text = "強さ : $value",
        style = MaterialTheme.typography.caption,
    )
}

/**
 * 気圧
 */
@Composable
private fun TyphoonPressure(value: String) {
    Text(
        text = "中心気圧 : $value",
        style = MaterialTheme.typography.caption,
    )
}

/**
 * 中心の最大風速
 */
@Composable
private fun TyphoonMaxWindSpeedNearCenter(value: String) {
    Text(
        text = "中心の最大風速 : $value",
        style = MaterialTheme.typography.caption,
    )
}

@Preview
@Composable
fun TyphoonTextContentPreview() {
    YaimafuniAndroidTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(120.dp)
            ) {
                TyphoonScale("あああ")
                TyphoonIntensity("あああ")
                TyphoonPressure("あああ")
                TyphoonMaxWindSpeedNearCenter("あああ")
            }
        }
    }
}

@Composable
fun ArrowImage() {
    Image(
        painter = painterResource(R.drawable.ic_keyboard_arrow_right_black_24dp),
        contentDescription = null,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Preview
@Composable
fun ArrowImagePreview() {
    YaimafuniAndroidTheme {
        Surface {
            ArrowImage()
        }
    }
}
