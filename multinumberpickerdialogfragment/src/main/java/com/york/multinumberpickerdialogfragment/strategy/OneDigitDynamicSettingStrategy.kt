package com.york.multinumberpickerdialogfragment.strategy

import android.view.View
import com.york.multinumberpickerdialogfragment.NumberPickerType
import com.york.multinumberpickerdialogfragment.R

class OneDigitDynamicSettingStrategy(
    container: View,
    bounds: Bounds,
    numberPickerType: NumberPickerType
) : DynamicSettingStrategy(container, bounds, numberPickerType) {

    override fun initDigitNumbers() {
        // 如果是整數一位，則將最小最大值轉成 int
        digitNumbers["MIN_VALUE_CENTER_DIGIT"] = bounds.first.toInt()
        digitNumbers["MAX_VALUE_CENTER_DIGIT"] = bounds.second.toInt()
    }

    override fun initNumberPickers() {
        numberPickers["CENTER_DIGIT"] = container.findViewById(R.id.unit_digit)
    }

    override fun initDefaultValue() {
        // 如果是整數一位，則將預設值轉成 int
        numberPickers["CENTER_DIGIT"]?.value = numberPickerType.defaultValue.toInt()
    }

    override fun dynamicChangeBounds() {
        // 如果是整數一位，僅設定單一 NumberPicker 的最小最大值
        numberPickers["CENTER_DIGIT"]?.minValue = digitNumbers.getValue("MIN_VALUE_CENTER_DIGIT")
        numberPickers["CENTER_DIGIT"]?.maxValue = digitNumbers.getValue("MAX_VALUE_CENTER_DIGIT")
    }
}