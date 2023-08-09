package com.yaeyama.linerchecker.ui.typhoon.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonDetailFragmentBinding
import com.yaeyama.linerchecker.ext.viewBinding
import com.yaeyama.linerchecker.utils.CustomTabUtil
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
                CustomTabUtil.start(requireActivity(), TYPHOON_WEB_URL)
            }
        }
        // 画像読み込み
        binding.image.load(argTyphoon.img)
    }
}

/** 台風 Webページ URL */
private const val TYPHOON_WEB_URL = "https://tenki.jp/lite/bousai/typhoon/"
