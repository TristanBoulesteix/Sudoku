package com.tboul.sudoku.views.activities

import android.os.Bundle
import com.tboul.sudoku.R
import com.tboul.sudoku.views.activities.templates.TemplateActivity

class LevelActivity : TemplateActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
    }
}