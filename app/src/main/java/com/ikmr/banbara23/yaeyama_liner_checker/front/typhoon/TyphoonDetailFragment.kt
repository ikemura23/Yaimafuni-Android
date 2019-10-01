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
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonDetailListBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber

class TyphoonDetailFragment : Fragment(), OnTyphoonDetailFragmentInteractionListener {

    // TODO: Customize parameters
    private var columnCount = 1

//    private var listener: OnTyphoonDetailFragmentInteractionListener? = null

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()

    private lateinit var binding: TyphoonDetailListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.typhoon_detail_list, container, false)
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
        val adapter = binding.list.adapter as TyphoonRecyclerViewAdapter
        adapter.updateData(typhoonList)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnTyphoonDetailFragmentInteractionListener) {
//            listener = context
//        } else {
//            // throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
//        }
//    }

    override fun onDetach() {
        super.onDetach()
//        listener = null
        disposable.dispose()
    }

    override fun onListFragmentInteraction(item: Typhoon?) {
        CustomTabUtil.start(activity, getString(R.string.typhoon_open_url))
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                TyphoonDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}

interface OnTyphoonDetailFragmentInteractionListener {
    fun onListFragmentInteraction(item: Typhoon?)
}
