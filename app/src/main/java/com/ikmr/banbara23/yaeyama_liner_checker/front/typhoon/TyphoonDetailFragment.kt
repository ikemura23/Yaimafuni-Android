package com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon.dummy.DummyContent.DummyItem
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import kotlinx.android.synthetic.main.typhoon_detail_list.list

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [TyphoonDetailFragment.OnListFragmentInteractionListener] interface.
 */
class TyphoonDetailFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()

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
        val view = inflater.inflate(R.layout.typhoon_detail_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                adapter = TyphoonRecyclerViewAdapter(listOf(), listener)
            }
        }
        return view
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
        val adapter = list.adapter as TyphoonRecyclerViewAdapter
        adapter.updateData(typhoonList)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            // throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                TyphoonDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
