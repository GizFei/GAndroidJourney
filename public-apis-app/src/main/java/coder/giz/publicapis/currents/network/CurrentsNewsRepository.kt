package coder.giz.publicapis.currents.network

import coder.giz.publicapis.currents.model.Language
import coder.giz.publicapis.currents.model.News
import coder.giz.publicapis.currents.model.Status

/**
 * Created by GizFei on 2022/10/29
 */
class CurrentsNewsRepository {

    companion object {
        private const val API_KEY = "SBrGNJVwHKg6FZdm6NL_00ptL26MnM8pMwt7h1zC-Hyr62-g"
        private const val BASE_URL = "https://api.currentsapi.services"
    }

    private val mParamsSupplier = object : CurrentsNewsRetrofitFactory.ParamsSupplier {
        override fun getApiKey(): String = API_KEY
    }

    private val mCurrentsNewsApi: CurrentsNewsApi = CurrentsNewsRetrofitFactory(mParamsSupplier)
        .createRetrofit(BASE_URL)
        .create(CurrentsNewsApi::class.java)

    suspend fun getLatestNews(language: Language = Language.EN): List<News> {
        val result = mCurrentsNewsApi.getLatestNews(language.value)
        return when (result.enumStatus) {
            Status.OK -> {
                result.news
            }
            Status.ERROR -> {
                throw Exception("/latest-news api status error")
            }
        }
    }

}