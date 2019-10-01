package com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonDetailItemBinding
import com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon.TyphoonDetailFragment.OnListFragmentInteractionListener
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import com.squareup.picasso.Picasso

class TyphoonRecyclerViewAdapter(
    private var mValues: List<Typhoon>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TyphoonRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Typhoon
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TyphoonDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.typhoon_detail_item,
            parent,
            false
        )
//        val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.typhoon_detail_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val typhoon = mValues[position]
        holder.binding.typhoon = typhoon

        holder.binding.apply {
            Picasso.get().load(typhoon.img).into(image)
//            datetime.text = typhoon.dateTime
//            name.text = typhoon.name
//            area.text = typhoon.area
//            scale.text = typhoon.scale
//            intensity.text = typhoon.intensity
//            pressure.text = typhoon.pressure
//            maxWindSpeed.text = typhoon.maxWindSpeedNearCenter
        }


        with(holder.binding.root) {
            tag = typhoon
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun updateData(typhoonList: List<Typhoon>) {
        mValues = typhoonList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: TyphoonDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {

//        val image: ImageView = mView.image
//        val dateTime: TextView = mView.datetime
//        val name: TextView = mView.name
//        val area: TextView = mView.area
//        val scale: TextView = mView.scale
//        val intensity: TextView = mView.intensity
//        val pressure: TextView = mView.pressure
//        val maxWindSpeed: TextView = mView.maxWindSpeed
    }
}
