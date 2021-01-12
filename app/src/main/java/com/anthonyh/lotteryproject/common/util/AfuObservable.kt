package com.anthonyh.lotteryproject.common.util

import android.util.Log
import java.util.*

/**
created by AnthonyH
createDate: 2020/9/20 0020
 */
class AfuObservable(val observers: Vector<Observer> = Vector()) {
    private val TAG = "AfuObserveble"
    fun addObserver(observer: Observer) {
        observers?.apply {
            if (!contains(observer)) {
                addElement(observer)
                Log.e(TAG, "addObserver: " + observers.size)
            } else {
                Log.e(TAG, "addObserver: not added" )
            }
        }
    }

    fun deleteObserver(observer: Observer?) {
        observers?.apply {
            remove(observer)
        }
        Log.e(TAG, "deleteObserver: " + observers.size)
    }

    fun notifyObservers(any: Any?) {
        observers.forEach {
            it.update(null, any)
        }
    }

    fun notifyObserver(any: Any?, observer: Observer) {
        observers?.apply {
            if (contains(observer)) {
//                Log.e(TAG, "恢复成功:$any ")
                observer.update(null, any)
            } else {
//                Log.e(TAG, "不包含，不恢复")
            }
        }
    }

    fun deleteObservers() {
        observers?.apply {
            observers.removeAllElements()
        }
    }

}