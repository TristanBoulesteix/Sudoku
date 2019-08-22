package com.tboul.sudoku.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.FrameLayout
import com.tboul.sudoku.R
import com.tboul.sudoku.models.Grid
import com.tboul.sudoku.views.GridView

class GameActivity : AppCompatActivity() {
    private val gridView by lazy { GridView(Grid(), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_game)

        val gridZone = findViewById<FrameLayout>(R.id.sudoku)
        gridZone.addView(gridView)
    }
}
