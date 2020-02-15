package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus
import kotlinx.android.synthetic.main.port_list_item.view.description
import kotlinx.android.synthetic.main.port_list_item.view.port_name
import kotlinx.android.synthetic.main.port_list_item.view.status

class PortListAdapter(
    lifecycleOwner: LifecycleOwner,
    portStatuses: MutableLiveData<List<PortStatus>>,
    private val listener: PortListFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<PortListAdapter.ViewHolder>() {

    private val items = mutableListOf<PortStatus>()

    init {
        portStatuses.observe(lifecycleOwner, Observer {
            it ?: return@Observer
            replaceAll(it)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.port_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.portName.text = item.portName
        holder.description.text = item.comment
        holder.status.text = item.status.text

        with(holder.view) {
            tag = item
            setOnClickListener { listener?.onListFragmentInteraction(item) }
        }
    }

    private fun replaceAll(newItems: List<PortStatus>) {
        items.apply {
            clear()
            addAll(newItems)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val portName: TextView = view.port_name
        val description: TextView = view.description
        val status: TextView = view.status

        override fun toString(): String {
            return super.toString() + " '" + portName.text + "'" + " '" + description.text + "'" + " '" + status.text + "'"
        }
    }
}
