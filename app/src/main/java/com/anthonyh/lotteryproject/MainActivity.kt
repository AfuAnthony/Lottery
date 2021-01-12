package com.anthonyh.lotteryproject

import android.os.Bundle
import android.widget.TableLayout
import android.widget.Toast
import com.anthonyh.lotteryproject.base.BaseActivity
import com.anthonyh.lotteryproject.common.database.ImportData
import com.anthonyh.lotteryproject.module.jibenzoushi.ui.fragment.JiBenZouShiFragment
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //申请权限
        XXPermissions.with(this)
            .permission(Permission.MANAGE_EXTERNAL_STORAGE)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    if (all) {
                        Toast.makeText(applicationContext, "权限申请成功", Toast.LENGTH_SHORT).show()
                        setContentView(R.layout.activity_main_ex)
                    } else {
                        Toast.makeText(applicationContext, "权限未全部通过，", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    Toast.makeText(applicationContext, "请打开需要的权限，", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
    }



    override fun getFullScreenFlag(): Boolean = true
}