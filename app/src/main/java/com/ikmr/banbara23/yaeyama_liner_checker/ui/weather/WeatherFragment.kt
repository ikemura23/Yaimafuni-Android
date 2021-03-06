package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.WeatherFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.observeEvent
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import com.ikemura.shared.model.weather.WeatherInfo
import com.ikemura.shared.repository.WeatherUiState
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/**
 * 天気詳細
 */
class WeatherFragment : Fragment(R.layout.weather_fragment) {

    private val viewModel: WeatherScreenViewModel by lazy {
        ViewModelProvider(this).get(WeatherScreenViewModel::class.java)
    }
    private val binding: WeatherFragmentBinding by viewBinding()

    @ExperimentalCoroutinesApi
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
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getWeather().collect { state ->
                Timber.d(state.toString())
                when (state) {
                    is WeatherUiState.Success -> {
                        bindData(state.weatherInfo)
                    }
                    is WeatherUiState.Error -> {
                        Timber.e(state.message)
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
        binding.weather = weather // TODO:エラー解決したい
        (binding.today.timeList.adapter as WeatherTimeListAdaptor).update(weather.today.table)
        (binding.today.timeList.adapter as WeatherTimeListAdaptor).update(weather.tomorrow.table)
    }

    private fun openBrowser() {
        CustomTabUtil.start(requireActivity(), Constants.WEATHER_URL)
    }
}
