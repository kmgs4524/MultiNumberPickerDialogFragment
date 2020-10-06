# MultiNumberPickerDialogFragment
多位數字的 NumberPicker，支援多位數字，例如: 1,22,333,1.1,1.11,22.2

![demo_image](https://github.com/kmgs4524/MultiNumberPickerDialogFragment/blob/master/multinumberpickerdialog_demo.gif)

## Setup
請將專案 clone 下來並 import module `multinumberpickerdialogfragment`，並在 build.gradle(app) 啟用 data binding 後即可使用。
```
android {
  ...
  
  dataBinding {
    enabled = true
  }
}
```

## Usage
可選擇以下幾種 NumberPickerType，呈現多位數字。藉由 NumberPickerType 的建構子傳入數值範圍的最小值、最大值及 dialog 顯示的預設值。
* OneDigit : 整數一位，1

* OneDigitTenth : 整數一位小數一位，1.1

* OneDigitHundredth : 整數一位小數二位，1.11

* TwoDigit : 整數二位，11

* TwoDigitTenth : 整數二位小數一位，11.1

* ThreeDigit : 整數三位，111
```
MultiNumberPickerDialogFragment(
    "NumberPickerDialog", // 標題
    NumberPickerType.TwoDigit(10f, 27f, 5f) // TwoDigit(minValue, maxValue, defaultValue)
) { value ->
    // 點擊事件
}.show()
```
