package com.tboul.sudoku.models

import com.tboul.sudoku.models.factories.GridFactory
import com.tboul.sudoku.utils.SUDOKU_SIZE
import kotlin.math.floor

class Grid {
    private var sudokuGrid: Array<Array<Box>> = arrayOf()

    val sudokuAsList: MutableList<Int>
        get() {
            val list = mutableListOf<Int>()

            for (boxes in sudokuGrid) {
                for (box in boxes) {
                    list.add(box.value)
                }
            }

            return list
        }

    init {
        val grid = GridFactory().grid

        for (i in 0 until grid.size) {
            var line = arrayOf<Box>()

            for (j in 0 until grid.size) {
                line += Box(grid[i][j])
            }

            sudokuGrid += line
        }

        val randNum = { x: Int -> floor((Math.random() * x + 1)).toInt() }

        var count = 10

        while (count != 0) {
            val cellId = randNum(SUDOKU_SIZE * SUDOKU_SIZE)

            val i = cellId / SUDOKU_SIZE
            var j = cellId % 9
            if (j != 0) j--

            if (sudokuGrid[i][j].visible) {
                count--
                sudokuGrid[i][j].visible = false
            }
        }
    }
}