package com.yaeyama.linerchecker.ui.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.domain.statusdetail.hasOperationStatus
import com.yaeyama.linerchecker.ui.common.getStatusBackgroundColor
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun DashBoardRowItem(
    modifier: Modifier,
    companyName: String,
    status: Status,
    companyAccessibilityName: String = companyName,
) {
    val statusBackgroundColor = status.getStatusBackgroundColor()
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (status.hasOperationStatus) {
            Text(
                text = companyName,
                modifier = Modifier.semantics { contentDescription = companyAccessibilityName },
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = status.text,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .background(
                        color = statusBackgroundColor,
                        shape = RoundedCornerShape(16),
                    )
                    .padding(4.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashBoardRowItemPreview(
    @PreviewParameter(ItemPreviewProvider::class)
    item: DashBoardRowItem,
) {
    YaimafuniAndroidTheme {
        DashBoardRowItem(
            companyName = item.companyName,
            status = item.status,
            modifier = Modifier,
        )
    }
}

/**
 * Preview用のクラス
 */
private data class DashBoardRowItem(
    val companyName: String,
    val status: Status,
)

private class ItemPreviewProvider : CollectionPreviewParameterProvider<DashBoardRowItem>(
    listOf(
        DashBoardRowItem(
            companyName = "安栄",
            status = Status(
                "normal",
                "通常運転",
            ),
        ),
        DashBoardRowItem(
            companyName = "YKF",
            status = Status(
                "cation",
                "未定",
            ),
        ),
        DashBoardRowItem(
            companyName = "その他",
            status = Status(
                "cancel",
                "欠航",
            ),
        ),
    ),
)
