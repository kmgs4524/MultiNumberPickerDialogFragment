package com.york.multinumberpickerdialogfragment.strategy

import android.view.View
import android.widget.NumberPicker
import com.york.multinumberpickerdialogfragment.NumberPickerType

typealias Bounds = Pair<Float, Float>

abstract class DynamicSettingStrategy(
    protected val container: View,
    protected val bounds: Bounds,
    protected val numberPickerType: NumberPickerType
) {
    protected val digitNumbers: HashMap<String, Int> = hashMapOf()
    protected val numberPickers: HashMap<String, NumberPicker> = hashMapOf()

    abstract fun initDigitNumbers()
    abstract fun initNumberPickers()
    abstract fun initDefaultValue()
    abstract fun dynamicChangeBounds()

    fun getValue(): Float {
        var value = 0f
        when (numberPickerType) {
            is NumberPickerType.OneDigit -> {
                // 如果是整數一位，直接取唯一的中間位數數字
                value = numberPickers.getValue("CENTER_DIGIT").value.toFloat()
            }
            is NumberPickerType.OneDigitTenth -> {
                // 如果是整數一位小數一位，ex: 3.2
                // 值 = 個位數字 + 小數一位數字 * 0.1
                value += numberPickers.getValue("LEFT_DIGIT").value
                value += numberPickers.getValue("RIGHT_DIGIT").value * 0.1f
            }
            is NumberPickerType.OneDigitHundredth -> {
                // 如果是整數一位小數兩位，ex: 3.26
                // 值 = 個位數字 + 小數一位數字 * 0.1 + 小數二位數字 * 0.01
                value += numberPickers.getValue("LEFT_DIGIT").value
                value += numberPickers.getValue("CENTER_DIGIT").value * 0.1f
                value += numberPickers.getValue("RIGHT_DIGIT").value * 0.01f
            }
            is NumberPickerType.TwoDigit -> {
                // 如果是整數二位，ex: 32
                // 值 = 十位數字  * 10 + 個位數字
                value += numberPickers.getValue("LEFT_DIGIT").value * 10f
                value += numberPickers.getValue("RIGHT_DIGIT").value
            }
            is NumberPickerType.TwoDigitTenth -> {
                // 如果是整數二位小數一位，ex: 32.6
                // 值 = 十位數字  * 10 + 個位數字 + 小數一位數字 * 0.1
                value += numberPickers.getValue("LEFT_DIGIT").value * 10f
                value += numberPickers.getValue("CENTER_DIGIT").value
                value += numberPickers.getValue("RIGHT_DIGIT").value * 0.1f
            }
            is NumberPickerType.ThreeDigit -> {
                // 如果是整數三位，ex: 326
                // 值 = 百位數字 * 100 + 十位數字 * 10 + 個位數字
                value += numberPickers.getValue("LEFT_DIGIT").value * 100f
                value += numberPickers.getValue("CENTER_DIGIT").value * 10f
                value += numberPickers.getValue("RIGHT_DIGIT").value
            }
        }
        return value
    }
}