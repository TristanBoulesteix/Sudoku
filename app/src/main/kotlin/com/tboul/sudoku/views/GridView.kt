package com.tboul.sudoku.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import com.github.clans.fab.FloatingActionMenu
import com.tboul.sudoku.R
import com.tboul.sudoku.models.Grid
import com.tboul.sudoku.utils.DIFFICULTY


@SuppressLint("ViewConstructor")
class GridView(
    private val grid: Grid,
    private val fabMenu: FloatingActionMenu,
    private val unlockAchievement: (achievement: String?) -> Unit,
    context: Context?,
    attrs: AttributeSet? = null
) :
    View(context, attrs), GestureDetector.OnGestureListener {
    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val gestureDetector: GestureDetector = GestureDetector(getContext(), this)
    private val buttonValidate by lazy { (getContext() as Activity).findViewById<Button>(R.id.button_validate) }

    private var gridSeparatorSize = 0F
    private var gridWidth = 0F
    private var cellWidth = 0F
    private var buttonWidth = 0F
    private var buttonRadius = 0F
    private var buttonMargin = 0F

    private var validate = false

    val resetClick = OnClickListener {
        grid.clearCells()
        grid.resetPosition()
        Toast.makeText(context, "Sudoku réinitialisé", Toast.LENGTH_SHORT).show()
        fabMenu.close(true)
        if (validate) {
            buttonValidate.text = context?.getString(R.string.validate_label)
            buttonValidate.setOnClickListener(validateClick)
        }
        validate = false
        postInvalidate()
    }

    val validateClick = OnClickListener {
        validate = true
        postInvalidate()
        if (grid.isValid) {
            val difficulty = when(grid.difficulty) {
                DIFFICULTY.EASY -> R.string.achievement_win_a_game_with_an_easy_difficulty
                DIFFICULTY.MEDIUM -> R.string.achievement_win_a_game_with_a_medium_difficulty
                DIFFICULTY.HARD -> R.string.achievement_win_a_game_with_a_hard_difficulty
            }
            unlockAchievement(context?.getString(difficulty))
            Toast.makeText(context, "Sudoku complété !", Toast.LENGTH_SHORT).show()
            buttonValidate.text = context?.getString(R.string.new_game_fab_label)
            buttonValidate.setOnClickListener(restartClick)
        }
    }

    val restartClick = OnClickListener {
        (getContext() as Activity).recreate()
    }

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
        paint.style = Paint.Style.FILL

        // Draw the sudoku
        for (i in 0..8) {
            for (j in 0..8) {
                paint.color = if (!grid[i][j].visible)
                    if (grid.x == i && grid.y == j && !validate) Color.YELLOW
                    else if (validate && grid[i][j].valid)
                        Color.GREEN
                    else if (validate && !grid[i][j].valid)
                        Color.RED
                    else
                        Color.WHITE
                else
                    0xFFF0F0F0.toInt()

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
                } else if (grid[i][j].currentValue != 0) {
                    paint.color = 0xFF000000.toInt()
                    paint.textSize = cellWidth * 0.7F
                    canvas?.drawText(
                        grid[i][j].currentValue.toString(),
                        i * cellWidth + cellWidth / 2,
                        j * cellWidth + cellWidth * 0.75F,
                        paint
                    )
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
                buttonLeft = buttonMargin
                buttonTop += buttonWidth + buttonMargin
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        if (!validate) {
            if (e!!.y < gridWidth) {
                grid.x = (e.x / cellWidth).toInt()
                grid.y = (e.y / cellWidth).toInt()
                postInvalidate()
                return true
            }

            var buttonLeft = buttonMargin
            var buttonTop = 9 * cellWidth + gridSeparatorSize / 2

            for (i in 1..9) {
                val rect =
                    RectF(buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonWidth)

                if (rect.contains(e.x, e.y)) {
                    if (grid.x != -1 && grid.y != -1) grid.updateValue(i)
                    postInvalidate()
                    return true
                }

                if (i != 6) {
                    buttonLeft += buttonWidth + buttonMargin
                } else {
                    buttonLeft = buttonMargin
                    buttonTop += buttonWidth + buttonMargin
                }
            }

            return true
        }
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onShowPress(e: MotionEvent?) {

    }
}