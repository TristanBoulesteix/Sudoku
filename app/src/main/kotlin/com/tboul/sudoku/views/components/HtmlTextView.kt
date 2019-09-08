package com.tboul.sudoku.views.components

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.tboul.sudoku.R

class HtmlTextView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {
    override fun setText(text: CharSequence?, type: BufferType?) {
        val htmlImageGetter = Html.ImageGetter {
            val id = when (it) {
                "sudoku_example" -> R.drawable.sudoku_example
                "sudoku_bottom_button" -> R.drawable.sudoku_bottom_button
                else -> R.drawable.sudoku_example
            }

            val drawable = ContextCompat.getDrawable(context!!, id)
            drawable?.setBounds(0, 0, 500, 500)

            drawable!!
        }

        super.setText(
            HtmlCompat.fromHtml(
                text.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY,
                htmlImageGetter,
                null
            ), type
        )
    }
}