package com.york.multinumberpickerdialogfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import com.york.multinumberpickerdialogfragment.strategy.*

sealed class NumberPickerType(
    val minValue: Float,
    val maxValue: Float,
    val defaultValue: Float
) {
    class OneDigit(
        @FloatRange(from = 0.0, to = 9.9) minValue: Float,
        @FloatRange(from = 0.0, to = 9.9) maxValue: Float,
        defaultValue: Float
    ) : NumberPickerType(minValue, maxValue, defaultValue) {
        override fun inflateView(inflater: LayoutInflater, dialogContentView: ViewGroup): View {
            return inflater.inflate(
                R.layout.one_digit_picker,
                dialogContentView,
                false
            )
        }
    }

    class OneDigitTenth(
        @FloatRange(from = 0.0, to = 9.9) minValue: Float,
        @FloatRange(from = 0.0, to = 9.9) maxValue: Float,
        defaultValue: Float
    ) : NumberPickerType(minValue, maxValue, defaultValue) {
        override fun inflateView(inflater: LayoutInflater, dialogContentView: ViewGroup): View {
            return inflater.inflate(
                R.layout.one_digit_tenth_picker,
                dialogContentView,
                false
            )
        }
    }

    class OneDigitHundredth(
        @FloatRange(from = 0.0, to = 9.99) minValue: Float,
        @FloatRange(from = 0.0, to = 9.99) maxValue: Float,
        defaultValue: Float
    ) : NumberPickerType(minValue, maxValue, defaultValue) {
        override fun inflateView(inflater: LayoutInflater, dialogContentView: ViewGroup): View {
            return inflater.inflate(
                R.layout.one_digit_hundredth_picker,
                dialogContentView,
                false
            )
        }
    }

    class TwoDigit(
        @FloatRange(from = 0.0, to = 99.0) minValue: Float,
        @FloatRange(from = 0.0, to = 99.0) maxValue: Float,
        defaultValue: Float
    ) : NumberPickerType(minValue, maxValue, defaultValue) {
        override fun inflateView(inflater: LayoutInflater, dialogContentView: ViewGroup): View {
            return inflater.inflate(
                R.layout.two_digit_picker,
                dialogContentView,
                false
            )
        }
    }

    class TwoDigitTenth(
        @FloatRange(from = 0.0, to = 99.9) minValue: Float,
        @FloatRange(from = 0.0, to = 99.9) maxValue: Float,
        defaultValue: Float
    ) : NumberPickerType(minValue, maxValue, defaultValue) {
        override fun inflateView(inflater: LayoutInflater, dialogContentView: ViewGroup): View {
            return inflater.inflate(
                R.layout.two_digit_tenth_picker,
                dialogContentView,
                false
            )
        }
    }

    class ThreeDigit(
        @FloatRange(from = 0.0, to = 999.0) minValue: Float,
        @FloatRange(from = 0.0, to = 999.0) maxValue: Float,
        defaultValue: Float
    ) : NumberPickerType(minValue, maxValue, defaultValue) {
        override fun inflateView(inflater: LayoutInflater, dialogContentView: ViewGroup): View {
            return inflater.inflate(
                R.layout.three_digit_picker,
                dialogContentView,
                false
            )
        }
    }

    private lateinit var strategy: DynamicSettingStrategy

    fun create(inflater: LayoutInflater, dialogContentView: ViewGroup): View {
        val container = inflateView(inflater, dialogContentView) as ViewGroup
        strategy = when (this) {
            is OneDigit -> OneDigitDynamicSettingStrategy(container, Bounds(minValue, maxValue), this)
            is OneDigitTenth -> TwoDigitDynamicSettingStrategy(container, Bounds(minValue, maxValue), this)
            is OneDigitHundredth -> ThreeDigitDynamicSettingStrategy(container, Bounds(minValue, maxValue), this)
            is TwoDigit -> TwoDigitDynamicSettingStrategy(container, Bounds(minValue, maxValue), this)
            is TwoDigitTenth -> ThreeDigitDynamicSettingStrategy(container, Bounds(minValue, maxValue), this)
            is ThreeDigit -> ThreeDigitDynamicSettingStrategy(container, Bounds(minValue, maxValue), this)
        }
        strategy.initDigitNumbers()
        strategy.initNumberPickers()
        strategy.dynamicChangeBounds()
        strategy.initDefaultValue()
        return container
    }

    fun getSelectedValue(): Float = strategy.getValue()

    abstract fun inflateView(inflater: LayoutInflater, dialogContentView: ViewGroup): View
}