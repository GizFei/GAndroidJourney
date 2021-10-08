package coder.giz.android.architecture.guide.data

import coder.giz.android.architecture.guide.model.User

/**
 * Created By GizFei on 2021/10/7
 */
class UserCache {

    // username: User
    private val mMap = hashMapOf<String, User>()

    operator fun get(username: String): User? {
        return mMap[username]
    }

    fun put(username: String, user: User) {
        mMap[username] = user
    }

    operator fun set(username: String, user: User) {
        put(username, user)
    }

}