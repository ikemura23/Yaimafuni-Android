package com.ikmr.banbara23.yaeyama_liner_checker.front.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.WeatherViewRowBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.Table

/**
 * 天気詳細 横スクロールできる時間ごと天気
 */
class WeatherTimeListAdaptor : RecyclerView.Adapter<WeatherTimeListAdaptor.ViewHolder>() {

    private var tableItems: List<Table> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: WeatherViewRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.weather_view_row,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val table = tableItems[position]
        holder.binding.table = table
        holder.binding.root.tag = table
    }

    override fun getItemCount(): Int {
        return tableItems.size
    }

    fun update(latestItems: List<Table>) {
        tableItems = latestItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: WeatherViewRowBinding) :
        RecyclerView.ViewHolder(binding.root)
}
