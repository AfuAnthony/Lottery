package com.anthonyh.lotteryproject.module.jibenzoushi.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.anthonyh.custom.widgets.JibenZouShiViewGroupEx
import com.anthonyh.lotteryproject.R
import com.anthonyh.lotteryproject.common.commoninterface.CustomTable
import com.anthonyh.lotteryproject.common.commoninterface.DataSource
import com.anthonyh.lotteryproject.common.database.LotteryRoomDatabase
import com.anthonyh.lotteryproject.module.jibenzoushi.repository.JiBenZouShiRepository
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean.JiBenZouShiBeanLiveBean
import com.anthonyh.lotteryproject.module.jibenzoushi.viewmodel.JiBenZouShiViewModel

/**
created by AnthonyH
createDate: 2020/9/27 0027
 */
class JiBenZouShiFragment : Fragment(),
    DataSource {
    private var showTable: CustomTable<JiBenZouShiBeanLiveBean>? = null
    private var jiBenZouShiViewModel: JiBenZouShiViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_jibenzoushi_ex, null)
        showTable = view.findViewById<JibenZouShiViewGroupEx>(R.id.jibensouzhi_vp)
        showTable?.setDataSource(this)
        initDataSource()
        return view
    }

    private fun initDataSource() {
        val database = LotteryRoomDatabase.getLotteryDatabase(context!!.applicationContext)
        val numberDao = database?.getNumberDao()
        jiBenZouShiViewModel = ViewModelProvider(this).get(JiBenZouShiViewModel::class.java)
        jiBenZouShiViewModel?.apply {
            jiBenZouShiRepository = JiBenZouShiRepository(numberDao)
            showNumber?.observe(viewLifecycleOwner, this@JiBenZouShiFragment::onChanged)
        }
    }

    //列表请求添加数据
    override fun requestData() {
        jiBenZouShiViewModel?.requestData()
    }

    override fun requestData(index: Int) {
        jiBenZouShiViewModel?.requestData(index)
    }

    /**
     * [JiBenZouShiViewModel]有数据给过来
     */
    private fun onChanged(data: JiBenZouShiBeanLiveBean) {
        Log.e(TAG, "onChanged: " + data.contentList?.size)
        showTable?.refresh(data)
    }

    private val TAG = "JiBenZouShiFragment"

}