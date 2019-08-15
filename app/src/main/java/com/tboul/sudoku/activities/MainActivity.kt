package com.tboul.sudoku.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Button
import com.tboul.sudoku.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start_button).setOnClickListener {
            val gameActivity = Intent(this@MainActivity, GameActivity::class.java)
            val transition = ActivityOptions.makeCustomAnimation(this@MainActivity,
                R.anim.transition_start,
                R.anim.trantion_end
            ).toBundle()
            startActivity(gameActivity, transition)
        }
    }
}
