package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikemura.shared.model.statusdetail.Status
import com.ikemura.shared.model.time_table.Header
import com.ikemura.shared.model.time_table.RowItem
import com.ikemura.shared.model.time_table.TimeTable
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.ui.theme.TableDividerColor
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.ui.theme.TableHeaderColor
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.ui.theme.YaimafuniAndroidTheme
import timber.log.Timber

@Composable
fun TimeTableList(timeTable: TimeTable) {
    Timber.d(timeTable.toString())
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TimeTableListHeader(
                header = timeTable.header
            )
            timeTable.row.forEach { row ->
                Divider(
                    color = TableDividerColor,
                    modifier = Modifier.height(1.dp)
                )
                TimeTableListItem(
                    leftStatus = row.left.status.text,
                    leftTime = row.left.time,
                    rightStatus = row.right.status.text,
                    rightTime = row.right.time
                )
            }
        }
    }
}

/**
 * テーブルのヘッダー
 */
@Composable
fun TimeTableListHeader(
    header: Header = Header(left = "石垣島", right = "大原港"),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = TableHeaderColor),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            text = header.left,
            modifier = Modifier.padding(4.dp),
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        Text(
            text = header.right,
            modifier = Modifier.padding(4.dp),
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

/**
 * テーブルのアイテム
 */
@Composable
fun TimeTableListItem(
    leftTime: String = "00:00",
    leftStatus: String = "通常運行",
    rightTime: String = "00:00",
    rightStatus: String = "通常運行",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        // 左側（石垣）
        TimeTableRowItem(
            time = leftTime,
            status = leftStatus
        )
        Divider(
            color = TableDividerColor,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        // 右側（ターゲット港）
        TimeTableRowItem(
            time = rightTime,
            status = rightStatus
        )
    }
}

@Composable
fun TimeTableRowItem(time: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        // modifier = Modifier.weight(1f)
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        // 時刻
        Text(
            text = time,
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
        )
        // ステータス文字
        Text(
            text = status,
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
        )
    }
}

@Preview
@Composable
private fun TimeTableListPreview() {
    YaimafuniAndroidTheme {
        Surface(color = MaterialTheme.colors.background) {
            val items = listOf("10:00", "b", "c", "d", "f")
            listOf("10:00", "11:00", "12:00", "13:00", "14:00")
            val header = Header(left = "石垣島", right = "大原港")
            val rowItem = RowItem(
                status = Status(code = "nomal", text = "通常運行"),
                time = "00:00",
                memo = "", // 使ってる？
            )
            val row = com.ikemura.shared.model.time_table.Row(
                left = rowItem,
                right = rowItem
            )
            val rows = listOf(row, row, row, row, row, row, row)
            val timeTable = TimeTable(
                header = header,
                row = rows
            )
            TimeTableList(timeTable)
        }
    }
}

@Preview(name = "ヘッダー")
@Composable
fun TimeTableListHeaderPreview() {
    val dummy = Header(left = "石垣島", right = "大原港")
    YaimafuniAndroidTheme {
        TimeTableListHeader(dummy)
    }
}

@Preview(name = "ボディ > Row")
@Composable
fun TimeTableListRowPreview() {
    YaimafuniAndroidTheme {
        Surface(color = MaterialTheme.colors.background) {
            TimeTableListItem(
                leftTime = "00:00",
                leftStatus = "通常運行",
                rightTime = "00:00",
                rightStatus = "通常運行",
            )
        }
    }
}

@Preview(name = "ボディ > Row > アイテム")
@Composable
private fun TimeTableRowItemPreview() {
    YaimafuniAndroidTheme {
        Surface(color = MaterialTheme.colors.background) {
            TimeTableRowItem(
                time = "16：45\n" +
                    "    上原経由",
                status = "通常運行"
            )
        }
    }
}
