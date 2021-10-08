package coder.giz.android.architecture.guide.data

import coder.giz.android.architecture.guide.model.User
import javax.inject.Inject

/**
 * Created By GizFei on 2021/10/7
 */
class UserRepository @Inject constructor(
    private val mWebservice: Webservice,
    private val mUserCache: UserCache
) {
    suspend fun getUser(username: String): User {
        val cached = mUserCache.get(username)
        if (cached != null) {
            return cached
        }
        val freshUser = mWebservice.getUser(username)
        mUserCache[username] = freshUser
        return freshUser
    }
}