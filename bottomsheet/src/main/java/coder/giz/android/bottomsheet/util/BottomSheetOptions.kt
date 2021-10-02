package coder.giz.android.bottomsheet.util

import android.os.Bundle
import java.io.Serializable

/**
 * BottomSheet配置项。
 * - 作`Dialog`时，配置项全部有效
 * - 作`Fragment`时，配置项部分生效
 *
 * Created by GizFei on 2021/7/2
 */
class BottomSheetOptions: Serializable {

    /**
     * 是否去掉背景遮罩。默认保留，即 `false`。
     */
    var noDim: Boolean = false
        private set

    /**
     * 是否使用顶部圆角。
     * BottomSheet的背景默认是白色。如果发现没有圆角，请检查内容布局文件：
     * · 是否设置了背景（如白色）。
     * · 是否添加了顶部内边距（为圆角腾出空间）。
     */
    var topRoundCorner: Boolean = true
        private set

    /**
     * 是否全屏（高度占满）
     */
    var fullscreen: Boolean = false
        private set

    /**
     * 顶部空间高度，单位：像素
     */
    var topSpacedPixels: Int = 0
        private set

    /**
     * 是否可以取消
     */
    var cancelable: Boolean = true
        private set

    /**
     * 是否可隐藏
     */
    var hideable: Boolean = true
        private set

    /**
     * 是否跳过 `STATE_COLLAPSED` 状态
     */
    var skipCollapsed: Boolean = false
        private set

    fun noDim(noDim: Boolean = true) = apply {
        this.noDim = noDim
    }

    fun topRoundCorner(make: Boolean = true) = apply {
        this.topRoundCorner = make
    }

    fun fullscreen(make: Boolean = true) = apply {
        this.fullscreen = make
    }

    fun topSpacedPixels(pixels: Int) = apply {
        this.topSpacedPixels = pixels
    }

    fun cancelable(able: Boolean) = apply {
        this.cancelable = able
    }

    fun hideable(able: Boolean) = apply {
        this.hideable = able
    }

    fun skipCollapsed(skip: Boolean) = apply {
        this.skipCollapsed = skip
    }

    fun toBundle() = Bundle().apply {
        putBoolean(OPTION_KEY_NO_DIM, noDim)
        putBoolean(OPTION_KEY_TOP_ROUND_CORNER, topRoundCorner)
        putBoolean(OPTION_KEY_FULLSCREEN, fullscreen)
        putInt(OPTION_KEY_TOP_SPACED_PIXELS, topSpacedPixels)
        putBoolean(OPTION_KEY_CANCELABLE, cancelable)
        putBoolean(OPTION_KEY_HIDEABLE, hideable)
        putBoolean(OPTION_KEY_SKIP_COLLAPSED, skipCollapsed)
    }

    fun mergeBundle(bundle: Bundle?) = toBundle().apply {
        bundle?.let(this::putAll)
    }

    override fun toString(): String {
        return """BottomSheetOptions(
            |noDim = $noDim, topRoundCorner = $topRoundCorner, fullscreen = $fullscreen, 
            |topSpacedPixels = $topSpacedPixels, cancelable = $cancelable, hideable = $hideable, 
            |skipCollapsed = $skipCollapsed
            |)
        """.trimMargin()
    }

    companion object {
        private const val OPTION_KEY_NO_DIM = "NoDim"
        private const val OPTION_KEY_TOP_ROUND_CORNER = "TopRoundCorner"
        private const val OPTION_KEY_FULLSCREEN = "Fullscreen"
        private const val OPTION_KEY_TOP_SPACED_PIXELS = "TopSpacedPixels"
        private const val OPTION_KEY_CANCELABLE = "Cancelable"
        private const val OPTION_KEY_HIDEABLE = "Hideable"
        private const val OPTION_KEY_SKIP_COLLAPSED = "SkipCollapsed"

        fun resolveBundle(bundle: Bundle?): BottomSheetOptions {
            val options = BottomSheetOptions()
            bundle ?: return options

            options.noDim = bundle.getBoolean(OPTION_KEY_NO_DIM, options.noDim)
            options.topRoundCorner = bundle.getBoolean(OPTION_KEY_TOP_ROUND_CORNER, options.topRoundCorner)
            options.fullscreen = bundle.getBoolean(OPTION_KEY_FULLSCREEN, options.fullscreen)
            options.topSpacedPixels = bundle.getInt(OPTION_KEY_TOP_SPACED_PIXELS, options.topSpacedPixels)
            options.cancelable = bundle.getBoolean(OPTION_KEY_CANCELABLE, options.cancelable)
            options.hideable = bundle.getBoolean(OPTION_KEY_HIDEABLE, options.hideable)
            options.skipCollapsed = bundle.getBoolean(OPTION_KEY_SKIP_COLLAPSED, options.skipCollapsed)

            return options
        }
    }

}