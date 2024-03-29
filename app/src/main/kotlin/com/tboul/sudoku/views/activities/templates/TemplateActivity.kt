package com.tboul.sudoku.views.activities.templates

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

abstract class TemplateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
    }
}