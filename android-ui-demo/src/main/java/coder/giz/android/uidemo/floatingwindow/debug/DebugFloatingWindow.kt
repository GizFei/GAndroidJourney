package coder.giz.android.uidemo.floatingwindow.debug

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.*
import androidx.core.view.isVisible
import androidx.databinding.ObservableBoolean
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import coder.giz.android.uidemo.databinding.LayoutDebugFloatingWindowBinding
import coder.giz.android.yfui.helper.OnPressScaleChangeTouchListener
import coder.giz.android.yfutility.components.toast
import coder.giz.android.yfutility.util.getScreenHeight
import kotlin.math.max
import kotlin.math.min

/**
 * 调试悬浮窗
 *
 * Created by GizFei on 2022/2/22
 */
class DebugFloatingWindow private constructor(private val mContext: Context) :
    DebugSettingCardAdapter.DebugSettingCallback {

    companion object {
        private const val IS_SINGLETON = false

        @SuppressLint("StaticFieldLeak")
        private var _instance: DebugFloatingWindow? = null
        fun instance(context: Context): DebugFloatingWindow {
            return if (IS_SINGLETON) {
                val value = _instance
                return if (value != null) {
                    value
                } else {
                    val newInstance = DebugFloatingWindow(context)
                    _instance = newInstance
                    newInstance
                }
            } else {
                DebugFloatingWindow(context)
            }
        }
    }

    private val mWindowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val mLayoutParams = WindowManager.LayoutParams().apply {
        type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        format = PixelFormat.RGBA_8888  // 如果不加这句，背景是全黑色
        flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        gravity = Gravity.START or Gravity.TOP
//        x = 200
        y = 0
        windowAnimations = android.R.style.Animation_Translucent
    }
    private var mViewCreated = false
    private val mBinding: LayoutDebugFloatingWindowBinding by lazy {
        LayoutDebugFloatingWindowBinding.inflate(LayoutInflater.from(mContext))
    }
    private var mDebugInfoCardAdapter: DebugInfoCardAdapter? = null
    private var mDebugSettingCardAdapter: DebugSettingCardAdapter? = null

    private val mDebugSettingSP = DebugSettingSP(mContext)
    private val mDebugInfoItemManager = DebugInfoItemManager(mContext)
    private val mScreenHeight: Int by lazy { getScreenHeight(mContext) }

    private var mIsShowing: Boolean = false
    private var mIsDebugInfoPanelVisible = ObservableBoolean(true)
    private var mIsDebugContentVisible = ObservableBoolean(true)

    fun getManager(): DebugInfoItemManager = mDebugInfoItemManager

    fun show() {
        // 申请悬浮窗权限
        if (!Settings.canDrawOverlays(mContext)) {
            mContext.toast("请先开启悬浮窗权限")
            mContext.startActivity(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${mContext.packageName}")
                )
            )
            return
        }

        if (mIsShowing) {
            return
        }

        val view = createView()
        mWindowManager.addView(view, mLayoutParams)
        mIsShowing = true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createView(): View {
        if (mViewCreated) {
            return mBinding.root
        }
        mViewCreated = true
        // 保证mBinding已创建
        mBinding.run {
            isInfoPanelVisible = mIsDebugInfoPanelVisible
            isContentVisible = mIsDebugContentVisible
            rvInfoCards.adapter = createInfoCardAdapter()
            rvSettingCards.adapter = createSettingCardAdapter()
            ivShowBtn.setOnClickListener { toggleViewsVisibility() }
            ivShowBtn.setOnTouchListener(FloatingOnTouchListener(root))
            ivSwitchBtn.setOnTouchListener(OnPressScaleChangeTouchListener())
            ivSwitchBtn.setOnClickListener { switchPanel() }
            ivCloseBtn.setOnTouchListener(OnPressScaleChangeTouchListener())
            ivCloseBtn.setOnClickListener { destroyView() }
        }
        return mBinding.root
    }

    /**
     * 隐藏/显示面板、按钮等视图内容。
     */
    private fun toggleViewsVisibility() {
        mIsDebugContentVisible.set(!mIsDebugContentVisible.get())
    }

    /**
     * 切换“信息面板”和“设置面板”。
     */
    private fun switchPanel() {
        mIsDebugInfoPanelVisible.set(!mIsDebugInfoPanelVisible.get())
    }

    /**
     * 移除悬浮窗。
     */
    private fun destroyView() {
        mIsShowing = false
        mWindowManager.removeView(mBinding.root)
    }

    private fun createInfoCardAdapter(): DebugInfoCardAdapter {
        val adapter = DebugInfoCardAdapter(mContext).also { mDebugInfoCardAdapter = it }
        mDebugInfoItemManager.initDebugInfoItemMap()
        val data = mDebugInfoItemManager.getCustomizedDebugInfoItems(mDebugSettingSP)
        updateInfoCardAdapter(data)
        return adapter
    }

    private fun updateInfoCardAdapter(data: List<DebugInfoItem>) {
        mBinding.tvDebugInfoEmptyHint.isVisible = data.isEmpty()
        mDebugInfoCardAdapter?.updateData(data)
    }

    private fun createSettingCardAdapter(): DebugSettingCardAdapter {
        val adapter = DebugSettingCardAdapter(mContext).apply {
            setCallback(this@DebugFloatingWindow)
            mDebugSettingCardAdapter = this
        }
        val settingsMap = mDebugSettingSP.getSettingMap()
        val settings = settingsMap
            .map { DebugSettingItem(it.key, it.key.title, it.value) }
            .sortedBy { it.itemType }
        adapter.updateData(settings)
        return adapter
    }

    override fun onSettingChanged(itemType: DebugInfoItemManager.ItemType, enabled: Boolean) {
        // 设置项变化，更新信息面板
        mDebugSettingSP.setSettingEnabled(itemType, enabled)
        val data = mDebugInfoItemManager.getCustomizedDebugInfoItems(mDebugSettingSP)
        updateInfoCardAdapter(data)
    }

    private inner class FloatingOnTouchListener(val targetView: View) : View.OnTouchListener {
        private var x: Int = 0
        private var y: Int = 0
        private var moved = false
        private var currentScale = 1.0f
        private val minScale = 0.8f

        override fun onTouch(v: View, event: MotionEvent?): Boolean {
            event ?: return false
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                    moved = false
                    startAnimation(v, currentScale, minScale)
                }
                MotionEvent.ACTION_MOVE -> {
                    moved = true
                    val moveX = event.rawX - x
                    val moveY = event.rawY - y
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                    val topY = max(0, moveY.toInt() + mLayoutParams.y)
                    mLayoutParams.y = min(topY, mScreenHeight)
                    mWindowManager.updateViewLayout(targetView, mLayoutParams)
                }
                MotionEvent.ACTION_UP -> {
                    startAnimation(v, currentScale, 1.0f)
                    if (!moved) {
                        v.performClick()
                    }
                }
            }
            return true
        }

        private fun startAnimation(v: View, fromScale: Float, toScale: Float) {
            val animator = ValueAnimator.ofFloat(fromScale, toScale)
            animator.duration = 200
            animator.interpolator = FastOutSlowInInterpolator()
            animator.addUpdateListener {
                currentScale = it.animatedValue as Float
                v.scaleX = currentScale
                v.scaleY = currentScale
            }
            animator.start()
        }
    }

}