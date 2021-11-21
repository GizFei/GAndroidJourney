package coder.giz.android.uidemo.helper

import android.view.Gravity

/**
 * Created by GizFei on 2021/11/14
 */
data class GravityItem(
    val gravity: Int,
    val description: String,
) {
    override fun toString(): String = description
}

@JvmField
val GravityItemSpinnerEntries = listOf(
    GravityItem(Gravity.NO_GRAVITY, "Gravity.NO_GRAVITY"),
    GravityItem(Gravity.START, "Gravity.START"),
    GravityItem(Gravity.TOP, "Gravity.TOP"),
    GravityItem(Gravity.END, "Gravity.END"),
    GravityItem(Gravity.BOTTOM, "Gravity.BOTTOM"),
    GravityItem(Gravity.START or Gravity.TOP, "Gravity.START or Gravity.TOP"),
    GravityItem(Gravity.END or Gravity.TOP, "Gravity.END or Gravity.TOP"),
    GravityItem(Gravity.START or Gravity.BOTTOM, "Gravity.START or Gravity.BOTTOM"),
    GravityItem(Gravity.END or Gravity.BOTTOM, "Gravity.END or Gravity.BOTTOM"),
)