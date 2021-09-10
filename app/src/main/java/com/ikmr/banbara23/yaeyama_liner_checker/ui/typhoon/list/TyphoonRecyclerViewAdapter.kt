package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ikemura.shared.model.tyhoon.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TyphoonListItemBinding
import com.squareup.picasso.Picasso

/**
 * 台風一覧 アダプター
 */
class TyphoonRecyclerViewAdapter(
    val onClick: (Typhoon) -> Unit,
) : RecyclerView.Adapter<TyphoonRecyclerViewAdapter.ViewHolder>() {

    private var typhoonList: List<Typhoon> = listOf()

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
        val typhoon = typhoonList[position]
        holder.binding.typhoon = typhoon

        Picasso.get().load(typhoon.img).into(holder.binding.image)

        with(holder.binding.root) {
            setOnClickListener {
                onClick(typhoon)
            }
        }
    }

    override fun getItemCount(): Int = typhoonList.size

    fun updateData(data: List<Typhoon>) {
        typhoonList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: TyphoonListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
