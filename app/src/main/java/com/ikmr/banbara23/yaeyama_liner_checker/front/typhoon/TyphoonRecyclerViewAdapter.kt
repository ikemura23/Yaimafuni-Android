package com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon.TyphoonDetailFragment.OnListFragmentInteractionListener
import com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon.dummy.DummyContent.DummyItem
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import kotlinx.android.synthetic.main.typhoon_detail_item.view.area
import kotlinx.android.synthetic.main.typhoon_detail_item.view.datetime
import kotlinx.android.synthetic.main.typhoon_detail_item.view.image
import kotlinx.android.synthetic.main.typhoon_detail_item.view.intensity
import kotlinx.android.synthetic.main.typhoon_detail_item.view.maxWindSpeed
import kotlinx.android.synthetic.main.typhoon_detail_item.view.name
import kotlinx.android.synthetic.main.typhoon_detail_item.view.pressure
import kotlinx.android.synthetic.main.typhoon_detail_item.view.scale

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class TyphoonRecyclerViewAdapter(
    private var mValues: List<Typhoon>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TyphoonRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.typhoon_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val typhoon = mValues[position]

        holder.apply {
            dateTime.text = typhoon.dateTime
            name.text = typhoon.name
            area.text = typhoon.area
            scale.text = typhoon.scale
            intensity.text = typhoon.intensity
            pressure.text = typhoon.pressure
            maxWindSpeed.text = typhoon.maxWindSpeedNearCenter
        }

        with(holder.mView) {
            tag = typhoon
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun updateData(typhoonList: List<Typhoon>) {
//        mValues = listOf()
        mValues = typhoonList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val image: ImageView = mView.image
        val dateTime: TextView = mView.datetime
        val name: TextView = mView.name
        val area: TextView = mView.area
        val scale: TextView = mView.scale
        val intensity: TextView = mView.intensity
        val pressure: TextView = mView.pressure
        val maxWindSpeed: TextView = mView.maxWindSpeed
    }
}
