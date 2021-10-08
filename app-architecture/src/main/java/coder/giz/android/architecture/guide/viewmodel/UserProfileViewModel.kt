package coder.giz.android.architecture.guide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coder.giz.android.architecture.guide.data.UserRepository

import coder.giz.android.architecture.guide.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created By GizFei on 2021/10/7
 */
@HiltViewModel
class UserProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository
) : ViewModel() {
    var username: String = savedStateHandle["username"] ?: ""

    private val mUser = MutableLiveData<User>()
    var user: LiveData<User> = mUser

    init {
        viewModelScope.launch {
            mUser.value = userRepository.getUser(username)
        }
    }
}