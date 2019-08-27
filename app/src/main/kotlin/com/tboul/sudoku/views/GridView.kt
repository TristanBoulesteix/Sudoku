package com.tboul.sudoku.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.tboul.sudoku.models.Grid


@SuppressLint("ViewConstructor")
class GridView(private val grid: Grid, context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private val paint = Paint(ANTI_ALIAS_FLAG)

    private var gridSeparatorSize = 0F
    private var gridWidth = 0F
    private var cellWidth = 0F
    private var buttonWidth = 0F
    private var buttonRadius = 0F
    private var buttonMargin = 0F


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Separator size
        gridSeparatorSize = (w / 9F) / 20F

        // Size of cells
        gridWidth = w.toFloat()
        cellWidth = gridWidth / 9F

        // Size of buttons
        buttonWidth = w / 7F
        buttonRadius = buttonWidth / 10F
        buttonMargin = (w - 6 * buttonWidth) / 7F
    }

    override fun onDraw(canvas: Canvas?) {
        paint.textAlign = Paint.Align.CENTER

        // Draw the sudoku
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                val backgroundColor = if (!grid[i][j].visible) Color.WHITE else 0xFFF0F0F0.toInt()
                paint.color = backgroundColor
                canvas?.drawRect(
                    i * cellWidth,
                    j * cellWidth,
                    (i + 1) * cellWidth,
                    (j + 1) * cellWidth,
                    paint
                )

                if (grid[i][j].visible) {
                    paint.color = 0xFF000000.toInt()
                    paint.textSize = cellWidth * 0.7F
                    canvas?.drawText(
                        grid[i][j].value.toString(),
                        i * cellWidth + cellWidth / 2,
                        j * cellWidth + cellWidth * 0.75F,
                        paint
                    )
                } else {
                    paint.textSize = cellWidth * 0.33F
                    paint.color = 0xFFA0A0A0.toInt()

/*                    if ( gameBoard.cells[y][x].marks[0] ) {
                        canvas.drawText("1",
                            x * cellWidth + cellWidth * 0.2f,
                            y * cellWidth + cellWidth * 0.3f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[1] ) {
                        canvas.drawText("2",
                            x * cellWidth + cellWidth * 0.5f,
                            y * cellWidth + cellWidth * 0.3f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[2] ) {
                        canvas.drawText("3",
                            x * cellWidth + cellWidth * 0.8f,
                            y * cellWidth + cellWidth * 0.3f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[3] ) {
                        canvas.drawText("4",
                            x * cellWidth + cellWidth * 0.2f,
                            y * cellWidth + cellWidth * 0.6f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[4] ) {
                        canvas.drawText("5",
                            x * cellWidth + cellWidth * 0.5f,
                            y * cellWidth + cellWidth * 0.6f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[5] ) {
                        canvas.drawText("6",
                            x * cellWidth + cellWidth * 0.8f,
                            y * cellWidth + cellWidth * 0.6f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[6] ) {
                        canvas.drawText("7",
                            x * cellWidth + cellWidth * 0.2f,
                            y * cellWidth + cellWidth * 0.9f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[7] ) {
                        canvas.drawText("8",
                            x * cellWidth + cellWidth * 0.5f,
                            y * cellWidth + cellWidth * 0.9f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[8] ) {
                        canvas.drawText("9",
                            x * cellWidth + cellWidth * 0.8f,
                            y * cellWidth + cellWidth * 0.9f, paint);
                    }*/
                }
            }
        }

        // Draw lines grid
        paint.color = Color.GRAY
        paint.strokeWidth = gridSeparatorSize / 2

        for (i in 0..9) {
            canvas?.drawLine(i * cellWidth, 0F, i * cellWidth, cellWidth * 9, paint)
            canvas?.drawLine(0F, i * cellWidth, cellWidth * 9, i * cellWidth, paint)
        }

        paint.color = Color.BLACK
        paint.strokeWidth = gridSeparatorSize
        for (i in 0..3) {
            canvas?.drawLine(i * (cellWidth * 3), 0F, i * (cellWidth * 3), cellWidth * 9, paint)
            canvas?.drawLine(0F, i * (cellWidth * 3), cellWidth * 9, i * (cellWidth * 3), paint)
        }

        // Draw buttons number
        val buttonZone = 9 * cellWidth + gridSeparatorSize / 2

        paint.textAlign = Paint.Align.CENTER
        paint.textSize = buttonWidth * 0.7F

        var buttonLeft = buttonMargin
        var buttonTop = buttonZone + buttonMargin

        @SuppressLint("DrawAllocation")
        for (i in 1..9) {
            paint.color = 0xFFFFFFFFF.toInt()
            paint.style = Paint.Style.FILL
            val rectF =
                RectF(buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonWidth)
            canvas?.drawRoundRect(rectF, buttonRadius, buttonRadius, paint)

            paint.color = 0xFF000000.toInt()
            canvas?.drawText(
                i.toString(),
                rectF.centerX(),
                rectF.top + rectF.height() * 0.75f,
                paint
            )

            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            canvas?.drawRoundRect(rectF, buttonRadius, buttonRadius, paint)

            if (i != 6) {
                buttonLeft += buttonWidth + buttonMargin
            } else {
                buttonLeft = buttonMargin;
                buttonTop += buttonWidth + buttonMargin
            }
        }
    }
}