package com.tboul.sudoku.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.databinding.BindingAdapter
import com.github.clans.fab.FloatingActionButton
import com.tboul.sudoku.R

fun View.dpToPx(dp: Number) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toInt()

fun String.concatenated(context: Context): String {
    var concatenated = this

    while (concatenated.contains("@")) {
        val indexStart = concatenated.indexOf("$")
        val indexEnd = concatenated.indexOf("@")
        val res = concatenated.substring(indexStart + 1, indexEnd)

           concatenated = concatenated.replaceRange(
            indexStart,
            indexEnd + 1,
            context.getString(getStringId(res, R.string::class.java))
        )
    }

    return concatenated
}

fun getStringId(resName: String, c: Class<R.string>): Int {
    return try {
        val idField = c.getDeclaredField(resName)
        idField.getInt(idField)
    } catch (e: Exception) {
        e.printStackTrace()
        -1
    }
}

@BindingAdapter("label")
fun setFabLabel(view: FloatingActionButton, label: String) {
    view.labelText = label
}