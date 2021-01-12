package com.anthonyh.lotteryproject.common.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
created by AnthonyH
createDate: 2020/9/27 0027
Room数据库上层抽象类
 */
@Database(entities = [LotteryNumber::class], version = LotteryRoomDatabase.VERSION)
abstract class LotteryRoomDatabase : RoomDatabase() {

    //操作彩票数据表的DAO
    abstract fun getNumberDao(): NumberDao

    companion object {
        const val VERSION = 1
        private const val DATABASE_NAME = "lottery_database.db"

        private lateinit var INSTANCE: LotteryRoomDatabase

        fun getLotteryDatabase(context: Context): LotteryRoomDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(LotteryRoomDatabase::class) {
                    if (!::INSTANCE.isInitialized) {
                        val instance = Room.databaseBuilder(
                            context, LotteryRoomDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                        INSTANCE = instance
                    }
                }
            }
            return INSTANCE
        }
    }
}