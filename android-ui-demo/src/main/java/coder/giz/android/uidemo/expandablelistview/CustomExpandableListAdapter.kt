package coder.giz.android.uidemo.expandablelistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import coder.giz.android.uidemo.databinding.ItemElvChildViewBinding
import coder.giz.android.uidemo.databinding.ItemElvGroupHeaderBinding

/**
 * Created by GizFei on 2021/12/6
 */
class CustomExpandableListAdapter(
    private val context: Context,
    private val groupTitles: List<String>,
    private val dataDetails: Map<String, List<String>>
) : BaseExpandableListAdapter() {

    private val mLayoutInflater = LayoutInflater.from(context)

    override fun getGroupCount(): Int = groupTitles.size

    override fun getChildrenCount(groupPosition: Int): Int =
        dataDetails[groupTitles[groupPosition]]?.size ?: 0

    override fun getGroup(groupPosition: Int): String = groupTitles[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): String? {
        return dataDetails[groupTitles[groupPosition]]?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val groupTitle = groupTitles[groupPosition]
        val binding = if (convertView == null) {
            ItemElvGroupHeaderBinding.inflate(mLayoutInflater, parent, false)
        } else {
            convertView.tag as ItemElvGroupHeaderBinding
        }
        binding.tvTitle.text = groupTitle
        binding.root.tag = binding
        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val text = getChild(groupPosition, childPosition)
        val binding = if (convertView == null) {
            ItemElvChildViewBinding.inflate(mLayoutInflater, parent, false)
        } else {
            convertView.tag as ItemElvChildViewBinding
        }
        binding.tvTitle.text = text
        binding.root.tag = binding
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
}