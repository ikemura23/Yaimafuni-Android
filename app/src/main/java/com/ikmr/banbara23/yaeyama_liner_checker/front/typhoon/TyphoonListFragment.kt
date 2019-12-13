package com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber

class TyphoonDetailFragment : Fragment(), OnTyphoonDetailFragmentInteractionListener {

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()

    private lateinit var binding: TyphoonListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.typhoon_list_fragment, container, false)
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { activity?.finish() }
        binding.list.adapter = TyphoonRecyclerViewAdapter(listOf(), this)
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
        CustomTabUtil.start(activity, getString(R.string.typhoon_open_url))
    }
}

interface OnTyphoonDetailFragmentInteractionListener {
    fun onListFragmentInteraction(item: Typhoon?)
}
