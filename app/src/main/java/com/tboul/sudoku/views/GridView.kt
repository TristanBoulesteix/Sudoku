package com.tboul.sudoku.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.tboul.sudoku.models.Grid
import com.tboul.sudoku.utils.SUDOKU_SIZE

class GridView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val numColumnsAndRows = SUDOKU_SIZE
    private val blackPaint = Paint().apply { style = Paint.Style.FILL_AND_STROKE }
    private val boldPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 5F
    }

    private var cellWidth: Int = 0
    private var cellHeight: Int = 0

    init {
        calculateDimensions()
        Grid()
    }

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int) {
        super.onSizeChanged(width, height, oldwidth, oldheight)
        calculateDimensions()
    }

    private fun calculateDimensions() {
        cellWidth = width / this.numColumnsAndRows
        cellHeight = height / this.numColumnsAndRows

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)

        val width = width
        val height = height

        canvas.drawLine(width.toFloat(), 0f, width.toFloat(), height.toFloat(), boldPaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), boldPaint)

        canvas.drawLine(0f, 0f, width.toFloat(), 0F, boldPaint)
        canvas.drawLine(0f, 0F, 0F, height.toFloat(), boldPaint)

        for (i in 1 until this.numColumnsAndRows) {
            val paint = if (i % 3 == 0) boldPaint else blackPaint
            canvas.drawLine((i * cellWidth).toFloat(), 0f, (i * cellWidth).toFloat(), height.toFloat(), paint)
            canvas.drawLine(0f, (i * cellHeight).toFloat(), width.toFloat(), (i * cellHeight).toFloat(), paint)
        }
    }
}