package com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.custom.widgets.AfuHorRecyclerView
import com.anthonyh.custom.widgets.IHorScollPresenter
import com.anthonyh.lotteryproject.R
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.JiBenZouShiBeanLiveBean
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.JiBenZouShiItemBean

/**
created by AnthonyH
createDate: 2020/9/19 0019
 */
class JiBenZouShiVerAdapter(private var iHorScollPresenter: IHorScollPresenter) :
    RecyclerView.Adapter<VerHolder>() {
    private val TAG = "VerAdapter"
    private var itemList: List<JiBenZouShiItemBean>? = ArrayList()

    /**
     * 标记是否已经加载全部的数据，主要用来判断在底部显示“正在加载”还是“没有更多了”
     */
    private var mAllDataLoaded: Boolean = false

    fun refresh(data: JiBenZouShiBeanLiveBean) {
        data?.run {
            mAllDataLoaded = data.isLast
            itemList = contentList!!
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_old, null)
        return VerHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    override fun onBindViewHolder(holder: VerHolder, position: Int) {
        val itemBean = itemList?.get(position)
        holder?.run {
            textView?.text = itemBean?.itemId.toString()
            recyclerView?.run {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layoutManager =
                        LinearLayoutManager(context)?.apply {
                            orientation = LinearLayoutManager.HORIZONTAL
                        }
                    init(iHorScollPresenter)
                    adapter =
                        JiBenZouShiHorAdapter(itemBean!!.redArray, itemBean.blueArray)
                    //更新数据后，滚动到当前共有的水平位置
                    post { iHorScollPresenter.notifyObserver(this) }
                }
            }
        }
    }
}

class VerHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    var textView: TextView? = itemView?.findViewById(R.id.item_head_tv)
    var recyclerView: AfuHorRecyclerView? = itemView?.findViewById(R.id.item_hor_recycleview)


}