package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import timber.log.Timber

class TyphoonListFragment : Fragment(R.layout.typhoon_list_fragment),
    OnTyphoonDetailFragmentInteractionListener {

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()
    private val binding: TyphoonListFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter =
            TyphoonRecyclerViewAdapter(
                listOf(),
                this
            )
        fetchTyphoon()
    }

    /**
     * 台風を取得
     */
    private fun fetchTyphoon() {
        disposable.add(
            apiClient
                .typhoon
                .subscribeWith(
                    object : ResourceSubscriber<List<Typhoon>>() {
                        override fun onNext(typhoonList: List<Typhoon>) {
                            Log.d("fetchTyphoon", typhoonList.toString())
                            bindTyphoon(typhoonList)
                        }

                        override fun onError(t: Throwable) {
                            Log.d("fetchTyphoon", t.toString())
                        }

                        override fun onComplete() {
                        }
                    })
        )
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

    override fun onDetach() {
        super.onDetach()
        disposable.dispose()
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
