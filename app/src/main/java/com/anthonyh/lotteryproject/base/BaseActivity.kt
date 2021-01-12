package com.anthonyh.lotteryproject.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
created by AnthonyH
createDate: 2020/9/28 0028
 */
open abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getFullScreenFlag()) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            supportActionBar?.run {
                hide()
            }
        }

    }


    abstract fun getFullScreenFlag(): Boolean
}