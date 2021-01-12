package com.anthonyh.lotteryproject.common.database

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
created by AnthonyH
createDate: 2020/9/29 0029
 */


/**
 * 执行数据库操作的线程池
 */
val DATABASE_EXECUTOR: ExecutorService = Executors.newSingleThreadExecutor()
