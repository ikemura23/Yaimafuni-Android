package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.WeatherFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

/**
 * 天気一覧
 */
class WeatherFragment : Fragment(R.layout.weather_fragment) {

    private val viewModel: WeatherScreenViewModel by lazy {
        ViewModelProvider(this).get(WeatherScreenViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = WeatherFragmentBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setContent {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                YaimafuniAndroidTheme {
                    WeatherScreen(
                        weatherViewModel = viewModel
                    )
                }
            }
        }
        return binding.root
    }
}
