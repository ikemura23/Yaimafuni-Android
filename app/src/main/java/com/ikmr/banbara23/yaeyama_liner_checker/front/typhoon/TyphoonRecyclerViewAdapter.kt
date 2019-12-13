package com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonListItemBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import com.squareup.picasso.Picasso

class TyphoonRecyclerViewAdapter(
    private var mValues: List<Typhoon>,
    private val mListener: OnTyphoonDetailFragmentInteractionListener?
) : RecyclerView.Adapter<TyphoonRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Typhoon
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TyphoonListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.typhoon_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val typhoon = mValues[position]
        holder.binding.typhoon = typhoon

        Picasso.get().load(typhoon.img).into(holder.binding.image)

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

    inner class ViewHolder(val binding: TyphoonListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
