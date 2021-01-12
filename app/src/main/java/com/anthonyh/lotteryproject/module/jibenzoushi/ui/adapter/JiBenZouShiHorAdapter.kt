package com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.custom.widgets.LotterTextView
import com.anthonyh.lotteryproject.R
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.SigBean

/**
created by AnthonyH
createDate: 2020/9/19 0019
 */
class JiBenZouShiHorAdapter(
    private val redArray: Array<SigBean>,
    private val blueArray: Array<SigBean>
) :
    RecyclerView.Adapter<JiBenZouShiHorHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JiBenZouShiHorHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_number, null)
        return JiBenZouShiHorHolder(view)
    }

    override fun getItemCount(): Int {
        return redArray.size + blueArray.size
    }

    override fun onBindViewHolder(holder: JiBenZouShiHorHolder, position: Int) {
        holder?.apply {
            //红球
            if (position >= 0 && position <= redArray.size - 1) {
                val sigBean = redArray[position]
                when (sigBean.seleted) {
                    true -> {
                        textView.apply {
                            isBackHightLight = true
                            backHightLightColor =
                                context!!.getColor(R.color.jibenzoushi_red_select_back)
                            text = (position + 1).toString()
                        }
                    }
                    else -> {
                        textView.apply {
                            isBackHightLight = false
                            text = sigBean.missingCount.toString()
                        }
                    }
                }
                //蓝球
            } else if (position >= redArray.size && position <= itemCount - 1) {//
                val sigBean = blueArray[position - redArray.size]//数组下标0-11;list position35-46
                when (sigBean.seleted) {
                    true -> {
                        textView.apply {
                            isBackHightLight = true
                            backHightLightColor = Color.BLUE
                            text = (position - redArray.size + 1).toString()
                        }
                    }
                    else -> {
                        textView?.apply {
                            isBackHightLight = false
                            text = sigBean.missingCount.toString()
                        }
                    }
                }
            } else {
                //error
            }

        }
    }
}


class JiBenZouShiHorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: LotterTextView = itemView?.findViewById(R.id.hor_tv)
}