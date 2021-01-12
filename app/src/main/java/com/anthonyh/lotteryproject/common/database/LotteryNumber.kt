package com.anthonyh.lotteryproject.common.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
created by AnthonyH
createDate: 2020/9/27 0027
 */
@Entity(tableName = "${LotteryNumber.TABLE_NAME}")
class LotteryNumber constructor(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "number_id")
    val numberId: Int,
    @ColumnInfo(name = "red_first")
    val redFirst: Int,
    @ColumnInfo(name = "red_second")
    val redSecond: Int,
    @ColumnInfo(name = "red_third")
    val redThird: Int,
    @ColumnInfo(name = "red_fourth")
    val redFourth: Int,
    @ColumnInfo(name = "red_fivth")
    val redFivth: Int,
    @ColumnInfo(name = "number_date")
    val numberDate: String,
    @ColumnInfo(name = "blue_first")
    val blueFirst: Int,
    @ColumnInfo(name = "blue_second")
    val blueSecond: Int
) {
//      constructor() : this(0, 0, 0, 0, 0, 0, "", 0, 0)

    companion object {
        const val TABLE_NAME = "lottery_number_table"
    }

    fun isRedSelected(number: Int): Int {
        return when (number) {
            redFirst -> redFirst
            redSecond -> redSecond
            redThird -> redThird
            redFourth -> redFourth
            redFivth -> redFivth
            else -> -1
        }
    }

    fun isBlueSelected(number: Int): Int {
        return when (number) {
            blueFirst -> blueFirst
            blueSecond -> blueSecond
            else -> -1
        }
    }

    override fun toString(): String {
        return "${redFirst},${redSecond},${redThird},${redFourth},${redFivth};${numberDate},${blueFirst},${blueSecond}"
    }

}