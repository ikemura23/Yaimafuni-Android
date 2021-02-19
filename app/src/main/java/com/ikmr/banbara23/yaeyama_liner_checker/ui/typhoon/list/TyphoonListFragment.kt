package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonRepository
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonUiState
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class TyphoonListFragment : Fragment(R.layout.typhoon_list_fragment),
    OnTyphoonDetailFragmentInteractionListener {

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()
    private val binding: TyphoonListFragmentBinding by viewBinding()
    private val repository: TyphoonRepository by lazy {
        // TODO: DI化したい
        val databaseReference = FirebaseDatabase.getInstance().reference.ref.child("typhoon/tenkijp")
        TyphoonRepository(databaseReference)
    }
    private val viewModel = TyphoonListViewModel(repository)

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

    private fun bindTyphoon(typhoonList: List<Typhoon>) {
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
        TyphoonListFragmentDirections.actionTyphoonListFragmentToTyphoonDetailFragment(item).let {
            findNavController().navigate(it)
        }
    }
}

interface OnTyphoonDetailFragmentInteractionListener {
    fun onListFragmentInteraction(item: Typhoon?)
}
