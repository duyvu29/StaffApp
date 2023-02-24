package com.example.staffapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_staff.view.*

class StaffAdapter : RecyclerView.Adapter<StaffAdapter.ItemHolder>  {
    companion object {
        var clickListenerDelete: ClickListenerDelete? = null
        var clickListenerUpdate: ClickListenerUpdate? = null
    }

    interface ClickListenerDelete {
        fun onItemClickDelete(position: Int, v: View)
    }

    interface ClickListenerUpdate {
        fun onItemClickUpdate(position: Int, v: View)
    }

    fun setOnItemUpdateListener(clickListenerUpdate: ClickListenerUpdate?) {
        StaffAdapter.clickListenerUpdate = clickListenerUpdate!!
    }

    fun setOnItemDeleteListener(clickListenerDelete: ClickListenerDelete?) {
        StaffAdapter.clickListenerDelete = clickListenerDelete!!
    }

    var context: Context
    var staffs: ArrayList<Staff>

    constructor(context: Context, list: ArrayList<Staff>) : super() {
        this.context = context
        this.staffs = list
    }

    fun reloadList(list: ArrayList<Staff>) {
        this.staffs = list;
        notifyDataSetChanged()
    }

    class ItemHolder : RecyclerView.ViewHolder {

        var tvFullName: TextView
        var tvAge: TextView
        var tvCountry: TextView
        var imvEdit: ImageView
        var imvDelete: ImageView

        constructor(itemView: View) : super(itemView) {
            tvFullName = itemView.tvFullName
            tvAge = itemView.tvAge
            tvCountry = itemView.tvCountry
            imvEdit = itemView.imvEdit
            imvDelete = itemView.imvDelete

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_staff, parent, false)
        var itemHolder = ItemHolder(v)

        return itemHolder
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = staffs[holder.adapterPosition]
        holder.tvFullName.text = item.fullName
        holder.tvAge.text = "${item.age}"
        holder.tvCountry.text = item.country

        holder.imvEdit.setOnClickListener { v ->
            clickListenerUpdate!!.onItemClickUpdate(holder.adapterPosition, v!!)
        }

        holder.imvDelete.setOnClickListener { v ->
            clickListenerDelete!!.onItemClickDelete(holder.adapterPosition, v!!)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return staffs.size
    }


}