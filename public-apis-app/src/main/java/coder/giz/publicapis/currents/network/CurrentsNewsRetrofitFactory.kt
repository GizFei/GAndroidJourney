package coder.giz.publicapis.currents.network

import coder.giz.android.yfutility.util.YFLog
import coder.giz.publicapis.helper.HttpLoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by GizFei on 2022/10/29
 */
class CurrentsNewsRetrofitFactory(
    private val mParamsSupplier: ParamsSupplier,
) {

    companion object {
        private val HTTP_LOGGER = HttpLoggingInterceptor.Logger {
            YFLog.d("CurrentsNews HttpLog", it)
        }
    }

    fun createRetrofit(url: String): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            // 超时时间
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            // HTTP头部拦截器
            addInterceptor(CurrentsNewsHeadersParamsInterceptor(mParamsSupplier))
            // 日志打印
            addInterceptor(HttpLoggingInterceptor(HTTP_LOGGER))
        }.build()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    interface ParamsSupplier {
        fun getApiKey(): String
    }

    class CurrentsNewsHeadersParamsInterceptor(
        private val mParamsSupplier: ParamsSupplier
    ) : Interceptor {

        companion object {
            private const val HEADER_AUTHORIZATION = "Authorization"
        }

        override fun intercept(chain: Interceptor.Chain): Response {
            // 在请求头部中添加相关信息
            val newRequest = chain.request().newBuilder()
                .addHeader(HEADER_AUTHORIZATION, mParamsSupplier.getApiKey())
                .build()
            // 处理新请求
            return chain.proceed(newRequest)
        }
    }

}