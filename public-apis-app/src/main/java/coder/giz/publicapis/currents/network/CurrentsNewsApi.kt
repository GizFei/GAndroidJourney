package coder.giz.publicapis.currents.network

import coder.giz.publicapis.currents.model.NewsListResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by GizFei on 2022/10/29
 */
interface CurrentsNewsApi {
    /**
     * 获取最近新闻。
     *
     * @param language 语言
     */
    @GET("/v1/latest-news")
    suspend fun getLatestNews(@Query("language") language: String): NewsListResult

}