package com.tboul.sudoku.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class Grid(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val numColumns: Int = 9
    private val numRows: Int = 9

    private var cellWidth: Int = 0
    private var cellHeight: Int = 0
    private val blackPaint = Paint()
    private var cellChecked: Array<BooleanArray>? = null

    init {
        blackPaint.style = Paint.Style.FILL_AND_STROKE
        calculateDimensions()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateDimensions()
    }

    private fun calculateDimensions() {
        cellWidth = width / this.numColumns
        cellHeight = height / this.numRows

        cellChecked = Array(this.numColumns) { BooleanArray(this.numRows) }

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)

        val width = width
        val height = height

        for (i in 0 until this.numColumns) {
            for (j in 0 until this.numRows) {
                if (cellChecked!![i][j]) {

                    canvas.drawRect(
                        (i * cellWidth).toFloat(), (j * cellHeight).toFloat(),
                        ((i + 1) * cellWidth).toFloat(), ((j + 1) * cellHeight).toFloat(),
                        blackPaint
                    )
                }
            }
        }

        for (i in 1 until this.numColumns) {
            canvas.drawLine((i * cellWidth).toFloat(), 0f, (i * cellWidth).toFloat(), height.toFloat(), blackPaint)
        }

        for (i in 1 until this.numRows) {
            canvas.drawLine(0f, (i * cellHeight).toFloat(), width.toFloat(), (i * cellHeight).toFloat(), blackPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val column = (event.x / cellWidth).toInt()
            val row = (event.y / cellHeight).toInt()

            cellChecked!![column][row] = !cellChecked!![column][row]
            invalidate()
        }

        return true
    }
}