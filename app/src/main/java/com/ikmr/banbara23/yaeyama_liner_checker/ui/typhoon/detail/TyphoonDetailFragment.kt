package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonDetailFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil
import com.squareup.picasso.Picasso
import timber.log.Timber

/**
 * 台風詳細 Fragment
 */
class TyphoonDetailFragment : Fragment() {
    private lateinit var binding: TyphoonDetailFragmentBinding
    private val typhoon: Typhoon? by lazy {
        TyphoonDetailFragmentArgs.fromBundle(requireArguments()).typhoon
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.typhoon_detail_fragment, container, false)
        binding.includeTitle.titleBar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.includeTitle.title = typhoon?.name
        binding.typhoon = typhoon
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 画像読み込み
        Timber.d(typhoon.toString())
        typhoon?.let { Picasso.get().load(it.img).into(binding.image) }
        // Webで見るボタン
        binding.button.setOnClickListener {
            CustomTabUtil.start(requireActivity(), Constants.TYPHOON_URL)
        }
    }
}
