package com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.lotteryproject.R

/**
created by AnthonyH
createDate: 2020/9/22 0022
 */
class JiBenZouShiHeaderAdapter : RecyclerView.Adapter<JiBenZouShiHeaderViewHolder>() {

    private val count: Int = 35 + 12

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JiBenZouShiHeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hor_header, null)
        return JiBenZouShiHeaderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return count
    }

    override fun onBindViewHolder(holder: JiBenZouShiHeaderViewHolder, position: Int) {
        //前后闭区间 0-46 //35=46
        if (position in 0..34) {
            holder?.run {
                textView?.text = (position + 1).toString()
            }
        } else {
            holder?.run {
                textView?.text = (position - 34).toString()
            }
        }
    }
}

class JiBenZouShiHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //    val textView: TextView = itemView?.findViewById(R.id.header_item_tv)
    val textView: TextView = itemView as TextView
}