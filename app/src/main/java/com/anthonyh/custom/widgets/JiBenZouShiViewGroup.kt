package com.anthonyh.custom.widgets

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.lotteryproject.*
import com.anthonyh.lotteryproject.common.commoninterface.CustomTable
import com.anthonyh.lotteryproject.common.commoninterface.DataSource
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter.JiBenZouShiHeaderAdapter
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.adapter.JiBenZouShiVerAdapter
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.JiBenZouShiBeanLiveBean

/**
created by AnthonyH
createDate: 2020/9/21 0021
 */
class JiBenZouShiViewGroup(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs),
    CustomTable<JiBenZouShiBeanLiveBean> {
    private val TAG = "JiBenZouShiViewGroup"

    private var presenter: IHorScollPresenter =
        HorScrollPresenter()

    private var contentAdapter: JiBenZouShiVerAdapter? = null

    //数据源
    private var dataSource: DataSource? = null

    private fun initView() {
        val viewGroup = LayoutInflater.from(context).inflate(R.layout.jibenzoushi_sub_layout_old, null)
        viewGroup?.run {
            if (this is ViewGroup) {
                viewGroup.findViewById<View>(R.id.sub_header_linealayout)?.apply {
                    if (this is LinearLayout) {
                        this@run.removeView(this)
                        this@JiBenZouShiViewGroup.addView(this)
                    }
                }
                viewGroup.findViewById<View>(R.id.sub_recycleview)?.apply {
                    if (this is RecyclerView) {
                        this@run.removeView(this)
                        this@JiBenZouShiViewGroup.addView(this)
                    }
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e(TAG, "onMeasure: ")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e(TAG, "onLayout: $childCount")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.e(TAG, "onDraw: ")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.e(TAG, "onSizeChanged: ")
        super.onSizeChanged(w, h, oldw, oldh)
        initSubView()
    }


    private fun initSubView() {
        if (childCount != 2) return
        //初始化头部列表
        val linearLayout = getChildAt(0)
        linearLayout?.run {
            val headerRecyclerView = findViewById<AfuHorRecyclerView>(R.id.header_recyclerview)
            headerRecyclerView?.run {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
                init(presenter)
                adapter = JiBenZouShiHeaderAdapter()
            }
        }

        //初始化内容列表
        val recyclerView = getChildAt(1)
        recyclerView?.run {
            if (this is RecyclerView) {
                layoutManager = LinearLayoutManager(context)
                contentAdapter = JiBenZouShiVerAdapter(presenter)
                adapter = contentAdapter
                dataSource?.requestData()//先加载数据
                //添加滚动监听，如果滚动到最后一条数据，自动加载新的数据
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (computeVerticalScrollExtent() + computeVerticalScrollOffset() >= computeVerticalScrollRange()) {
                            Log.e(TAG, "onScrolled: 滑动到底部")
                            dataSource?.requestData()
                        }
                    }
                })

            }
        }

    }

    /**
     *从xml中加载完毕，但如果不是从xml中添加的一个ViewGroup呢？
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    override fun refresh(data: JiBenZouShiBeanLiveBean) {
        contentAdapter?.refresh(data)
    }

    override fun setDataSource(dataSource: DataSource) {
        this.dataSource = dataSource
    }
}



