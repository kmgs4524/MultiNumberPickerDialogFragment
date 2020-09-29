package com.york.multinumberpickerdialogfragment.strategy

import android.view.ViewGroup
import com.york.multinumberpickerdialogfragment.NumberPickerType
import com.york.multinumberpickerdialogfragment.R

class TwoDigitDynamicSettingStrategy(
    container: ViewGroup,
    bounds: Bounds,
    numberPickerType: NumberPickerType
) : DynamicSettingStrategy(container, bounds, numberPickerType) {

    override fun initDigitNumbers() {
        val minValue = bounds.first
        val maxValue = bounds.second

        when (numberPickerType) {
            // 如果是整數一位小數一位 ex: 3.2
            // 取個位數數字則 3.2 轉成 int 得到 3
            // 取小數一位數字則 3.2 * 10 轉 int 再 % 10 得到 2
            is NumberPickerType.OneDigitTenth -> {
                digitNumbers["MIN_VALUE_LEFT_DIGIT"] = minValue.toInt()
                digitNumbers["MIN_VALUE_RIGHT_DIGIT"] = (minValue * 10).toInt() % 10
                digitNumbers["MAX_VALUE_LEFT_DIGIT"] = maxValue.toInt()
                digitNumbers["MAX_VALUE_RIGHT_DIGIT"] = (maxValue * 10).toInt() % 10
            }
            // 如果是整數二位 ex: 32
            // 取十位數數字則 32.0 轉成 int 再除以 10 得到 3
            // 取個位數字則 32.0 轉 int 再 % 10 得到 2
            is NumberPickerType.TwoDigit -> {
                digitNumbers["MIN_VALUE_LEFT_DIGIT"] = numberPickerType.minValue.toInt() / 10
                digitNumbers["MIN_VALUE_RIGHT_DIGIT"] = numberPickerType.minValue.toInt() % 10
                digitNumbers["MAX_VALUE_LEFT_DIGIT"] = numberPickerType.maxValue.toInt() / 10
                digitNumbers["MAX_VALUE_RIGHT_DIGIT"] = numberPickerType.maxValue.toInt() % 10
            }
        }
    }

    override fun initNumberPickers() {
        when (numberPickerType) {
            is NumberPickerType.OneDigitTenth -> {
                numberPickers["LEFT_DIGIT"] = container.findViewById(R.id.unit_digit)
                numberPickers["RIGHT_DIGIT"] = container.findViewById(R.id.tenth)
            }
            is NumberPickerType.TwoDigit -> {
                numberPickers["LEFT_DIGIT"] = container.findViewById(R.id.ten_digit)
                numberPickers["RIGHT_DIGIT"] = container.findViewById(R.id.unit_digit)
            }
        }
    }

    override fun initDefaultValue() {
        var defaultValueLeftDigitNum = 0
        var defaultValueRightDigitNum = 0
        when (numberPickerType) {
            // 如果是整數一位小數一位 ex: 3.2
            // 取個位數數字只需轉成 int 就能得到 3
            // 取小數一位數字則 3.2 * 10 轉 int 再 % 10 得到 2
            is NumberPickerType.OneDigitTenth -> {
                defaultValueLeftDigitNum = numberPickerType.defaultValue.toInt()
                defaultValueRightDigitNum = (numberPickerType.defaultValue * 10).toInt() % 10
                numberPickers["LEFT_DIGIT"]?.value = defaultValueLeftDigitNum
            }
            // 如果是整數二位 ex: 32
            // 取十位數數字需轉成 int 再除以 10
            // 取個位數字則轉成 int 再 % 10 得到 2
            is NumberPickerType.TwoDigit -> {
                defaultValueLeftDigitNum = numberPickerType.defaultValue.toInt() / 10
                defaultValueRightDigitNum = numberPickerType.defaultValue.toInt() % 10
                numberPickers["LEFT_DIGIT"]?.value = defaultValueLeftDigitNum
            }
        }
        when (defaultValueLeftDigitNum) {
            // 若預設值左位數數字為最小值左位數數字，右位數數字範圍變最小值右位數字 ~ 9
            // ex: 最小值 3.2, 預設值 3.5，右位數數字範圍變 2 ~ 9，右位數預設數字為 5
            digitNumbers["MIN_VALUE_LEFT_DIGIT"] -> {
                numberPickers["RIGHT_DIGIT"]?.apply {
                    minValue = digitNumbers.getValue("MIN_VALUE_RIGHT_DIGIT")
                    maxValue = 9
                    value = defaultValueRightDigitNum
                }
            }
            // 若預設值左位數數字為最大值左位數數字，右位數數字範圍變 0 ~ 最大值右位數字
            // ex: 最大值 9.2, 預設值 9.1，右位數數字範圍變 0 ~ 2，右位數預設數字為 1
            digitNumbers["MAX_VALUE_LEFT_DIGIT"] -> {
                numberPickers["RIGHT_DIGIT"]?.apply {
                    minValue = 0
                    maxValue = digitNumbers.getValue("MAX_VALUE_RIGHT_DIGIT")
                    value = defaultValueRightDigitNum
                }
            }
            // 若預設值左位數數字不是最小值或最大值左位數數字，右位數數字範圍變 0 ~ 9
            // ex: 最小值 3.2，最大值 9.2，預設值為 4.1，右位數數字範圍變 0 ~ 9，右位數預設數字為 1
            else -> {
                numberPickers["RIGHT_DIGIT"]?.apply {
                    minValue = 0
                    maxValue = 9
                    value = defaultValueRightDigitNum
                }
            }
        }
    }

    override fun dynamicChangeBounds() {
        numberPickers["LEFT_DIGIT"]?.apply {
            minValue = digitNumbers.getValue("MIN_VALUE_LEFT_DIGIT")
            maxValue = digitNumbers.getValue("MAX_VALUE_LEFT_DIGIT")
            // 當左邊位數數字改變時，動態改變右邊位數可滑動的數值範圍
            setOnValueChangedListener { _, _, newVal ->
                when (newVal) {
                    // 如果左邊位數數字為最大值數字，則右邊位數數字滑動範圍變 0 ~ 最大值右位數數字
                    // ex: 最大值為 9.6，當左邊位數滑到 9 時，右邊位數數字範圍變 0 ~ 6
                    digitNumbers.getValue("MAX_VALUE_LEFT_DIGIT") -> {
                        numberPickers["RIGHT_DIGIT"]?.minValue = 0
                        numberPickers["RIGHT_DIGIT"]?.maxValue = digitNumbers.getValue("MAX_VALUE_RIGHT_DIGIT")
                    }
                    // 如果左邊位數數字為最小值數字，則右邊位數數字滑動範圍變最小值右位數數字 ~ 9
                    // ex: 最小值為 3.2，當左邊位數滑到 3 時，右邊位數數字範圍變 2 ~ 9
                    digitNumbers.getValue("MIN_VALUE_LEFT_DIGIT") -> {
                        numberPickers["RIGHT_DIGIT"]?.minValue = digitNumbers.getValue("MIN_VALUE_RIGHT_DIGIT")
                        numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                    }
                    // 如果左邊位數數字不是最小值或最大值數字，則右邊位數數字範圍變 0 ~ 9
                    // ex: 最小值為 3.2，最大值為 9.6，當左邊位數滑到 4 時，右邊位數數字範圍變 0 ~ 9
                    else -> {
                        numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                        numberPickers["RIGHT_DIGIT"]?.minValue = 0
                    }
                }
            }
        }
    }
}