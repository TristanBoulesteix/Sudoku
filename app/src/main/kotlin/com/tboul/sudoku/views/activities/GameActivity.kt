package com.tboul.sudoku.views.activities

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.github.clans.fab.FloatingActionButton
import com.tboul.sudoku.R
import com.tboul.sudoku.models.Grid
import com.tboul.sudoku.utils.dpToPx
import com.tboul.sudoku.views.GridView
import com.tboul.sudoku.views.activities.templates.MainTemplateActivity


class GameActivity : MainTemplateActivity() {
    private val gridView by lazy {
        GridView(
            Grid(intent.getIntExtra("difficulty", 12)),
            findViewById(R.id.fab_menu),
            this
        )
    }

    override fun actionOnBackConfirmed() {
        finish()
    }

    override val confirmExitMessage: String by lazy { getString(R.string.confirm_exit_game_message) }
    override val confirmExitTitle: String by lazy { getString(R.string.confirm_exit_game_title) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gridViewLayout = findViewById<FrameLayout>(R.id.sudoku)
        with(gridViewLayout) {
            val size = Point()
            windowManager.defaultDisplay.getSize(size)

            val params = layoutParams as RelativeLayout.LayoutParams

            when {
                size.y > 1800 -> params.topMargin = dpToPx(80)
                size.y > 1200 -> params.topMargin = dpToPx(50)
                else -> params.topMargin = dpToPx(20)
            }

            layoutParams = params
        }
        gridViewLayout.addView(gridView)


        findViewById<FloatingActionButton>(R.id.fab_home).setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
        findViewById<FloatingActionButton>(R.id.fab_new_game).setOnClickListener(gridView.restartClick)

        // buttons actions
        findViewById<Button>(R.id.button_restart).setOnClickListener(gridView.resetClick)
        findViewById<Button>(R.id.button_validate).setOnClickListener(gridView.validateClick)
    }
}
