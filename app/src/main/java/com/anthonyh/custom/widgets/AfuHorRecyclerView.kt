package com.anthonyh.custom.widgets

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
created by AnthonyH
createDate: 2020/9/20 0020
 */
@RequiresApi(Build.VERSION_CODES.M)
class AfuHorRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs),
    Observer {
    private val TAG = "AfuHorRecyclerView"
    private var presenter: IHorScollPresenter? = null
    private var xScroll = 0
    fun init(presenter: IHorScollPresenter) {
        this.presenter = presenter
        registObserver()
        clearState()
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        if (dx != 0) {
            xScroll += dx
            presenter?.injectData(xScroll)
        }
    }


    override fun onTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            //刚刚接触到时，取取消对外部的滑动监听
            MotionEvent.ACTION_DOWN -> {
                unRegistObserver()
            }
            //move事件的时候不做任何更改
            MotionEvent.ACTION_MOVE -> {
            }
            //其他事件，注册监听其他列表的滚动
            else -> {
                registObserver()
            }
        }
        return super.onTouchEvent(e)
    }

    private fun unRegistObserver() {
        presenter?.deleteObserver(this)
    }

    private fun registObserver() {
        presenter?.addObserver(this)
    }

    override fun update(o: Observable?, xScroll: Any?) {
        if (xScroll is Int) {
            val delTa = xScroll - this.xScroll
            if (delTa != 0) {
                scrollBy(delTa, 0)
            } else {
            }
        }
    }
    private fun clearState() {
        xScroll = 0
    }



}

