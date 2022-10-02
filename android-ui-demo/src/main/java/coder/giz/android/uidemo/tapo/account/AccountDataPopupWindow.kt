package coder.giz.android.uidemo.tapo.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import coder.giz.android.uidemo.databinding.LayoutAccountDataPopupWindowBinding

/**
 * Created by GizFei on 2022/3/1
 */
class AccountDataPopupWindow(private val mContext: Context) {

    private val mBinding = LayoutAccountDataPopupWindowBinding.inflate(LayoutInflater.from(mContext), null, false)
    private val mPopupWindow = PopupWindow(
        mBinding.root,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
    ).apply {
        isOutsideTouchable = false
        isFocusable = true
    }
    private var mAccountItem: AccountItem? = null
    private var mCallback: Callback? = null

    init {
        initViews()
    }

    private fun initViews() {
        mBinding.tvDeleteBtn.setOnClickListener { deleteAccount() }
        mBinding.tvSwitchBtn.setOnClickListener { switchAccount() }
    }

    fun showAsDropDown(anchor: View, xOff: Int = 0, yOff: Int = 0) {
        mPopupWindow.showAsDropDown(anchor, xOff, yOff)
    }

    fun setAccountItem(item: AccountItem) {
        mAccountItem = item
        updateViews()
    }

    private fun updateViews() {
        mAccountItem?.let {
            mBinding.run {
                tvAccountEmail.text = it.email
                tvAccountUsername.text = it.username
                ivAccountAvatar.setImageResource(it.avatarRes)
            }
        }
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    /**
     * 删除账号信息。
     */
    private fun deleteAccount() {
        mAccountItem?.let {
            mCallback?.deleteAccount(it)
        }
    }

    /**
     * 切换成此账号。
     */
    private fun switchAccount() {
        mAccountItem?.let {
            mCallback?.switchAccount(it)
        }
    }

    interface Callback {
        fun deleteAccount(item: AccountItem)
        fun switchAccount(item: AccountItem)
    }

}