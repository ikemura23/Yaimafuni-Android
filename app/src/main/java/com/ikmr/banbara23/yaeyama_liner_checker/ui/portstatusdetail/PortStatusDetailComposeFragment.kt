package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
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
    Text(
        text = "hello world",
        style = MaterialTheme.typography.h5,
    )
}

@Preview
@Composable
private fun PreviewScreen() {
    Screen()
}