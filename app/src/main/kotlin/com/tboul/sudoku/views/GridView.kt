package com.tboul.sudoku.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import com.tboul.sudoku.models.Grid


@SuppressLint("ViewConstructor")
class GridView(private val grid: Grid, context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private val paint = Paint(ANTI_ALIAS_FLAG)

    private var gridSeparatorSize = 0.toFloat()
    private var gridWidth = 0.toFloat()
    private var cellWidth = 0.toFloat()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        gridSeparatorSize = (w / 9F) / 20F
        gridWidth = w.toFloat()
        cellWidth = gridWidth / 9F
    }

    override fun onDraw(canvas: Canvas?) {
        paint.textAlign = Paint.Align.CENTER

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
    }
}