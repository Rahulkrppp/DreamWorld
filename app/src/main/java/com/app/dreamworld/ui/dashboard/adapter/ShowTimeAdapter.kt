package com.app.dreamworld.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dreamworld.data.remote.res.Event
import com.app.dreamworld.data.remote.res.ShowsDetailsRes
import com.app.dreamworld.databinding.ItemShowListBinding
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.extension.SERVER_INPUT_FORMAT_YYYY_MM_DD_HH_MM_SS
import com.app.dreamworld.util.extension.displayDate
import java.text.SimpleDateFormat

class ShowTimeAdapter (var context: Context, var list: ArrayList<ShowsDetailsRes>,
                       var clickListener: (view: View, model: ShowsDetailsRes, position: Int) -> Unit = { _: View, _: ShowsDetailsRes, _: Int -> }) : RecyclerView.Adapter<ShowTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemShowListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item=list[holder.adapterPosition]
        holder.itemBinding.apply {
            tvTitle.text=item.title
            tvDate.text=item.startTime?.displayDate(SERVER_INPUT_FORMAT_YYYY_MM_DD_HH_MM_SS, SimpleDateFormat("dd"))
            tvMonth.text=item.startTime?.displayDate(SERVER_INPUT_FORMAT_YYYY_MM_DD_HH_MM_SS, SimpleDateFormat("MMM"))
            tvDay.text=item.startTime?.displayDate(SERVER_INPUT_FORMAT_YYYY_MM_DD_HH_MM_SS, SimpleDateFormat("EEE"))
            tvTime.text=item.startTime?.displayDate(SERVER_INPUT_FORMAT_YYYY_MM_DD_HH_MM_SS, SimpleDateFormat("hh:mm a"))
            clMainShow.clickWithDebounce {
                clickListener(clMainShow,item,holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(val itemBinding: ItemShowListBinding) : RecyclerView.ViewHolder(itemBinding.root)
}