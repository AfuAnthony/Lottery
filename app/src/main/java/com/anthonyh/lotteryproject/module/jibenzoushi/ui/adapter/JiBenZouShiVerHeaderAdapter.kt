package com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.custom.widgets.LotterTextView
import com.anthonyh.lotteryproject.R

/**
@author HAnthony
@date 2020/12/16 0016
@description
 */

class JiBenZouShiVerHeaderAdapter(private var mIdList: List<String> = ArrayList()) :
    RecyclerView.Adapter<VerHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerHeaderViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_ver_header, null)
        return VerHeaderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mIdList.size
    }

    override fun onBindViewHolder(holder: VerHeaderViewHolder, position: Int) {
        holder.apply {
            textView.text = mIdList[position]
        }
    }

    fun refresh(idList: List<String>?) {
        idList?.run {
            mIdList = idList
            notifyDataSetChanged()
        }
    }

}

class VerHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView = view?.findViewById<TextView>(R.id.item_head_tv)
}