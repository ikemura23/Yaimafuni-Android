package com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon.detail.TyphoonDetailActivity
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber

class TyphoonListFragment : androidx.fragment.app.Fragment(),
    OnTyphoonDetailFragmentInteractionListener {

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()

    private lateinit var binding: TyphoonListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.typhoon_list_fragment, container, false)
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { activity?.finish() }
        binding.list.adapter =
            TyphoonRecyclerViewAdapter(
                listOf(),
                this
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val intent = Intent(context, TyphoonDetailActivity::class.java).apply {
            putExtra(Constants.BUNDLE_KEY_DETAIL, item)
        }
        requireActivity().startActivity(intent)
    }
}

interface OnTyphoonDetailFragmentInteractionListener {
    fun onListFragmentInteraction(item: Typhoon?)
}
