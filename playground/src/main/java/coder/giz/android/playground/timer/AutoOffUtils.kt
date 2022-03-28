package coder.giz.android.playground.timer

/**
 * Created by GizFei on 2022/3/28
 */
object AutoOffUtils {

    /**
     *
     * @param endTime
     */
    fun getMillis(endTime: Int, delayMin: Int) {
        val totalMillis = delayMin * 60 * 1000
        val remainMillis = endTime * 1000 - System.currentTimeMillis()
    }

}