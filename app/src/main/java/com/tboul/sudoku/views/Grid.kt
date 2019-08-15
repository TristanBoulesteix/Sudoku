package com.tboul.sudoku.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.app.Activity
import android.graphics.Paint
import android.util.DisplayMetrics
import android.os.Build
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi



class Grid @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    //number of row and column
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    val GAP_WIDTH_DP = 62
    private var GAP_WIDTH_PIXEL: Float = 0.toFloat()
    private val paint = Paint()

    fun PixelGridView(context: Context): ??? {
        super(context)
        init(context)
    }

    fun PixelGridView(context: Context,  attrs: AttributeSet): ??? {
        super(context, attrs)
        init(context)

    }

    fun PixelGridView(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int): ??? {
        super(context, attrs, defStyleAttr)
        init(context)

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun PixelGridView(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): ??? {
        super(context, attrs, defStyleAttr, defStyleRes)
        init(context)
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun init(context: Context) {

        // set paint color
        paint.setColor(Color.GREEN)

        // get view dimentions
        getScreenDimensions()

    }

    private fun getScreenDimensions() {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        //gap size in pixel
        GAP_WIDTH_PIXEL = convertDpToPixel(GAP_WIDTH_DP.toFloat(), context)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        getScreenDimensions()

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // draw Horizontal line from Y= 0 -> Y+=Gap... till screen width
        var verticalPosition = 0f

        while (verticalPosition <= screenHeight) {

            canvas.drawLine(
                0f, verticalPosition,
                screenWidth.toFloat(), verticalPosition, paint
            )

            verticalPosition += GAP_WIDTH_PIXEL

        }

        // draw Vertical line from X=0 -> X+=Gap... till screen Height 0|||hor+gap|||W
        var horizontalPosition = 0f

        while (horizontalPosition <= screenWidth) {

            canvas.drawLine(
                horizontalPosition, 0f,
                horizontalPosition, screenHeight.toFloat(), paint
            )

            horizontalPosition += GAP_WIDTH_PIXEL

        }

    }
}