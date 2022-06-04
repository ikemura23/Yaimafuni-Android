package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ikemura.shared.model.tyhoon.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme
import com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.detail.TyphoonDetailUiModel

/**
 * 台風一覧 Fragment
 */
class TyphoonListFragment : Fragment(R.layout.typhoon_list_fragment) {

    private val viewModel: TyphoonListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = TyphoonListFragmentBinding.inflate(inflater, container, false)
        binding.composeView.apply {

            setContent {
                // ViewのLifecycleOwnerが破棄されたときに、コンポジションを破棄する
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                YaimafuniAndroidTheme {
                    TyphoonListContent(
                        typhoonListViewModel = viewModel,
                        onItemClick = { typhoon: Typhoon ->
                            navigateToTyphoonDetail(typhoon.toTyphoonUiModel())
                        }
                    )
                }
            }
        }
        return binding.root
    }

    private fun navigateToTyphoonDetail(uiModel: TyphoonDetailUiModel) {
        TyphoonListFragmentDirections.actionTyphoonListFragmentToTyphoonDetailFragment(uiModel).let {
            findNavController().navigate(it)
        }
    }

    private fun Typhoon.toTyphoonUiModel(): TyphoonDetailUiModel {
        return TyphoonDetailUiModel(
            name = this.name,
            dateTime = this.dateTime,
            img = this.img,
            scale = this.scale,
            intensity = this.intensity,
            pressure = this.pressure,
            area = this.area,
            maxWindSpeedNearCenter = this.maxWindSpeedNearCenter,
        )
    }
}
