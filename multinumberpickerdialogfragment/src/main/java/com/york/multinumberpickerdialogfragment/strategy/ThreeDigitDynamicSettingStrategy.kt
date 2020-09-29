package com.york.multinumberpickerdialogfragment.strategy

import android.view.ViewGroup
import com.york.multinumberpickerdialogfragment.NumberPickerType
import com.york.multinumberpickerdialogfragment.R

class ThreeDigitDynamicSettingStrategy(
    container: ViewGroup,
    bounds: Bounds,
    numberPickerType: NumberPickerType
) : DynamicSettingStrategy(container, bounds, numberPickerType) {

    override fun initDigitNumbers() {
        when (numberPickerType) {
            // 如果是整數一位小數兩位 ex: 3.26
            // 取個位數數字只需轉成 int 就能得到 3
            // 取小數一位數字則 3.26 * 10 轉 int 再 % 10 得到 2
            // 取小數二位數字則 3.26 * 100 轉 int 再 % 10 得到 6
            is NumberPickerType.OneDigitHundredth -> {
                digitNumbers["MIN_VALUE_LEFT_DIGIT"] = numberPickerType.minValue.toInt()
                digitNumbers["MIN_VALUE_CENTER_DIGIT"] =
                    (numberPickerType.minValue * 10).toInt() % 10
                digitNumbers["MIN_VALUE_RIGHT_DIGIT"] =
                    (numberPickerType.minValue * 100).toInt() % 10
                digitNumbers["MAX_VALUE_LEFT_DIGIT"] = numberPickerType.maxValue.toInt()
                digitNumbers["MAX_VALUE_CENTER_DIGIT"] =
                    (numberPickerType.maxValue * 10).toInt() % 10
                digitNumbers["MAX_VALUE_RIGHT_DIGIT"] =
                    (numberPickerType.maxValue * 100).toInt() % 10
            }
            // 如果是整數兩位小數一位 ex: 32.6
            // 取十位數數字需 32.6 轉 int 得 32，再 / 10 得到 3
            // 取個位數字則  32.6 轉 int 得 32，再 % 10 得到 2
            // 取小數一位數字則 32.6 * 10 轉 int 得 326，再 % 10 得到 6
            is NumberPickerType.TwoDigitTenth -> {
                digitNumbers["MIN_VALUE_LEFT_DIGIT"] = numberPickerType.minValue.toInt() / 10
                digitNumbers["MIN_VALUE_CENTER_DIGIT"] = numberPickerType.minValue.toInt() % 10
                digitNumbers["MIN_VALUE_RIGHT_DIGIT"] =
                    (numberPickerType.minValue * 10).toInt() % 10
                digitNumbers["MAX_VALUE_LEFT_DIGIT"] = numberPickerType.maxValue.toInt() / 10
                digitNumbers["MAX_VALUE_CENTER_DIGIT"] = numberPickerType.maxValue.toInt() % 10
                digitNumbers["MAX_VALUE_RIGHT_DIGIT"] =
                    (numberPickerType.maxValue * 10).toInt() % 10
            }
            // 如果是整數三位 ex: 326
            // 取百位數字需 326 轉 int 再 / 100 得 3
            // 取十位數字則 326 轉 int 再 % 100 / 10 得 2
            // 取個位數字則 326 轉 int 再 % 10 得 6
            is NumberPickerType.ThreeDigit -> {
                digitNumbers["MIN_VALUE_LEFT_DIGIT"] = numberPickerType.minValue.toInt() / 100
                digitNumbers["MIN_VALUE_CENTER_DIGIT"] =
                    numberPickerType.minValue.toInt() % 100 / 10
                digitNumbers["MIN_VALUE_RIGHT_DIGIT"] = numberPickerType.minValue.toInt() % 10
                digitNumbers["MAX_VALUE_LEFT_DIGIT"] = numberPickerType.maxValue.toInt() / 100
                digitNumbers["MAX_VALUE_CENTER_DIGIT"] =
                    numberPickerType.maxValue.toInt() % 100 / 10
                digitNumbers["MAX_VALUE_RIGHT_DIGIT"] = numberPickerType.maxValue.toInt() % 10
            }
        }
    }

    override fun initNumberPickers() {
        when (numberPickerType) {
            is NumberPickerType.OneDigitHundredth -> {
                numberPickers["LEFT_DIGIT"] = container.findViewById(R.id.unit_digit)
                numberPickers["CENTER_DIGIT"] = container.findViewById(R.id.tenth)
                numberPickers["RIGHT_DIGIT"] = container.findViewById(R.id.hundredth)
            }
            is NumberPickerType.TwoDigitTenth -> {
                numberPickers["LEFT_DIGIT"] = container.findViewById(R.id.ten_digit)
                numberPickers["CENTER_DIGIT"] = container.findViewById(R.id.unit_digit)
                numberPickers["RIGHT_DIGIT"] = container.findViewById(R.id.tenth)
            }
            is NumberPickerType.ThreeDigit -> {
                numberPickers["LEFT_DIGIT"] = container.findViewById(R.id.hundred_digit)
                numberPickers["CENTER_DIGIT"] = container.findViewById(R.id.ten_digit)
                numberPickers["RIGHT_DIGIT"] = container.findViewById(R.id.unit_digit)
            }
        }
    }

    override fun initDefaultValue() {
        var defaultValueLeftDigitNum = 0
        var defaultValueCenterDigitNum = 0
        var defaultValueRightDigitNum = 0
        when (numberPickerType) {
            // 如果是整數一位小數兩位 ex: 3.26
            // 取個位數數字只需轉成 int 就能得到 3
            // 取小數一位數字則 3.26 * 10 轉 int 再 % 10 得到 2
            // 取小數二位數字則 3.26 * 100 轉 int 再 % 10 得到 6
            is NumberPickerType.OneDigitHundredth -> {
                defaultValueLeftDigitNum = (numberPickerType.defaultValue).toInt()
                defaultValueCenterDigitNum = ((numberPickerType.defaultValue * 10).toInt() % 10)
                defaultValueRightDigitNum = ((numberPickerType.defaultValue * 100).toInt() % 10)
            }
            // 如果是整數二位小數一位 ex: 32.6
            // 取十位數數字需 32.6 轉 int 得 32，再 / 10 得到 3
            // 取個位數字則 32.6 轉 int 得 32，再 % 10 得到 2
            // 取小數一位數字則 32.6 * 10 轉 int 得 326，再 % 10 得到 6
            is NumberPickerType.TwoDigitTenth -> {
                defaultValueLeftDigitNum = numberPickerType.defaultValue.toInt() / 10
                defaultValueCenterDigitNum = numberPickerType.defaultValue.toInt() % 10
                defaultValueRightDigitNum = ((numberPickerType.defaultValue * 10).toInt() % 10)
            }
            // 如果是整數三位 ex: 326
            // 取百位數字需 326 轉 int 再 / 100 得 3
            // 取十位數字則 326 轉 int 再 % 100 / 10 得 2
            // 取個位數字則 326 轉 int 再 % 10 得 6
            is NumberPickerType.ThreeDigit -> {
                defaultValueLeftDigitNum = numberPickerType.defaultValue.toInt() / 100
                defaultValueCenterDigitNum = numberPickerType.defaultValue.toInt() % 100 / 10
                defaultValueRightDigitNum = numberPickerType.defaultValue.toInt() % 10
            }
        }

        numberPickers["LEFT_DIGIT"]?.value = defaultValueLeftDigitNum
        when (defaultValueLeftDigitNum) {
            digitNumbers["MIN_VALUE_LEFT_DIGIT"] -> {
                // 若預設值左位數數字為最小值左位數數字，中間位數數字範圍變最小值中間位數字 ~ 9
                // ex: 最小值 3.23, 預設值 3.56，中間位數數字範圍變 2 ~ 9，中間位數預設數字為 5
                numberPickers["CENTER_DIGIT"]?.apply {
                    minValue = digitNumbers.getValue("MIN_VALUE_CENTER_DIGIT")
                    maxValue = 9
                    value = defaultValueCenterDigitNum
                }
                if (defaultValueCenterDigitNum == digitNumbers["MIN_VALUE_CENTER_DIGIT"]) {
                    // 若預設值中位數數字為最小值中間位數數字，右位數數字範圍變最小值右位數字 ~ 9
                    // ex: 最小值 3.23, 預設值 3.26，右位數數字範圍變 3 ~ 9，右位數預設數字為 6
                    numberPickers["RIGHT_DIGIT"]?.apply {
                        minValue = digitNumbers.getValue("MIN_VALUE_RIGHT_DIGIT")
                        maxValue = 9
                        value = defaultValueRightDigitNum
                    }
                } else {
                    // 若預設值中間位數數字不是最小值中間位數字，右位數數字範圍變 0 ~ 9
                    // ex: 最小值 3.23, 預設值 3.56，右位數數字範圍變 0 ~ 9，右位數預設數字為 6
                    numberPickers["RIGHT_DIGIT"]?.apply {
                        minValue = 0
                        maxValue = 9
                        value = defaultValueRightDigitNum
                    }
                }
            }
            digitNumbers["MAX_VALUE_LEFT_DIGIT"] -> {
                // 若預設值左位數數字為最大值左位數數字，中間位數數字範圍變 0 ~ 最大值中間位數字
                // ex: 最大值 9.38, 預設值 9.26，中間位數數字範圍變 0 ~ 3，中間位數預設數字為 2
                numberPickers["CENTER_DIGIT"]?.apply {
                    minValue = 0
                    maxValue = digitNumbers.getValue("MAX_VALUE_CENTER_DIGIT")
                    value = defaultValueCenterDigitNum
                }
                if (defaultValueCenterDigitNum == digitNumbers["MAX_VALUE_CENTER_DIGIT"]) {
                    // 若預設值中位數數字為最大值中間位數數字，右位數數字範圍變 0 ~ 最大值右位數字
                    // ex: 最大值 9.38, 預設值 9.36，右位數數字範圍變 0 ~ 8，右位數預設數字為 6
                    numberPickers["RIGHT_DIGIT"]?.apply {
                        minValue = 0
                        maxValue = digitNumbers.getValue("MAX_VALUE_RIGHT_DIGIT")
                        value = defaultValueRightDigitNum
                    }
                } else {
                    // 若預設值中位數數字不是最大值中間位數數字，右位數數字範圍變 0 ~ 最大值右位數字
                    // ex: 最大值 9.38, 預設值 9.26，右位數數字範圍變 0 ~ 9，右位數預設數字為 6
                    numberPickers["RIGHT_DIGIT"]?.apply {
                        minValue = 0
                        maxValue = 9
                        value = defaultValueRightDigitNum
                    }
                }
            }
            // 若預設值左位數數字不是最小值或最大值左位數數字，中間位及右位數數字範圍變 0 ~ 9
            // ex: 最小值 3.23，最大值 9.38，預設值為 4.12，中間位及右位數數字範圍變 0 ~ 9，中間位數預設數字為 1，右位數預設數字為 2
            else -> {
                numberPickers["CENTER_DIGIT"]?.apply {
                    minValue = 0
                    maxValue = 9
                    value = defaultValueCenterDigitNum
                }
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
            // 當左邊位數數字改變時，動態改變中間位及右邊位數可滑動的數值範圍
            setOnValueChangedListener { _, _, newVal ->
                when (newVal) {
                    // 如果左位數數字是最大值左位數數字，則中間位數字範圍變 0 ~ 最大值中間位數字
                    // ex: 最大值是 9.38，左位數數字滑到 9 時，中間位數範圍變 0 ~ 3
                    digitNumbers.getValue("MAX_VALUE_LEFT_DIGIT") -> {
                        numberPickers["CENTER_DIGIT"]?.minValue = 0
                        numberPickers["CENTER_DIGIT"]?.maxValue =
                            digitNumbers.getValue("MAX_VALUE_CENTER_DIGIT")
                        when (numberPickers["CENTER_DIGIT"]?.value) {
                            // 如果中間位數字也是最大值中間位數字，則右位數數字範圍變 0 ~ 最大值右位數數字
                            // ex: 最大值是 9.38，左位數數字滑到 9 的同時中間位數數字是 3，右位數數字範圍變 0 ~ 8
                            digitNumbers.getValue("MAX_VALUE_CENTER_DIGIT") -> {
                                numberPickers["RIGHT_DIGIT"]?.maxValue =
                                    digitNumbers.getValue("MAX_VALUE_RIGHT_DIGIT")
                                numberPickers["RIGHT_DIGIT"]?.minValue = 0
                            }
                            // 如果中間位數字不是最大值中間位數字，則右位數數字範圍變 0 ~ 9
                            // ex: 最大值是 9.33，左位數數字滑到 9 的同時中間位數數字是 2，右位數數字範圍變 0 ~ 9
                            else -> {
                                numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                                numberPickers["RIGHT_DIGIT"]?.minValue = 0
                            }
                        }
                    }
                    // 如果左位數數字是最小值左位數數字，則中間位數字範圍變最小值中間位數字 ~ 9
                    // ex: 最小值是 3.23，左邊位數數字滑到 3 時，中間位數範圍變 2 ~ 9
                    digitNumbers.getValue("MIN_VALUE_LEFT_DIGIT") -> {
                        numberPickers["CENTER_DIGIT"]?.minValue =
                            digitNumbers.getValue("MIN_VALUE_CENTER_DIGIT")
                        numberPickers["CENTER_DIGIT"]?.maxValue = 9
                        when (numberPickers["CENTER_DIGIT"]?.value) {
                            // 如果中間位數字也是最小值中間位數字，則右位數範圍變最小值右位數數字 ~ 9
                            // ex: 最小值是 3.23，左位數數字滑到 3 的同時中間位數數字是 2，右位數數字範圍變 3 ~ 9
                            digitNumbers.getValue("MIN_VALUE_CENTER_DIGIT") -> {
                                numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                                numberPickers["RIGHT_DIGIT"]?.minValue =
                                    digitNumbers.getValue("MIN_VALUE_RIGHT_DIGIT")
                            }
                            // 如果中間位數字不是最小值中間位數字，則右邊位數範圍變 0 ~ 9
                            // ex: 最小值是 3.23，左位數數字滑到 3，中間位數字是 4 時，右位數數字範圍變 0 ~ 9
                            else -> {
                                numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                                numberPickers["RIGHT_DIGIT"]?.minValue = 0
                            }
                        }
                    }
                    // 如果左位數數字不是最小值或最大值左位數數字，則中間位和右位數數字範圍變 0 ~ 9
                    // ex: 最小值是 3.23，最大值為 9.38，左位數數字滑到 5 時，中間位及右位數數字範圍變 0 ~ 9
                    else -> {
                        numberPickers["CENTER_DIGIT"]?.maxValue = 9
                        numberPickers["CENTER_DIGIT"]?.minValue = 0
                        numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                        numberPickers["RIGHT_DIGIT"]?.minValue = 0
                    }
                }
            }
        }
        numberPickers["CENTER_DIGIT"]?.apply {
            minValue = digitNumbers.getValue("MIN_VALUE_CENTER_DIGIT")
            maxValue = 9
            // 當中間位數字改變時，動態改變右邊位數可滑動的數值範圍
            setOnValueChangedListener { _, _, newVal ->
                when (newVal) {
                    // 如果中間位數字滑到最小值數字時，判斷左位數數字是否為最小值數字
                    digitNumbers.getValue("MIN_VALUE_CENTER_DIGIT") -> {
                        when (numberPickers["LEFT_DIGIT"]?.value) {
                            // 如果左位數數字也是最小值數字，右位數數字範圍變最小值右位數字 ~ 9
                            // ex: 最小值是 3.23，中間位數數字滑到 2 的同時左位數數字是 3，右位數數字範圍變 3 ~ 9
                            digitNumbers.getValue("MIN_VALUE_LEFT_DIGIT") -> {
                                numberPickers["RIGHT_DIGIT"]?.minValue =
                                    digitNumbers.getValue("MIN_VALUE_RIGHT_DIGIT")
                                numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                            }
                            // 如果左位數數字不是最小值，則右位數數字範圍變 0 ~ 9
                            // ex: 最小值是 3.23，中間位數數字滑到 2 的同時左位數數字不是 3，右位數數字範圍變 0 ~ 9
                            else -> {
                                numberPickers["RIGHT_DIGIT"]?.minValue = 0
                                numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                            }
                        }
                    }
                    // 如果中間位數數字滑到最大值數字時，判斷左位數數字是否為最大值數字
                    digitNumbers.getValue("MAX_VALUE_CENTER_DIGIT") -> {
                        when (numberPickers["LEFT_DIGIT"]?.value) {
                            // 如果左位數數字也是最大值數字時，右位數數字範圍變 0 ~ 最大值右位數字
                            // ex: 最大值是 9.38，中間位數數字滑到 3 的同時左位數數字是 9，右位數數字範圍變 0 ~ 8
                            digitNumbers.getValue("MAX_VALUE_LEFT_DIGIT") -> {
                                numberPickers["RIGHT_DIGIT"]?.minValue = 0
                                numberPickers["RIGHT_DIGIT"]?.maxValue =
                                    digitNumbers.getValue("MAX_VALUE_RIGHT_DIGIT")
                            }
                            // 如果左位數數字不是最大值數字時，右位數數字範圍變 0 ~ 9
                            // ex: 最大值是 9.38，中間位數數字滑到 3 的同時左位數數字不是 9，右位數數字範圍變 0 ~ 9
                            else -> {
                                numberPickers["RIGHT_DIGIT"]?.minValue = 0
                                numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                            }
                        }
                    }
                    // 如果中間位數字不是最小值或最大值數字時，右位數數字範圍變 0 ~ 9
                    // ex: 最小值是 3.23，最大值是 9.38，中間位數數字滑到 4 時，右位數數字範圍變 0 ~ 9
                    else -> {
                        numberPickers["RIGHT_DIGIT"]?.minValue = 0
                        numberPickers["RIGHT_DIGIT"]?.maxValue = 9
                    }
                }
            }
        }
        numberPickers["RIGHT_DIGIT"]?.apply {
            minValue = digitNumbers.getValue("MIN_VALUE_RIGHT_DIGIT")
            maxValue = 9
        }
    }
}