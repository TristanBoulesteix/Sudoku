package com.tboul.sudoku.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.FrameLayout
import com.tboul.sudoku.R
import com.tboul.sudoku.utlis.initOnce
import com.tboul.sudoku.views.Grid

class GameActivity : AppCompatActivity() {
    private var grid by initOnce<FrameLayout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_game)
        grid = findViewById(R.id.sudoku)
        grid.addView(Grid(this))
    }
}
