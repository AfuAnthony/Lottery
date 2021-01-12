package com.anthonyh.custom.widgets

import com.anthonyh.lotteryproject.common.util.AfuObservable

/**
@author HAnthony
@date 2020/12/16 0016
@description
 */


class ScorllBean(val scrollX: Int, val scrollY: Int) {
    override fun toString(): String {
        return "ScorllBean(scrollX=$scrollX, scrollY=$scrollY)"
    }
}

class SyncControlScorll(val observeble: AfuObservable = AfuObservable()) {


}