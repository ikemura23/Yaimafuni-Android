package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

/**
 * 天気一覧
 */
class WeatherFragment : Fragment() {

    private val viewModel: WeatherScreenViewModel by lazy {
        ViewModelProvider(this).get(WeatherScreenViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                setContent {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    YaimafuniAndroidTheme {
                        WeatherScreen(
                            weatherViewModel = viewModel,
                        )
                    }
                }
            }
        }
    }
}
