package com.anthonyh.lotteryproject.common.commoninterface

/**
created by AnthonyH
createDate: 2020/9/28 0028
 */
/**
 * 使用者实现这个接口，当列表控件需要数据的时候会调用[requestData]
 */
interface DataSource {
    fun requestData()
    fun requestData(index: Int)
}
