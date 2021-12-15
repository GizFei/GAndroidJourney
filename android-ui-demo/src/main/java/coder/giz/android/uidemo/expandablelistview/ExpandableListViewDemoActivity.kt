package coder.giz.android.uidemo.expandablelistview

import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityExpandableListViewDemoBinding
import coder.giz.android.yfui.base.MvvmBaseActivity
import coder.giz.android.yfutility.components.toast

/**
 * Created by GizFei on 2021/12/5
 */
class ExpandableListViewDemoActivity
    : MvvmBaseActivity<ActivityExpandableListViewDemoBinding, ExpandableListViewDemoViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_expandable_list_view_demo

    override fun getViewModel() = ExpandableListViewDemoViewModel()

    override fun initView() {
        val data = ExpandableListDataGenerator.getData()
        val groupTitles = data.keys.toList()
        val adapter = CustomExpandableListAdapter(this,
            groupTitles, data)
        mBinding.expandableListView.run {
            setAdapter(adapter)
            setOnGroupExpandListener { groupPos ->
                toast("Expand Group Title: ${groupTitles[groupPos]}")
            }
            setOnGroupCollapseListener { groupPos ->
                toast("Collapse Group Title: ${groupTitles[groupPos]}")
            }
            setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                toast("OnChildClick ${groupTitles[groupPosition]} " +
                        "-> ${data[groupTitles[groupPosition]]?.get(childPosition)}。id: $id")
                true
            }
        }
    }

    override fun subscribeToViewModel() {

    }
}