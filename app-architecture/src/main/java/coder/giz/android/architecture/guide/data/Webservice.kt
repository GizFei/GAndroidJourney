package coder.giz.android.architecture.guide.data

import coder.giz.android.architecture.guide.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit风格的网络请求接口。使用Github REST API。
 *
 * Created By GizFei on 2021/10/7
 */
interface Webservice {
    /**
     * [Get a user API](https://docs.github.com/en/rest/reference/users#get-a-user)
     */
    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String): User

    @GET("/users/{username}")
    suspend fun getUser2(@Path("username") username: String): Call<User>
}