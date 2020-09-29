package com.york.multinumberpickerdialogfragment

import android.content.Context

object DensityUtils {
    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * getDensity(context)
    }

    private fun getDensity(context: Context): Float {
        val metrics = context.resources.displayMetrics
        return metrics.density
    }
}