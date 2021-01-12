package com.anthonyh.custom.widgets

import com.anthonyh.lotteryproject.common.util.AfuObservable
import java.util.*

/**
created by AnthonyH
createDate: 2020/9/20 0020
主要用来控制横向多个RecycleView的同步滚动
 */
class HorScrollPresenter(var observable: AfuObservable? = AfuObservable()) :
    IHorScollPresenter {
    private val TAG = "HorScrollPresenter"

    /**
     *记录当前横向滚动的总距离，主要用来同步刚刚出现在屏幕中的一些条目的位置
     */
    var mNowXScorll = 0

    override fun addObserver(observer: Observer) {
        observable?.addObserver(observer)
    }

    override fun deleteObserver(observer: Observer) {
        observable?.deleteObserver(observer)
    }

    override fun notifyObserver(observer: Observer) {
        observable?.apply {
            notifyObserver(mNowXScorll, observer)
        }
    }

    override fun injectData(xScroll: Int) {
        mNowXScorll = xScroll
        observable?.run {
            notifyObservers(mNowXScorll)
        }
    }

    override fun destroy() {
        observable?.deleteObservers()
    }

}

interface IHorScollPresenter {
    fun addObserver(observer: Observer)
    fun deleteObserver(observer: Observer)

    /**
     * 立即用保存的主要数据刷新指定监听者
     */
    fun notifyObserver(observer: Observer)

    /**
     * 更改主要数据，更改后，刷新全部监听者
     */
    fun injectData(xScroll: Int)
    fun destroy()
}