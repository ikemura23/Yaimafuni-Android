package com.yaeyama.linerchecker.ui.portstatusdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.common.getStatusBackgroundColor
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.statusdetail.Status
import timber.log.Timber

/**
 * 運行詳細のに上部に表示するステータスとコメント
 */
@Composable
fun PortMainStatus(
    portName: String,
    status: Status,
    statusDescription: String,
) {
    Timber.d("portName: $portName")
    Timber.d("status: $status")
    Timber.d("statusDescription: $statusDescription")

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = portName,
                    style = MaterialTheme.typography.h6,
                )
                if (status.code.isNotEmpty()) {
                    // ステータスの背景色
                    val statusBackgroundColor = status.getStatusBackgroundColor()
                    Text(
                        text = status.text,
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .background(
                                color = statusBackgroundColor,
                                shape = RoundedCornerShape(16),
                            )
                            .padding(vertical = 4.dp, horizontal = 4.dp),
                    )
                }
            }
            if (statusDescription.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = statusDescription,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PortMainStatusPreview() {
    YaimafuniAndroidTheme {
        Surface(color = MaterialTheme.colors.background) {
            PortMainStatus(
                portName = "港名",
                status = Status("nomal", "通常運行"),
                statusDescription = "コメント",
            )
        }
    }
}
