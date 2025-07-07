package com.app.dreamworld.ui.dashboard.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.dreamworld.BuildConfig
import com.app.dreamworld.data.remote.res.Event
import com.app.dreamworld.databinding.RowEventBinding
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.loadImage
import com.app.dreamworld.util.loadRoundedCornerCircleImage

class EventAdapter (var context: Context, var list: ArrayList<Event>,
                    var clickListener: (view: View, model: Event, position: Int) -> Unit = { _: View, _: Event, _: Int -> }) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item=list[holder.adapterPosition]
        holder.itemBinding.apply {
            imgEvent.loadRoundedCornerCircleImage(context, BuildConfig.BASE_URL+item.image, catchImage = true)
            tvEventTitle.text=item.title
            tvPersonCount.text=item.personCapacity
            clMain.clickWithDebounce {
                clickListener(clMain,item,holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(val itemBinding: RowEventBinding) : RecyclerView.ViewHolder(itemBinding.root)
}