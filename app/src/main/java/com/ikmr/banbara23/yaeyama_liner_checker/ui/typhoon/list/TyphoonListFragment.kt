package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ikemura.shared.model.tyhoon.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme
import com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.detail.TyphoonDetailUiModel
import timber.log.Timber

/**
 * 台風一覧 Fragment
 */
class TyphoonListFragment : Fragment(R.layout.typhoon_list_fragment) {

    private lateinit var binding: TyphoonListFragmentBinding
    private val viewModel = TyphoonListViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = TyphoonListFragmentBinding.inflate(inflater, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.list.adapter = TyphoonRecyclerViewAdapter { typhoon: Typhoon ->
//            val uiModel = typhoon.toTyphoonUiModel()
//            navigateToTyphoonDetail(uiModel)
//        }
        fetchTyphoon()
    }

    /**
     * 台風を取得
     */
    private fun fetchTyphoon() {
        lifecycleScope.launchWhenCreated {
            viewModel.getTyphoonList().collect { typhoon ->
                Timber.d("typhoon: $typhoon")
//                bindTyphoon(typhoon)
            }
        }
    }

    /**
     * 表示
     */
    private fun bindTyphoon(typhoonList: List<Typhoon>) {
        Timber.d("bindTyphoon: $typhoonList")
        if (typhoonList.isEmpty()) {
            binding.list.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
            return
        }
        val adapter = binding.list.adapter as TyphoonRecyclerViewAdapter
        adapter.updateData(typhoonList)
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
