package coder.giz.android.uidemo.viewdata

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Created by GizFei on 2022/9/8
 */

open class OptionsViewData

class OneOptionViewData(
    val text: String,
    @DrawableRes
    val imageId: Int,
) : OptionsViewData() {
    constructor(
        context: Context,
        @StringRes textId: Int,
        @DrawableRes imageId: Int,
    ): this(context.getString(textId), imageId)
}

class TwoOptionsViewData(
    val optionOneText: String,
    @DrawableRes
    val optionOneImageId: Int,
    val optionTwoText: String,
    @DrawableRes
    val optionTwoImageId: Int,
) : OptionsViewData() {
    constructor(
        context: Context,
        @StringRes optionOneTextId: Int,
        @DrawableRes optionOneImageId: Int,
        @StringRes optionTwoTextId: Int,
        @DrawableRes optionTwoImageId: Int,
    ): this(
        context.getString(optionOneTextId),
        optionOneImageId,
        context.getString(optionTwoTextId),
        optionTwoImageId,
    )
}

class ThreeOptionsViewData(
    val optionOneText: String,
    @DrawableRes
    val optionOneImageId: Int,
    val optionTwoText: String,
    @DrawableRes
    val optionTwoImageId: Int,
    val optionThreeText: String,
    @DrawableRes
    val optionThreeImageId: Int,
) : OptionsViewData() {
    constructor(
        context: Context,
        @StringRes optionOneTextId: Int,
        @DrawableRes optionOneImageId: Int,
        @StringRes optionTwoTextId: Int,
        @DrawableRes optionTwoImageId: Int,
        @StringRes optionThreeTextId: Int,
        @DrawableRes optionThreeImageId: Int,
    ): this(
        context.getString(optionOneTextId),
        optionOneImageId,
        context.getString(optionTwoTextId),
        optionTwoImageId,
        context.getString(optionThreeTextId),
        optionThreeImageId,
    )
}

class MultiOptionsViewData(
    val optionOneText: String,
    @DrawableRes
    val optionOneImageId: Int,
    val optionTwoText: String? = null,
    @DrawableRes
    val optionTwoImageId: Int? = null,
    val optionThreeText: String? = null,
    @DrawableRes
    val optionThreeImageId: Int? = null,
)