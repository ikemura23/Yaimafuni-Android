package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import com.ikemura.shared.repository.UiState
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.WeatherFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.observeEvent
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil
import timber.log.Timber

/**
 * 天気詳細
 */
class WeatherFragment : Fragment(R.layout.weather_fragment) {

    private val viewModel: WeatherScreenViewModel by lazy {
        ViewModelProvider(this).get(WeatherScreenViewModel::class.java)
    }
    private val binding: WeatherFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        // 今日の天気のリスト設定
        with(binding.today.timeList) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = WeatherTimeListAdaptor()
        }

        // 明日の天気のリスト設定
        with(binding.tomorrow.timeList) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = WeatherTimeListAdaptor()
        }

        setupViewModel()
        binding.weather = WeatherInfo() // null文字の表示対策、いつか対応して消したい
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getWeather().collect { state ->
                Timber.d(state.toString())
                when (state) {
                    is UiState.Success -> {
                        bindData(state.data)
                    }
                    is UiState.Error -> {
                        Timber.e(state.error)
                    }
                }
            }
        }

        viewModel.event.observeEvent(viewLifecycleOwner) { nav ->
            when (nav) {
                is WeatherScreenViewModel.Nav.Error -> Timber.e("WeatherFragment でエラー発生")
                is WeatherScreenViewModel.Nav.More -> openBrowser()
            }
        }
    }

    private fun bindData(weather: WeatherInfo) {
        Timber.d(weather.toString())
        binding.weather = weather // TODO:エラー解決したい
        (binding.today.timeList.adapter as WeatherTimeListAdaptor).update(weather.today.table)
        (binding.tomorrow.timeList.adapter as WeatherTimeListAdaptor).update(weather.tomorrow.table)
    }

    private fun openBrowser() {
        CustomTabUtil.start(requireActivity(), Constants.WEATHER_URL)
    }
}
