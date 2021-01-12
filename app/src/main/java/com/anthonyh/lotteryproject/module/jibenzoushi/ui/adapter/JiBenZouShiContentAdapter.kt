package com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.custom.widgets.LotterTextView
import com.anthonyh.lotteryproject.R
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.JiBenZouShiItemBean

/**
@author HAnthony
@date 2020/12/16 0016
@description
 */
class JiBenZouShiContentAdapter(
    private var itemList: ArrayList<JiBenZouShiItemBean> = ArrayList()
) : RecyclerView.Adapter<ViewHolder>() {

    private val TAG = "JiBenZouShiContentAdapt"

    fun refresh(isFinish: Boolean, list: List<JiBenZouShiItemBean>?) {
        list?.run {
//            itemList = list
            val startPos = itemList.size * 47
            itemList.addAll(list)
            notifyItemRangeChanged(startPos, list.size * 47)
        }
    }

    fun getLastIndex(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_number, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size * 47
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            val jibenItemBean = itemList[(position) / 47]
            var index = (position) % 47
            when (index) {
                in 0..34 -> {//红球
                    //0-34
                    val redSigBean = jibenItemBean.redArray[index]
                    if (redSigBean.seleted) {
                        textView.apply {
                            isBackHightLight = true
                            backHightLightColor =
                                context!!.getColor(R.color.jibenzoushi_red_select_back)
                            text = (index + 1).toString()
                        }
                    } else {
                        textView.apply {
                            isBackHightLight = false
                            text = redSigBean.missingCount.toString()
                        }
                    }
                }
                in 35..46 -> {//蓝球 1-11
                    val blueSigBean = jibenItemBean.blueArray[index - 35]
                    if (blueSigBean.seleted) {
                        textView.apply {
                            isBackHightLight = true
                            backHightLightColor = Color.BLUE
                            text = (index - 35 + 1).toString()
                        }
                    } else {
                        textView?.apply {
                            isBackHightLight = false
                            text = blueSigBean.missingCount.toString()
                        }
                    }
                }
                else -> {
                    Log.e(TAG, "onBindViewHolder: error$index")
                }
            }
        }
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //    val textView: LotterTextView = itemView?.findViewById(R.id.hor_tv)
    val textView: LotterTextView = itemView as LotterTextView
}