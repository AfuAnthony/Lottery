package com.anthonyh.lotteryproject.module.jibenzoushi.ui.bean

import com.anthonyh.lotteryproject.common.database.LotteryNumber

/**
created by AnthonyH
createDate: 2020/9/28 0028
[redArray]前区
[blueArray]后区
 */
class JiBenZouShiItemBean(
    val itemId: Int,
    val redArray: Array<SigBean>,
    val blueArray: Array<SigBean>
) {

    companion object {

        private const val RED_COUNT = 35
        private const val BLUE_COUNT = 12

        /**
         * 这个创建的过程循环和比较太多了，还是不能放在主线程
         */
        fun create(
            list: List<JiBenZouShiItemBean>,//适配器中已有的条目
            lotteryNumber: LotteryNumber//当前被即将被添加的号码
        ): JiBenZouShiItemBean? {
//            var readNumbers = Array(RED_COUNT) { SigBean(false, 0) }
//            var blueNumbers = Array(BLUE_COUNT) { SigBean(false, 0) }
//            // 计算红球区每个数字
//            readNumbers = getNumbers(list, lotteryNumber, readNumbers)
//            // 计算蓝球数字
//            blueNumbers = getNumbers(list, lotteryNumber, blueNumbers)
            val redNumbers = getRedNumbers(list, lotteryNumber)
            val blueNumbers = getBlueNumbers(list, lotteryNumber)
            return JiBenZouShiItemBean(lotteryNumber.numberId, redNumbers, blueNumbers)
        }

//        private fun getNumbers(
//            list: List<JiBenZouShiItemBean>,
//            lotteryNumber: LotteryNumber, numberArray: Array<SigBean>
//        ): Array<SigBean> {
//            for (index in numberArray.indices) {//选中，填入相应的数字
//                val select = lotteryNumber.isBlueSelected(index + 1)
//                val currentIndexSigElement = numberArray[index]
//                if (select in 1..numberArray.size) {
//                    currentIndexSigElement.seleted = true
//                } else if (list.isNotEmpty()) {//未选中，如果不是第一期，计算遗漏
//                    val itemBean = list.last()
//                    if (itemBean != null) {
//                        //取出最后一次同一个号码的遗漏次数+1，设置为当前的遗漏次数
//                        currentIndexSigElement.seleted = false
//                        currentIndexSigElement.missingCount =
//                            itemBean.blueArray[index].missingCount + 1
//                    }
//                }
//            }
//            return numberArray
//        }


        //
        private fun getBlueNumbers(
            list: List<JiBenZouShiItemBean>,
            lotteryNumber: LotteryNumber
        ): Array<SigBean> {
            val blueNumbers = Array(BLUE_COUNT) { SigBean(false, 0) }
            for (index in blueNumbers.indices) {
                val select = lotteryNumber.isBlueSelected(index + 1)
                val currentIndexSigElement = blueNumbers[index]
                if (select in 1..blueNumbers.size) {//1-35红球中的当前号码被选中
                    currentIndexSigElement.seleted = true
                } else if (list.isNotEmpty()) {//当前号码未被选中，计算同号码的遗漏次数
                    val itemBean = list.last()
                    if (itemBean != null) {
                        //取出最后一次同一个号码的遗漏次数+1，设置为当前的遗漏次数
                        currentIndexSigElement.seleted = false
                        currentIndexSigElement.missingCount =
                            itemBean.redArray[index].missingCount + 1
                    }
                }
            }
            return blueNumbers
        }

        private fun getRedNumbers(
            list: List<JiBenZouShiItemBean>,
            lotteryNumber: LotteryNumber
        ): Array<SigBean> {
            val redNumbers = Array(RED_COUNT) { SigBean(false, 0) }
            for (index in redNumbers.indices) {
                val select = lotteryNumber.isRedSelected(index + 1)
                val currentIndexSigElement = redNumbers[index]
                if (select in 1..redNumbers.size) {//1-35红球中的当前号码被选中
                    currentIndexSigElement.seleted = true
                } else if (list.isNotEmpty()) {//当前号码未被选中，计算同号码的遗漏次数
                    val lastItemBean = list.last()
                    if (lastItemBean != null) {
                        //取出最后一次同一个号码的遗漏次数+1，设置为当前的遗漏次数
                        currentIndexSigElement.seleted = false
                        currentIndexSigElement.missingCount =
                            lastItemBean.redArray[index].missingCount + 1
                    }
                }
            }
            return redNumbers
        }
    }
}


/**
 *
 *
 * [seleted]表示当前号码是否被选中，如果没有选中，第二个参数才有意义
 * [missingCount]表示同一个号码，最近一次被选中到当前期遗漏的期数
 *
 */
class SigBean(var seleted: Boolean, var missingCount: Int) {
}


class JiBenZouShiBeanLiveBean(
    val isLast: Boolean,
    val idList: List<String>?,
    val contentList: List<JiBenZouShiItemBean>?
)

