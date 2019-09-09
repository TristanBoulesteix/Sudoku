package com.tboul.sudoku.views.components

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.tboul.sudoku.R
import com.tboul.sudoku.utils.concatenated

class HtmlTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    override fun setText(text: CharSequence?, type: BufferType?) {
        val htmlImageGetter = Html.ImageGetter {
            when (it) {
                "sudoku_example" -> ContextCompat.getDrawable(context!!, R.drawable.sudoku_example)?.apply { setBounds(0,0,500,500) }
                "sudoku_bottom_button" -> ContextCompat.getDrawable(context!!, R.drawable.sudoku_bottom_button)?.apply { setBounds(0, 0,500,250) }
                else -> ContextCompat.getDrawable(context!!, R.drawable.sudoku_example)?.apply { setBounds(0,0,100,100) }
            }
        }

        super.setText(
            HtmlCompat.fromHtml(
                text.toString().concatenated(context),
                HtmlCompat.FROM_HTML_MODE_LEGACY,
                htmlImageGetter,
                null
            ), type
        )
    }
}