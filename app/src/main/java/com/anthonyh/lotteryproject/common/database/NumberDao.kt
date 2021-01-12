package com.anthonyh.lotteryproject.common.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
created by AnthonyH
createDate: 2020/9/27 0027
DAO必须是接口或者抽象类
 */
@Dao
abstract class NumberDao {
    /**
     *
     *
     *查询从 [startRow]行开始的记录[count]条，之所以不用id来查询，因为不知道数据库的id的起点和是否连续
     * 所以直接依賴行数更好
     *
     */
    @Query("select * from ${LotteryNumber.TABLE_NAME}  limit :startRow, :count ")
    abstract fun getNumbers(startRow: Int, count: Int): List<LotteryNumber>?


    @Query("select * from ${LotteryNumber.TABLE_NAME}  limit :startRow, :count ")
    abstract fun getNumbersLively(startRow: Int, count: Int): LiveData<LotteryNumber>?


    /**
     * 插入单条彩票记录
     */
    @Insert(entity = LotteryNumber::class, onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNumber(lotteryNumber: LotteryNumber)

    /**
     * 插入多条记录，用事务：一是为了提高速度，而是如果一条失败都失败，免得插入不完整
     */
    @Transaction
    open fun insertNumbers(lotteryNumbers: List<LotteryNumber>) {
        lotteryNumbers?.run {
            if (isNotEmpty()) {
                forEach {
                    insertNumber(it)
                }
            }
        }
    }

    @Query("delete from ${LotteryNumber.TABLE_NAME}")
    abstract fun deleteAll()

}