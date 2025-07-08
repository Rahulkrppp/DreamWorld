package com.app.dreamworld.ui.booking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.EventBookingList
import com.app.dreamworld.data.remote.res.TicketBookingDetailsReq
import com.app.dreamworld.databinding.ItemBookinScanBinding
import com.app.dreamworld.util.clickWithDebounce


class TicketScanBookingAapter(var context: Context, var list: ArrayList<TicketBookingDetailsReq>,
                              var clickListener: (view: View, model: TicketBookingDetailsReq, position: Int) -> Unit = { _: View, _: TicketBookingDetailsReq, _: Int -> }) : RecyclerView.Adapter<TicketScanBookingAapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBookinScanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val model=list[holder.adapterPosition]
        holder.itemBinding.apply {

            tvTicketCode.text = model.booking_qr_number
            cbSelectCoupon.isChecked = model.selected
            tvMemberShipName.text=model.show_title
            tvValidDate.text=model.event_date
            if (model.scannedAt?.isNotEmpty() == true){
                llScannedTime.isVisible=true
                tvScannedTime.text=model.scannedAt
            }


            if (model.isScanned=="1"){
                cbSelectCoupon.isEnabled=false
            }else{
                cbSelectCoupon.isEnabled=true
            }
            cbSelectCoupon.clickWithDebounce {
                model.selected=false
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(val itemBinding: ItemBookinScanBinding) : RecyclerView.ViewHolder(itemBinding.root)
}