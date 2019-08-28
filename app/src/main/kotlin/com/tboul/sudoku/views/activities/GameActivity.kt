package com.tboul.sudoku.views.activities

import android.os.Bundle
import android.view.Window
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.github.clans.fab.FloatingActionButton
import com.tboul.sudoku.R
import com.tboul.sudoku.models.Grid
import com.tboul.sudoku.views.GridView

class GameActivity : AppCompatActivity() {
    private val gridView by lazy { GridView(Grid(), findViewById(R.id.fab_menu), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_game)

        findViewById<FloatingActionButton>(R.id.fab_reset).setOnClickListener(gridView.clickReset)
        findViewById<FrameLayout>(R.id.sudoku).addView(gridView)
    }
}
