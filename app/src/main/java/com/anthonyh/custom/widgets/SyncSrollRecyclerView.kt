package com.anthonyh.custom.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
@author HAnthony
@date 2020/12/16 0016
@description
 */
class SyncSrollRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs),
    Observer {
    var TAG: String = "default"
    lateinit var syncControlScorll: SyncControlScorll
    lateinit var mode: SyncMode

    private var xScrolled = 0
    private var yScrolled = 0

    fun initVar(syncControlScorll: SyncControlScorll, mode: SyncMode) {
        this.mode = mode
        if (mode != SyncMode.NONE) {
            this.syncControlScorll = syncControlScorll
            registListener()
        }
    }

    fun registListener() {
        syncControlScorll?.apply {
            val size = observeble.observers.size
            Log.e(TAG, "$size,registListener: $TAG")
            observeble.addObserver(this@SyncSrollRecyclerView)
        }
    }

    fun unregistListener() {
        syncControlScorll?.apply {
            observeble.deleteObserver(this@SyncSrollRecyclerView)
        }
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        if (mode == SyncMode.NONE) return
        Log.e(TAG, "onScrolled: $dx,$dy")
        if (dx != 0) {
            this.xScrolled += dx
        }
        if (dy != 0) {
            this.yScrolled += dy
        }

        Log.e(TAG, "onScrolled:开始分发： " + syncControlScorll?.observeble.observers.size)
        syncControlScorll?.observeble.notifyObservers(ScorllBean(xScrolled, yScrolled))
    }


    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (mode == SyncMode.NONE) return super.onTouchEvent(e)
        when (e?.action) {
            //刚刚接触到时，取取消对外部的滑动监听
            MotionEvent.ACTION_DOWN -> {
                unregistListener()
            }
            //move事件的时候不做任何更改
            MotionEvent.ACTION_MOVE -> {
            }
            //其他事件，注册监听其他列表的滚动
            else -> {
                registListener()
            }
        }
        return super.onTouchEvent(e)
    }

    override fun update(o: Observable?, scorllBean: Any?) {
        Log.e(TAG, "update: " + scorllBean.toString())
        scorllBean?.apply {
            if (this is ScorllBean) {
                when (mode) {
                    SyncMode.HOR -> {
                        val dx = scrollX - xScrolled
                        if (dx != 0) {
                            scrollBy(dx, 0)
                        }
                    }
                    SyncMode.VER -> {
                        val dy = scrollY - yScrolled
                        if (dy != 0) {
                            scrollBy(0, dy)
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

}

/**
 * 滚动的模式
 */
enum class SyncMode {
    HOR,
    VER,
    NONE
}