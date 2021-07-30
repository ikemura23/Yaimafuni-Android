package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

/**
 * 運行詳細のCompose Fragment
 */
class PortStatusDetailComposeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // FragmentにComposeを使う書き方
        return ComposeView(requireContext()).apply {
            setContent {

            }
        }
    }
}
