package com.ikmr.banbara23.yaeyama_liner_checker.front.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.WeatherFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * A placeholder fragment containing a simple view.
 */
class WeatherFragment : Fragment() {

    private val viewModel: WeatherScreenViewModel by lazy {
        ViewModelProviders.of(this).get(WeatherScreenViewModel::class.java)
    }
    private lateinit var binding: WeatherFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.weather_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleBar.setNavigationOnClickListener { findNavController().navigateUp() }

        // 今日の天気のリスト設定
        with(binding.today.timeList) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = WeatherTimeListAdaptor()
        }

        // 明日の天気のリスト設定
        with(binding.tomorrow.timeList) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = WeatherTimeListAdaptor()
        }

        setupViewModel()
        viewModel.fetchWeather()
    }

    private fun setupViewModel() {
        viewModel.item.observe(viewLifecycleOwner) {
            (binding.today.timeList.adapter as WeatherTimeListAdaptor).update(it.today.table)
            (binding.tomorrow.timeList.adapter as WeatherTimeListAdaptor).update(it.tomorrow.table)
        }

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it) {
                is WeatherScreenViewModel.Nav.Error -> {
                    //エラーメッセージ
                }
                is WeatherScreenViewModel.Nav.More -> openBrowser()
            }
        })
    }

    private fun openBrowser() {
        CustomTabUtil.start(requireActivity(), Constants.WEATHER_URL)
    }
}
