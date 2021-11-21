package coder.giz.android.architecture.components.uilayer.databinding

import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.yfui.recyclerview.adapter.SimpleListItemOneAdapter
import coder.giz.android.yfui.recyclerview.adapter.SimpleListItemTwoAdapter

/**
 * Created by GizFei on 2021/11/2
 */

@BindingAdapter("stringList")
fun setListViewStringsData(listView: ListView, data: List<String>) {
    val key = "Key"
    listView.adapter = SimpleAdapter(
        listView.context,
        data.map { mapOf(key to it) },
        android.R.layout.simple_list_item_1,
        arrayOf(key),
        intArrayOf(android.R.id.text1)
    )
}

@BindingAdapter("stringList")
fun setRecyclerViewStringsData(recyclerView: RecyclerView, data: List<String>) {
    recyclerView.adapter = SimpleListItemOneAdapter().apply {
        updateData(data)
    }
}

@BindingAdapter("stringMap")
fun setListViewMapData(listView: ListView, mapData: Map<String, String>) {
    val key = "Key"
    val value = "Value"
    listView.adapter = SimpleAdapter(
        listView.context,
        mapData.map { mapOf(key to it.key, value to it.value) },
        android.R.layout.simple_list_item_2,
        arrayOf(key, value),
        intArrayOf(android.R.id.text1, android.R.id.text2)
    )
}

@BindingAdapter("stringMap")
fun setRecyclerViewMapData(recyclerView: RecyclerView, mapData: Map<String, String>) {
    recyclerView.adapter = SimpleListItemTwoAdapter().apply {
        updateData(mapData.map { it.key to it.value })
    }
}