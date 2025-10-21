package com.yaeyama.linerchecker.ui.portstatusdetail.component

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
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.theme.TableDividerColor
import com.yaeyama.linerchecker.ui.theme.TableHeaderColor
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.statusdetail.Status
import com.yaeyama_liner_checker.domain.time_table.Header
import com.yaeyama_liner_checker.domain.time_table.Row
import com.yaeyama_liner_checker.domain.time_table.RowItem
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import timber.log.Timber

typealias TimeRow = Row

@Composable
fun TimeTableList(timeTable: TimeTable) {
    Timber.d(timeTable.toString())
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            TimeTableListHeader(
                header = timeTable.header,
            )
            timeTable.row.forEach { row ->
                HorizontalDivider(
                    color = TableDividerColor,
                    modifier = Modifier.height(1.dp),
                )
                TimeTableListItem(
                    leftStatus = row.left.status.text,
                    leftTime = row.left.time,
                    rightStatus = row.right.status.text,
                    rightTime = row.right.time,
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
        // TODO: modifierをパラメータで渡したくないため、weightを使わない方法にできるか？
        val modifier = Modifier
            .fillMaxWidth()
            .weight(1f)

        // 左側（石垣）
        TimeTableRowItem(
            time = leftTime,
            status = leftStatus,
            modifier = modifier,
        )
        HorizontalDivider(
            color = TableDividerColor,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
        )
        // 右側（ターゲット港）
        TimeTableRowItem(
            time = rightTime,
            status = rightStatus,
            modifier = modifier,
        )
    }
}

@Composable
fun TimeTableRowItem(time: String, status: String, modifier: Modifier) {
    Row(
        modifier = modifier,
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

@Preview(showBackground = true)
@Composable
private fun TimeTableListPreview() {
    YaimafuniAndroidTheme {
        val header = Header(left = "石垣島", right = "大原港")
        val rowItem = RowItem(
            status = Status(code = "nomal", text = "通常運行"),
            time = "00:00",
            memo = "", // 使ってる？
        )
        // TimeRowとは、typealiasでRowクラスの別名、composeのRowと名前が同じなので紛らわしい
        val row = TimeRow(
            left = rowItem,
            right = rowItem,
        )
        val rows = List(5) { row }
        val timeTable = TimeTable(
            header = header,
            row = rows,
        )
        TimeTableList(timeTable)
    }
}

@Preview(name = "ヘッダー")
@Composable
private fun TimeTableListHeaderPreview() {
    val dummy = Header(left = "石垣島", right = "大原港")
    YaimafuniAndroidTheme {
        TimeTableListHeader(dummy)
    }
}

@Preview(name = "ボディ > Row", showBackground = true)
@Composable
private fun TimeTableListRowPreview() {
    YaimafuniAndroidTheme {
        TimeTableListItem(
            leftTime = "00:00",
            leftStatus = "通常運行",
            rightTime = "00:00",
            rightStatus = "通常運行",
        )
    }
}

@Preview(name = "TimeTableRowItem", showBackground = true)
@Composable
private fun TimeTableRowItemPreview() {
    YaimafuniAndroidTheme {
        val modifier = Modifier
            .fillMaxWidth()

        TimeTableRowItem(
            time = "16：45\n" +
                "    上原経由",
            status = "通常運行",
            modifier,
        )
    }
}
