package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.ikemura.shared.model.tyhoon.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonRepositoryOld
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonUiState
import com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.detail.TyphoonDetailUiModel
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/**
 * 台風一覧 Fragment
 */
class TyphoonListFragment : Fragment(R.layout.typhoon_list_fragment),
    OnTyphoonDetailFragmentInteractionListener {

    private val binding: TyphoonListFragmentBinding by viewBinding()
    private val repositoryOld: TyphoonRepositoryOld by lazy {
        // TODO: DI化したい
        val databaseReference = FirebaseDatabase.getInstance().reference.ref.child("typhoon/tenkijp")
        TyphoonRepositoryOld(databaseReference)
    }
    private val viewModel = TyphoonListViewModel(repositoryOld)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = TyphoonRecyclerViewAdapter(mListener = this)
        fetchTyphoon()
    }

    /**
     * 台風を取得
     */
    private fun fetchTyphoon() {
        lifecycleScope.launchWhenCreated {
            viewModel.getTyphoonList().collect { state ->
                Timber.d("TyphoonUiState.Success: $state")
                when (state) {
                    is TyphoonUiState.Success -> {
                        bindTyphoon(state.typhoonList)
                    }
                    is TyphoonUiState.Error -> {
                        Timber.e(state.message)
                    }
                }
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

    override fun onListFragmentInteraction(item: Typhoon?) {
        item ?: return
        Timber.d(item.toString())

        val uiModel: TyphoonDetailUiModel = item.toTyphoonUiModel()

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

interface OnTyphoonDetailFragmentInteractionListener {
    fun onListFragmentInteraction(item: Typhoon?)
}
