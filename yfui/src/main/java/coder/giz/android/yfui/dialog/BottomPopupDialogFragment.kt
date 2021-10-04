package coder.giz.android.yfui.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import coder.giz.android.yfui.R
import coder.giz.android.yfui.databinding.FragmentBottomDialogBinding

/**
 * 从底部弹出的 [DialogFragment]。左上、右上两圆角为16dp。
 *
 * Created by GizFei on 2021/1/31
 */
class BottomPopupDialogFragment : DialogFragment() {

    private lateinit var mBinding: FragmentBottomDialogBinding

    private var mContentView: View? = null
    private var mContentLayoutId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_dialog, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (mContentView == null && mContentLayoutId > 0) {
            mContentView = layoutInflater.inflate(mContentLayoutId, mBinding.flContent, true)
        } else if (mContentView != null) {
            mBinding.flContent.addView(mContentView)
        }
        makeFullscreen()
        mBinding.rootView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_in_from_bottom))
    }

    /**
     * 全屏沉底显示
     */
    private fun makeFullscreen() {
        dialog?.window?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            decorView.setPadding(0)
            attributes = attributes.apply {
                gravity = Gravity.BOTTOM
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.MATCH_PARENT
            }
        }
    }

    fun setContentView(view: View) {
        if (this::mBinding.isInitialized) {
            mContentView?.let {
                mBinding.flContent.removeView(it)
            }
            mContentView = view
            mBinding.flContent.addView(view)
        } else {
            mContentView = view
        }
    }

    fun setContentView(@LayoutRes layoutId: Int) {
        if (this::mBinding.isInitialized) {
            mContentView = layoutInflater.inflate(layoutId, mBinding.flContent, true)
        } else {
            mContentLayoutId = layoutId
        }
    }

}