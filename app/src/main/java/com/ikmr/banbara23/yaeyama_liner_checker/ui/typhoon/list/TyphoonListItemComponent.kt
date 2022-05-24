package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
        modifier = Modifier.size(120.dp),
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

@Composable
private fun TyphoonTextContent(value: String) {
    Text(
        text = value,
        style = MaterialTheme.typography.body2,
    )
}

@Preview
@Composable
fun TyphoonTextContentPreview() {
    YaimafuniAndroidTheme {
        Surface {
            TyphoonTextContent("大きさ：大型")
        }
    }
}
