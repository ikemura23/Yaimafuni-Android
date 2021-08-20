package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.ui.theme.StatusColor
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.ui.theme.TableHeaderColor
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.ui.theme.YaimafuniAndroidTheme

/**
 * 運行詳細のCompose Fragment
 */
class PortStatusDetailComposeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // FragmentにComposeを使う書き方
        return ComposeView(requireContext()).apply {
            setContent {
                YaimafuniAndroidTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        Screen()
                    }
                }
            }
        }
    }
}

@Composable
private fun Screen() {
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
                )
                Text(
                    text = "ステータス",
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            color = StatusColor.Normal,
                            shape = RoundedCornerShape(16)
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
            Text(
                text = "コメントコメントコメントコメントコメントコメントコメントコメント",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            )
        }
    }
}

/**
 * テーブルのヘッダー
 */
@Composable
fun TimeTableListHeader(
    leftTitle: String = "石垣島",
    rightTitle: String = "大原港",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = TableHeaderColor),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            text = leftTitle,
            modifier = Modifier.padding(4.dp),
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        Text(
            text = rightTitle,
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
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            // 時刻
            Text(
                text = leftTime,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
            )
            // ステータス文字
            Text(
                text = leftStatus,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
            )
        }
        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        // 右側（ターゲット港）
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            // 時刻
            Text(
                text = rightTime,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
            )
            // ステータス文字
            Text(
                text = rightStatus,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewPortStatusDetail() {
    YaimafuniAndroidTheme {
        TimeTableListItem()
    }
}