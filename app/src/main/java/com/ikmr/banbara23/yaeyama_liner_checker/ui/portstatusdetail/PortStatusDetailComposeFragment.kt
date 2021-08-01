package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.android.material.composethemeadapter.MdcTheme

/**
 * 運行詳細のCompose Fragment
 */
class PortStatusDetailComposeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // FragmentにComposeを使う書き方
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    Screen()
                }
            }
        }
    }
}

@Composable
private fun Screen() {
    Card {
        Row {
            Text(
                text = "港名",
            )
            Text(
                text = "ステータス",
            )
        }

    }
}

@Preview
@Composable
private fun PreviewScreen() {
    Screen()
}