package com.tboul.sudoku.utils

import android.util.TypedValue
import android.view.View

fun View.dpToPx(dp: Number) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()