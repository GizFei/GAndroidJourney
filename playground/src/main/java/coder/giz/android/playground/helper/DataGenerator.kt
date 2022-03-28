package coder.giz.android.playground.helper

import coder.giz.android.playground.timer.AutoOffDemoActivity
import coder.giz.android.playground.timer.CountdownTimerActivity
import coder.giz.android.yfutility.navigation.ActivityNavItem
import coder.giz.android.yfutility.navigation.HeaderNavItem

/**
 * Created by GizFei on 2022/3/21
 */
object DataGenerator {

    val AndroidUiDemoNavItems = listOf(
        HeaderNavItem("Timer（定时器）"),
        ActivityNavItem("倒计时", "倒计时实现方法", CountdownTimerActivity::class),
        ActivityNavItem("AutoOff示例", "AutoOff示例测试页面", AutoOffDemoActivity::class),
    )

}