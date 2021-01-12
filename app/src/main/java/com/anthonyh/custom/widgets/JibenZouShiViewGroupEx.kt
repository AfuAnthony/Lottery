package com.anthonyh.custom.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.lotteryproject.R
import com.anthonyh.lotteryproject.common.commoninterface.CustomTable
import com.anthonyh.lotteryproject.common.commoninterface.DataSource
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter.JiBenZouShiContentAdapter
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter.JiBenZouShiHeaderAdapter
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter.JiBenZouShiVerHeaderAdapter
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.JiBenZouShiBeanLiveBean

/**
@author HAnthony
@date 2020/12/16 0016
@description
 */
class JibenZouShiViewGroupEx(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs),
    CustomTable<JiBenZouShiBeanLiveBean> {

    private companion object {
        const val TAG = "JibenZouShiViewGroupEx"
    }

    private val mContentAdapter = JiBenZouShiContentAdapter()
    private val mVerHeaderAdapter = JiBenZouShiVerHeaderAdapter()

    //数据源
    private var mDataSource: DataSource? = null

    private val mSyncControlScorll = SyncControlScorll()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e(TAG, "onMeasure: ")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        Log.e(TAG, "onFinishInflate: ")
        addChildViewContent()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.e(TAG, "onSizeChanged: ")
        val contentView = getChildAt(0)
        contentView?.run {
            initHorHeader(contentView)
            initVerHeader(contentView)
            initContentView(contentView)
            mDataSource?.requestData(0)//init data
        }
    }

    private fun addChildViewContent() {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.jibenzoushi_viewgroup, null)
        addView(contentView)
    }

    private fun initContentView(contentView: View) {
        val recyclerView =
            contentView.findViewById<SyncSrollRecyclerView>(R.id.content_recyclerview)
        recyclerView?.run {
            setItemViewCacheSize(20);
            initVar(mSyncControlScorll, SyncMode.VER)
            //todo，硬编码
            layoutManager = GridLayoutManager(context, 47)
            adapter = mContentAdapter
            addOnScrollListener(mScorllListener)
        }
    }

    private val mScorllListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
                Log.e(TAG, "onScrolled: 滑动到底部")
                mDataSource?.requestData(mContentAdapter.getLastIndex())
            }
        }
    }


    private fun initVerHeader(contentView: View) {
        val recyclerView =
            contentView.findViewById<SyncSrollRecyclerView>(R.id.ver_header_recyclerview)
        recyclerView?.run {
            initVar(mSyncControlScorll, SyncMode.VER)
            layoutManager = LinearLayoutManager(context)
            adapter = mVerHeaderAdapter
            addOnScrollListener(mScorllListener)
        }
    }

    private fun initHorHeader(contentView: View) {
        val horHeaderRecyclerView =
            contentView.findViewById<RecyclerView>(R.id.hor_header_recyclerview)
        horHeaderRecyclerView?.run {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = JiBenZouShiHeaderAdapter()
        }
    }


    override fun setDataSource(dataSource: DataSource) {
        this.mDataSource = dataSource
    }

    override fun refresh(data: JiBenZouShiBeanLiveBean) {
        mContentAdapter.refresh(data.isLast, data.contentList)
        mVerHeaderAdapter.refresh(data.idList)
    }

}