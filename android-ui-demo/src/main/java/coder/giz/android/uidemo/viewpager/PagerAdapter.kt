package coder.giz.android.uidemo.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import coder.giz.android.uidemo.helper.DataGenerator

/**
 * Created by GizFei on 2022/9/4
 */
class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity), PagerFragment.Callback {

    private val mDataList: MutableList<String> = DataGenerator.OperatorSystemList.toMutableList()

    override fun getItemCount(): Int = mDataList.size

    override fun createFragment(position: Int): Fragment {
        return PagerFragment.newInstance(position, mDataList[position]).apply {
            callback = this@PagerAdapter
        }
    }

    override fun getItemId(position: Int): Long {
        return mDataList[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return mDataList.find { it.hashCode().toLong() == itemId } != null
    }

    override fun onClose(data: String) {
        val idx = mDataList.indexOf(data)
        if (idx != -1) {
            mDataList.removeAt(idx)
            notifyItemRemoved(idx)
        }
    }
}