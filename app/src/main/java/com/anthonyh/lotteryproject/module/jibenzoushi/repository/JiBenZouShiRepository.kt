package com.anthonyh.lotteryproject.module.jibenzoushi.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anthonyh.lotteryproject.common.database.ImportData
import com.anthonyh.lotteryproject.common.database.LotteryNumber
import com.anthonyh.lotteryproject.common.database.NumberDao
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.JiBenZouShiBeanLiveBean

/**
created by AnthonyH
createDate: 2020/9/27 0027
 */
class JiBenZouShiRepository(private val dao: NumberDao) {


//    /**
//     * 当上层请求新的数据的时候，通过viewModel去访问repository的方法，
//     * repository查询数据库，将得到的数据
//     */
//    private var _numbers = MutableLiveData<JiBenZouShiBeanLiveBean>()
//
//    /**
//     * 发布到外部(上层),需要先将具体的可变的MutableLiveData(带有set方法)
//     * 转型成抽象的LiveData(不带有set方法)，这样外部就不能自己更改数据，达到唯一数据源设置，
//     * 也防止外部可能因为误操作导致的bug
//     *
//     *
//     */
//    val numbers: LiveData<JiBenZouShiBeanLiveBean>
//        get() = _numbers


    fun loadNewData(position: Int, count: Int): List<LotteryNumber>? {
        return dao?.getNumbers(position + 1, count)
    }




}