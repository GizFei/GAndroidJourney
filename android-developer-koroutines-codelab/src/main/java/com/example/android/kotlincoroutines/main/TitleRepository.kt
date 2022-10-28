/*
 * Copyright (C) 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.kotlincoroutines.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import coder.giz.android.yfutility.util.YFLog
import com.example.android.kotlincoroutines.util.BACKGROUND
import com.example.android.kotlincoroutines.util.appendThreadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * TitleRepository provides an interface to fetch a title or request a new one be generated.
 *
 * Repository modules handle data operations. They provide a clean API so that the rest of the app
 * can retrieve this data easily. They know where to get the data from and what API calls to make
 * when data is updated. You can consider repositories to be mediators between different data
 * sources, in our case it mediates between a network API and an offline database cache.
 */
class TitleRepository(val network: MainNetwork, val titleDao: TitleDao) {

    companion object {
        private const val TAG = "TitleRepository"
    }

    /**
     * [LiveData] to load title.
     *
     * This is the main interface for loading a title. The title will be loaded from the offline
     * cache.
     *
     * Observing this will not cause the title to be refreshed, use [TitleRepository.refreshTitleWithCallbacks]
     * to refresh the title.
     */
    val title: LiveData<String?> = titleDao.titleLiveData.map { it?.title }


    // TODO: Add coroutines-based `fun refreshTitle` here
    suspend fun refreshTitle() {
        // TODO: Refresh from network and write to database
        YFLog.w(TAG, "refreshTitle".appendThreadInfo())
//        delay(1000)
        // interact with *blocking* network and IO calls from a coroutine
        // 切换到IO线程进行操作
        withContext(Dispatchers.IO) {
            // 切换到IO线程创建协程
            val result = try {
                YFLog.w(TAG, "network.fetchNextTitle().execute()".appendThreadInfo())
                // Make network request using a blocking call
                // 调用阻塞线程的网络请求
                network.fetchNextTitle().execute()
            } catch (cause: Throwable) {
                // If the network throws an exception, inform the caller
                throw TitleRefreshError("Unable to refresh title", cause)
            }

            YFLog.w(TAG, "network result return.".appendThreadInfo())

            if (result.isSuccessful) {
                // Save it to database
                // 调用阻塞线程的数据库方法
                YFLog.w(TAG, "titleDao.insertTitle".appendThreadInfo())
                titleDao.insertTitleSuspend(Title(result.body()!!))
            } else {
                // If it's not successful, inform the callback of the error
                throw TitleRefreshError("Unable to refresh title", null)
            }
        }
    }

    /**
     * 协程改造的终极版本。
     */
    suspend fun refreshTitleFinalVersion() {
        // TODO: Refresh from network and write to database
        YFLog.w(TAG, "refreshTitle".appendThreadInfo())
        try {
            // 以下两个suspend函数内部已经自动切换调度器执行，所以不需要在这里withContext
            YFLog.w(TAG, "network.fetchNextTitleSuspend".appendThreadInfo())
            val result = network.fetchNextTitleSuspend()
            YFLog.w(TAG, "titleDao.insertTitleSuspend".appendThreadInfo())
            titleDao.insertTitleSuspend(Title(result))
        } catch (cause: Throwable) {
            // If anything throws an exception, inform the caller
            YFLog.w(TAG, "refreshTitleFinalVersion error: $cause ".appendThreadInfo())
            throw TitleRefreshError("Unable to refresh title", cause)
        }
    }

    /**
     * Refresh the current title and save the results to the offline cache.
     *
     * This method does not return the new title. Use [TitleRepository.title] to observe
     * the current tile.
     */
    fun refreshTitleWithCallbacks(titleRefreshCallback: TitleRefreshCallback) {
        // This request will be run on a background thread by retrofit
        BACKGROUND.submit {
            try {
                // Make network request using a blocking call
                val result = network.fetchNextTitle().execute()
                if (result.isSuccessful) {
                    // Save it to database
                    val resultBody = result.body()!!
                    YFLog.d(resultBody)
                    titleDao.insertTitle(Title(resultBody))
                    // Inform the caller the refresh is completed
                    titleRefreshCallback.onCompleted()
                } else {
                    // If it's not successful, inform the callback of the error
                    titleRefreshCallback.onError(
                            TitleRefreshError("Unable to refresh title", null))
                }
            } catch (cause: Throwable) {
                // If anything throws an exception, inform the caller
                titleRefreshCallback.onError(
                        TitleRefreshError("Unable to refresh title", cause))
            }
        }
    }
}

/**
 * Thrown when there was a error fetching a new title
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class TitleRefreshError(message: String, cause: Throwable?) : Throwable(message, cause)

interface TitleRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}
