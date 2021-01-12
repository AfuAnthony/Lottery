package com.anthonyh.lotteryproject.module.jibenzoushi.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.anthonyh.lotteryproject.module.jibenzoushi.repository.JiBenZouShiRepository
import com.anthonyh.lotteryproject.common.commoninterface.DataSource
import com.anthonyh.lotteryproject.common.database.DATABASE_EXECUTOR
import com.anthonyh.lotteryproject.common.database.ImportData
import com.anthonyh.lotteryproject.common.database.LotteryRoomDatabase
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.JiBenZouShiBeanLiveBean
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.JiBenZouShiItemBean

/**
created by AnthonyH
createDate: 2020/9/27 002
 */
class JiBenZouShiViewModel constructor() : ViewModel(),
    DataSource {

    companion object {
        /**
         * 每次默认请求10条数据
         */
        const val PER_COUNT = 20
    }

    private lateinit var jiBenZouShiRepository: JiBenZouShiRepository

    /**
     * 可以改变值的只放在内部，
     */
    private val _showNumber = MutableLiveData<JiBenZouShiBeanLiveBean>()

    /**
     * 让外部监听这个不可变的
     */
    val showNumber: LiveData<JiBenZouShiBeanLiveBean>
        get() = _showNumber


    private val doubleDimensioData = ArrayList<JiBenZouShiItemBean>()
    private val idList = ArrayList<String>()

    fun init(context: Context) {
        val database = LotteryRoomDatabase.getLotteryDatabase(context)
        val numberDao = database?.getNumberDao()
        jiBenZouShiRepository = JiBenZouShiRepository(numberDao)
    }

    override fun requestData() {
        requestData(doubleDimensioData.size)
    }


    fun importData(context: Context) {
        //test
        ImportData.run(
            context,
            object : ImportData.ImportDataListener {
                override fun onSuccced(count: Long) {
                    Toast.makeText(context, "成功导入$count 条数据", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Exception) {
                    e?.run {
                        Toast.makeText(
                            context,
                            "导入错误:${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            })
    }



    override fun requestData(index: Int) {
        DATABASE_EXECUTOR.submit {
            try {
                val lotteryList = jiBenZouShiRepository?.loadNewData(index, PER_COUNT)
                //将查询的数据转换成可以直接显示的item，添加进大列表
                lotteryList?.run {
                    doubleDimensioData.clear()
                    forEach {
                        val item = JiBenZouShiItemBean.create(doubleDimensioData, it)
                        item?.run {
                            doubleDimensioData.add(this)
                        }
                        idList.add(it.numberId.toString())
                    }
                    _showNumber.postValue(
                        JiBenZouShiBeanLiveBean(
                            false,
                            idList,
                            doubleDimensioData
                        )
                    )

                } ?: kotlin.run {
                    //说明是查询不到数据了
                    _showNumber.postValue(JiBenZouShiBeanLiveBean(true, null, null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}