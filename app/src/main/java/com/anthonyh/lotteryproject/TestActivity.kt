package com.anthonyh.lotteryproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.custom.widgets.LotterTextView

/**
@author HAnthony
@date 2020/12/14 0014
@description
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_hor)
        val recyclerView: RecyclerView = findViewById(R.id.test_recyclerview)
        recyclerView?.apply {
            layoutManager = GridLayoutManager(this@TestActivity, 35 + 12)
            adapter = TestAdapter()
        }
    }


    class TestAdapter : RecyclerView.Adapter<TestViewHolder>() {
        private val RED_LENGTH = 35
        private val BLUE_LENGTH = 12
        private val ITEM_SIZE = RED_LENGTH + BLUE_LENGTH
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, null)
            return TestViewHolder(view)
        }

        override fun getItemCount(): Int {
            return ITEM_SIZE * 100
        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
            holder.textView?.apply {
                isBackHightLight = true
                backHightLightColor =
                    context!!.getColor(R.color.jibenzoushi_red_select_back)
                val rePos = (position + 1) % ITEM_SIZE
                text = when {
                    rePos in 1..35 -> {
                        rePos.toString()
                    }
                    rePos > 35 -> {
                        (rePos - 35).toString()
                    }
                    else -> {//0
                        "12"
                    }
                }
            }
        }
    }

    class TestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: LotterTextView = view.findViewById(R.id.hor_tv)
    }

}