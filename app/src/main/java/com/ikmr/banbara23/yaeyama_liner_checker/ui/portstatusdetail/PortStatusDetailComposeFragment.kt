package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.ui.theme.StatusColor
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
                    color = StatusColor.Normal,
                    modifier = Modifier
                        .background(
                            color = Color.Blue,
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

@Preview(showBackground = true)
@Composable
fun DefaultPreviewPortStatusDetail() {
    YaimafuniAndroidTheme {
        Screen()
    }
}