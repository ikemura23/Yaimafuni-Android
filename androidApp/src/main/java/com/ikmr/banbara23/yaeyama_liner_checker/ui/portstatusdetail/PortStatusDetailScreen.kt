package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.StatusColor
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

/**
 * どこからも使われていない？
 */
@Composable
fun PortStatusDetailScreen() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "港名",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "ステータス",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .background(
                            color = StatusColor.Normal,
                            shape = RoundedCornerShape(16)
                        )
                        .padding(vertical = 4.dp, horizontal = 4.dp)
                )
            }
            Text(
                text = "コメントコメントコメントコメントコメントコメントコメントコメント",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PortStatusDetailScreenPreview() {
    YaimafuniAndroidTheme {
        PortStatusDetailScreen()
    }
}
