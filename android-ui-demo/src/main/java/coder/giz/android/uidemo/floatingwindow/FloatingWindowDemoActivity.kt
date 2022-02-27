package coder.giz.android.uidemo.floatingwindow

import android.view.View
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityFloatingWindowDemoBinding
import coder.giz.android.uidemo.floatingwindow.debug.DebugFloatingWindow
import coder.giz.android.yfui.base.DataBindingBaseActivity
import kotlin.random.Random

/**
 * 悬浮窗示例页面
 */
class FloatingWindowDemoActivity :
    DataBindingBaseActivity<ActivityFloatingWindowDemoBinding>(),
    View.OnClickListener {

    private val mDebugFloatingWindow by lazy { DebugFloatingWindow.instance(this) }

    override fun getLayoutId(): Int = R.layout.activity_floating_window_demo

    override fun initDataBinding() {
        super.initDataBinding()
        mBinding.onClickListener = this
    }

    override fun initView() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_debug_floating_window -> mDebugFloatingWindow.show()
            R.id.btn_current_page_info -> updateCurrentPageInfo()
            R.id.btn_device_offline_reason_info -> updateDeviceOfflineReasonInfo()
        }
    }

    private fun updateCurrentPageInfo() {
        when (Random.nextInt(3)) {
            0 -> mDebugFloatingWindow.getManager().updateCurrentPageItem(
                activityName = this::class.simpleName
            )
            1 -> mDebugFloatingWindow.getManager().updateCurrentPageItem(
                fragmentName = "FakeDemoFragment"
            )
            2 -> mDebugFloatingWindow.getManager().updateCurrentPageItem(
                activityName = "FakeDemoActivity",
                fragmentName = "FakDemoInActivityFragment"
            )
        }
    }

    private fun updateDeviceOfflineReasonInfo() {
        when (Random.nextInt(2)) {
            0 -> mDebugFloatingWindow.getManager().updateDeviceOfflineReasonPageItem(
                "Local SyncDeviceInfoAndComponentInfo failed."
            )
            1 -> mDebugFloatingWindow.getManager().updateDeviceOfflineReasonPageItem(
                "Remote /shadows? /settings /details failed."
            )
        }
    }

}