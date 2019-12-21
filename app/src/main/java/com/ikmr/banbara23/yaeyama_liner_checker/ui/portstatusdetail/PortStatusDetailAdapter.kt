package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.StatusHelper
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.Row
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable
import kotlinx.android.synthetic.main.time_table_row.view.left_status
import kotlinx.android.synthetic.main.time_table_row.view.left_time
import kotlinx.android.synthetic.main.time_table_row.view.right_status
import kotlinx.android.synthetic.main.time_table_row.view.right_time

class PortStatusDetailAdapter(
    lifecycleOwner: LifecycleOwner,
    timetable: MutableLiveData<TimeTable>
) : RecyclerView.Adapter<PortStatusDetailAdapter.ViewHolder>() {
    private val items = mutableListOf<Row>()

    init {
        timetable.observe(lifecycleOwner, Observer {
            it ?: return@Observer
            replaceAll(it.row)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.time_table_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.leftTime.text = item.left.time
        holder.leftStatus.text = item.left.status.text
        holder.leftStatus.setTextColor(StatusHelper.getStatusColor(item.left.status.code))

        holder.rightTime.text = item.right.time
        holder.rightStatus.text = item.right.status.text
        holder.rightStatus.setTextColor(StatusHelper.getStatusColor(item.right.status.code))

        with(holder.view) {
            tag = item
        }
    }

    private fun replaceAll(newItems: List<Row>) {
        items.apply {
            clear()
            addAll(newItems)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val leftTime: TextView = view.left_time
        val leftStatus: TextView = view.left_status
        val rightTime: TextView = view.right_time
        val rightStatus: TextView = view.right_status
    }
}
