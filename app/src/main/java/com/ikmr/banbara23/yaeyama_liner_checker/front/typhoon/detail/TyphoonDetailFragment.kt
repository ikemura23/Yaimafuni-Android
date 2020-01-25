package com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon.detail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.FragmentTyphoonDetailBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import com.squareup.picasso.Picasso

/**
 * 台風詳細 Fragment
 */
class TyphoonDetailFragment : Fragment() {
    private lateinit var binding: FragmentTyphoonDetailBinding
    private val typhoon: Typhoon
        get() = arguments!!.getParcelable(Constants.BUNDLE_KEY_DETAIL) as Typhoon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_typhoon_detail, container, false)
        binding.typhoon = typhoon
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 戻る設定
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { requireActivity().finish() }
        // 画像読み込み
        Picasso.get().load(typhoon.img).into(binding.image)
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) = TyphoonDetailFragment().apply { arguments = args }
    }
}
