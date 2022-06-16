package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonDetailFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil
import com.squareup.picasso.Picasso
import timber.log.Timber

/**
 * 台風詳細 Fragment
 */
class TyphoonDetailFragment : Fragment(R.layout.typhoon_detail_fragment) {
    private val binding: TyphoonDetailFragmentBinding by viewBinding()
    private val argTyphoon: TyphoonDetailUiModel by lazy {
        TyphoonDetailFragmentArgs.fromBundle(requireArguments()).typhoon
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d(argTyphoon.toString())

        with(binding) {
            typhoon = argTyphoon
            // タイトル設定
            includeTitle.titleBar.setNavigationOnClickListener { findNavController().navigateUp() }
            // Webで見るボタン
            button.setOnClickListener {
                CustomTabUtil.start(requireActivity(), Constants.TYPHOON_URL)
            }
        }
        // 画像読み込み
        Picasso.get().load(argTyphoon.img).into(binding.image)
    }
}
