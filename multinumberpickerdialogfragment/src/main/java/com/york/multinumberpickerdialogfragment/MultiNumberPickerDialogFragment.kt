package com.york.multinumberpickerdialogfragment

import android.graphics.ColorSpace.connect
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.DialogFragment

class MultiNumberPickerDialogFragment(
    private val title: String,
    private val numberPickerType: NumberPickerType,
    private val onValueChanged: (Float) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogMultiNumberPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogMultiNumberPickerBinding.inflate(inflater, container, false).apply {
            title.text = this@MultiNumberPickerDialogFragment.title
        }
        val dialogContentView = binding.root as ConstraintLayout
        val numberPickerContainer = numberPickerType.create(inflater, dialogContentView)

        // 將各個 type 的 number picker 加進來並設在 dialog 中間
        numberPickerContainer.id = View.generateViewId()
        dialogContentView.addView(numberPickerContainer.apply {
            layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0)
        })
        ConstraintSet().apply {
            clone(dialogContentView)
            connect(
                numberPickerContainer.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            connect(
                numberPickerContainer.id,
                ConstraintSet.TOP,
                binding.title.id,
                ConstraintSet.BOTTOM,
                DensityUtils.convertDpToPixel(10f, requireContext()).toInt()
            )
            connect(
                numberPickerContainer.id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            connect(
                numberPickerContainer.id,
                ConstraintSet.BOTTOM,
                binding.cancel.id,
                ConstraintSet.TOP,
                DensityUtils.convertDpToPixel(18f, requireContext()).toInt()
            )
            constrainDefaultHeight(numberPickerContainer.id, ConstraintSet.MATCH_CONSTRAINT_SPREAD)
            applyTo(dialogContentView)
        }
        initConfirmListener()
        initCancelListener()
        return dialogContentView
    }

    private fun initConfirmListener() {
        binding.confirm.setOnClickListener {
            onValueChanged(numberPickerType.getSelectedValue())
            dismiss()
        }
    }

    private fun initCancelListener() {
        binding.cancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val window = dialog?.window
        window?.setLayout(
            resources.getDimensionPixelSize(R.dimen.adjust_dialog_width),
            resources.getDimensionPixelSize(R.dimen.adjust_dialog_height)
        )
        window?.setGravity(Gravity.CENTER)
    }

    companion object {
        const val MULTI_NUMBER_PICKER_DIALOG = "MULTI_NUMBER_PICKER_DIALOG"
    }
}
