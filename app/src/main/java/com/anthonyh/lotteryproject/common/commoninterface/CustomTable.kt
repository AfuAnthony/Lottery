package com.anthonyh.lotteryproject.common.commoninterface

/**
created by AnthonyH
createDate: 2020/9/28 0028
 */
interface CustomTable<T> {

    fun setDataSource(dataSource: DataSource)

    fun refresh(data: T)
}