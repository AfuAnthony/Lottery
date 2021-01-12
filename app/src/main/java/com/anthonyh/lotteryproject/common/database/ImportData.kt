package com.anthonyh.lotteryproject.common.database

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import org.apache.poi.hssf.usermodel.HSSFDateUtil
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.text.SimpleDateFormat


/**
created by AnthonyH
createDate: 2020/9/29 0029
从excel文件中导入数据，插入到数据库[LotteryRoomDatabase]
 */
object ImportData {

    private const val TAG = "ImportData"
    private const val ONCE_INSERT_COUNT = 20
    private val EXCEL_FILE_PATH: String =
        Environment.getExternalStorageDirectory().path.toString() + "/lo.xlsx"
    private var sumCount: Long = 0
    private val formatter =
        SimpleDateFormat("yyyy/MM/dd")
    private val mainHandler = Handler(Looper.getMainLooper())

    fun run(context: Context, importListener: ImportDataListener) {
        context!!.run {
            DATABASE_EXECUTOR.submit {
                try {
                    val database = LotteryRoomDatabase.getLotteryDatabase(context)
                    database!!.run {
                        val dao = getNumberDao()
                        dao!!.run {
                            deleteAll()
                            doImport(dao)
                            close()
                            callBackSuc(importListener)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    callBackError(e, importListener)
                }
            }
        }
    }

    private fun callBackSuc(listener: ImportDataListener) {
        listener?.run { mainHandler.post { onSuccced(sumCount) } }
    }

    private fun callBackError(e: java.lang.Exception, listener: ImportDataListener) {
        listener?.run { mainHandler.post { onError(e) } }
    }

    private fun doImport(dao: NumberDao) {
        val excelFile = File(EXCEL_FILE_PATH)
        excelFile?.run {
            if (exists()) {
                //为了防止内存中加载过多数据，每当次列表插满的时候，往数据库导入一次
                val lotterList = ArrayList<LotteryNumber>(ONCE_INSERT_COUNT)

                val stream: InputStream = FileInputStream(excelFile)
                val workbook = XSSFWorkbook(stream)
                val sheet = workbook.getSheetAt(0)
                //
                val rowCount = sheet.physicalNumberOfRows
                for (rowIndex in 0 until rowCount) {
                    val row: Row = sheet.getRow(rowIndex)
                    val rowCellCount = row.physicalNumberOfCells
                    val formulaEvaluator: FormulaEvaluator =
                        workbook.creationHelper.createFormulaEvaluator()
                    if (rowIndex == 1839) {
                        Log.e(TAG, "doImport: ...")
                    }
                    if (rowCellCount == 9) {
                        try {
                            val cellDate = row.getCell(6)
                            if (HSSFDateUtil.isCellDateFormatted(cellDate)) {

                                val dateString = formatter.format(
                                    HSSFDateUtil.getJavaDate(
                                        formulaEvaluator.evaluate(cellDate).getNumberValue()
                                    )
                                )
                                val lotteryNumber = LotteryNumber(
                                    formulaEvaluator.evaluate(row.getCell(0)).numberValue.toInt(),
                                    formulaEvaluator.evaluate(row.getCell(1)).numberValue.toInt(),
                                    formulaEvaluator.evaluate(row.getCell(2)).numberValue.toInt(),
                                    formulaEvaluator.evaluate(row.getCell(3)).numberValue.toInt(),
                                    formulaEvaluator.evaluate(row.getCell(4)).numberValue.toInt(),
                                    formulaEvaluator.evaluate(row.getCell(5)).numberValue.toInt(),
                                    dateString,
                                    formulaEvaluator.evaluate(row.getCell(7)).numberValue.toInt(),
                                    formulaEvaluator.evaluate(row.getCell(8)).numberValue.toInt()
                                )
                                lotterList.add(lotteryNumber)
                                checkIfInsertAndCleanList(dao, lotterList, true)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.e(TAG, "跳过一次:${e.message} ")
                        }
                    }
                }
                checkIfInsertAndCleanList(dao, lotterList, true)
            }

        }
    }

    //插入一次
    fun checkIfInsertAndCleanList(
        dao: NumberDao,
        lotterList: ArrayList<LotteryNumber>,
        isFinishLoop: Boolean
    ) {
        if ((isFinishLoop && lotterList.size == ONCE_INSERT_COUNT) || !isFinishLoop) {
            dao.insertNumbers(lotterList)
            sumCount += lotterList.size
            lotterList.clear()
            Log.d(TAG, "checkIfInsertAndCleanList: 成功插入数据------")
        }
    }


    interface ImportDataListener {
        fun onSuccced(count: Long)
        fun onError(e: Exception)
    }

}