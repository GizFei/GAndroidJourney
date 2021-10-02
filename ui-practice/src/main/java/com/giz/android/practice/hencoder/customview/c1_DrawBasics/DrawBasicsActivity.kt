package com.giz.android.practice.hencoder.customview.c1_DrawBasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.giz.android.practice.R
import com.giz.android.practice.databinding.ActivityDrawBasicsBinding

class DrawBasicsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDrawBasicsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_draw_basics)

        initView()
        Log.w("Window", "onCreate: ${window::class.simpleName}[${window}]")
    }

    private fun initView() {
        mBinding.run {
            viewPager.adapter = DrawBasicsAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

            tabLayout.setupWithViewPager(viewPager)
        }
    }

    private class DrawBasicsAdapter(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {

        private val mTitleLayoutPairList = listOf(
            "drawColor" to R.layout.page_drawbasics_draw_color,
            "drawCircle" to R.layout.page_drawbasics_draw_circle,
            "drawRect" to R.layout.page_drawbasics_draw_rect,
            "drawRoundRect" to R.layout.page_drawbasics_draw_roundrect,
            "drawPoint" to R.layout.page_drawbasics_draw_point,
            "drawOval" to R.layout.page_drawbasics_draw_oval,
            "drawLine" to R.layout.page_drawbasics_draw_line,
            "drawArc" to R.layout.page_drawbasics_draw_arc,
            "drawPath" to R.layout.page_drawbasics_draw_path,
            "Histogram" to R.layout.page_drawbasics_draw_bitmap,
            "PieChart" to R.layout.page_drawbasics_draw_circle,
            "DynamicPath" to R.layout.page_drawbasics_draw_dynamic_path
        )

        override fun getItem(position: Int): Fragment = if (position != mTitleLayoutPairList.lastIndex)
            PageFragment.newInstance(mTitleLayoutPairList[position].second) else DynamicPathPageFragment()

        override fun getCount(): Int = mTitleLayoutPairList.size

        override fun getPageTitle(position: Int): CharSequence? = mTitleLayoutPairList[position].first

    }
}