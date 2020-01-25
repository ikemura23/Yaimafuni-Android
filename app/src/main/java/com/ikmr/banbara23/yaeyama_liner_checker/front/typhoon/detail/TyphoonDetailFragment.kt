package com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon

class TyphoonDetailFragment : Fragment() {
    
    val typhoon: Typhoon
        get() = arguments?.getParcelable(Constants.BUNDLE_KEY_DETAIL) as Typhoon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_typhoon_detail, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) = TyphoonDetailFragment().apply { arguments = args }
    }
}
