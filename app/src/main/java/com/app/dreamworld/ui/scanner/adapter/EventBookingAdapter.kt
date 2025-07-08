package com.app.dreamworld.ui.scanner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.EventBookingList
import com.app.dreamworld.databinding.ItemEventListBinding
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.extension.SERVER_FORMAT
import com.app.dreamworld.util.extension.displayDate
import java.text.SimpleDateFormat
import java.util.Locale


class EventBookingAdapter (var context: Context, var list: ArrayList<EventBookingList>,
                           var clickListener: (view: View, model: EventBookingList, position: Int) -> Unit = { _: View, _: EventBookingList, _: Int -> }) : RecyclerView.Adapter<EventBookingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemEventListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item=list[holder.adapterPosition]
        holder.itemBinding.apply {
            tvEventName.text=item.eventTitle
            tvCustomerCount.text=(item.bookedSeats + " "+context.getString(R.string.registered_customers))
            tvDate.text=item.eventDate?.displayDate(SERVER_FORMAT, SimpleDateFormat("dd"))
            tvMonth.text=item.eventDate?.displayDate(SERVER_FORMAT, SimpleDateFormat("MMM"))
            tvDay.text=item.eventDate?.displayDate(SERVER_FORMAT, SimpleDateFormat("EEE"))
            clMainBooking.clickWithDebounce {
                clickListener(clMainBooking,item,holder.adapterPosition)
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(val itemBinding: ItemEventListBinding) : RecyclerView.ViewHolder(itemBinding.root)
}