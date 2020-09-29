package com.york.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.york.multinumberpickerdialogfragment.MultiNumberPickerDialogFragment
import com.york.multinumberpickerdialogfragment.NumberPickerType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog.setOnClickListener {
            val dialogFragment = MultiNumberPickerDialogFragment(
                "NumberPickerDialog",
                NumberPickerType.TwoDigit(0f, 20f, 5f)
            ) {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
            dialogFragment.show(supportFragmentManager, "ONE_DIGIT")
        }
    }
}
