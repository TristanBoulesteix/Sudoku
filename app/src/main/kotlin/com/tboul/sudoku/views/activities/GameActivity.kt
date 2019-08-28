package com.tboul.sudoku.views.activities

import android.app.ActivityOptions
import android.content.Intent
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
        findViewById<FloatingActionButton>(R.id.fab_settings).setOnClickListener {
            val gameActivity = Intent(this@GameActivity, SettingsActivity::class.java)
            val transition = ActivityOptions.makeCustomAnimation(
                this@GameActivity,
                R.anim.transition_start,
                R.anim.trantion_end
            ).toBundle()
            startActivity(gameActivity, transition)
        }
        findViewById<FloatingActionButton>(R.id.fab_validate).setOnClickListener{
            gridView.validate = true
            gridView.postInvalidate()
        }
        findViewById<FrameLayout>(R.id.sudoku).addView(gridView)
    }
}
