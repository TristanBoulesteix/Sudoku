package com.tboul.sudoku.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Grid(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val numColumns: Int = 9
    private val blackPaint = Paint().apply { style = Paint.Style.FILL_AND_STROKE }
    private val boldPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 5F
    }

    private var cellWidth: Int = 0
    private var cellHeight: Int = 0

    init {
        calculateDimensions()
    }

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int) {
        super.onSizeChanged(width, height, oldwidth, oldheight)
        calculateDimensions()
    }

    private fun calculateDimensions() {
        cellWidth = width / this.numColumns
        cellHeight = height / this.numColumns

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

        for (i in 1 until this.numColumns) {
            val paint = if (i % 3 == 0) {
                boldPaint
            } else {
                blackPaint
            }
            canvas.drawLine((i * cellWidth).toFloat(), 0f, (i * cellWidth).toFloat(), height.toFloat(), paint)
            canvas.drawLine(0f, (i * cellHeight).toFloat(), width.toFloat(), (i * cellHeight).toFloat(), paint)
        }
    }
}