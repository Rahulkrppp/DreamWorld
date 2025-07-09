package com.app.dreamworld.ui.member

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dreamworld.data.remote.res.CustomerDetailsRes
import com.app.dreamworld.databinding.ItemMemberListBinding
import com.app.dreamworld.util.clickWithDebounce

class MemberListAdapter(
    var context: Context, var list: ArrayList<CustomerDetailsRes>,
    var clickListener: (view: View, model: CustomerDetailsRes, position: Int) -> Unit = { _: View, _: CustomerDetailsRes, _: Int -> }
) : RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMemberListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = list[holder.adapterPosition]
        holder.itemBinding.apply {
            tvCustomerName.text=item.customerName
            tvCustomerMobileNumber.text=item.customerMobile
            tvCustomerEmail.text= (item.showName+"\n" + item.availabeSeats)
            val firstLetter = item.customerName?.firstOrNull()?.uppercaseChar()
            ivCustomerProfile.text=firstLetter.toString()
            ivShare.clickWithDebounce {
                clickListener(ivShare,item,holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val itemBinding: ItemMemberListBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}