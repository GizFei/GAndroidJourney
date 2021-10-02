package coder.giz.android.bottomsheet.util

import kotlin.reflect.KClass

/**
 * BottomSheet特性实现处注解
 *
 * Created by GizFei on 2021/7/3
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION, AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class BottomSheetFeatureImpl(vararg val features: KClass<out BottomSheetFeature>)
