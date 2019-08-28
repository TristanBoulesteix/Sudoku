package com.tboul.sudoku.models

import com.tboul.sudoku.models.factories.GridFactory
import com.tboul.sudoku.utils.SUDOKU_SIZE
import kotlin.math.floor

class Grid {
    private var sudokuGrid: Array<Array<Cell>> = arrayOf()

    var x = -1
    var y = -1

    init {
        val grid = GridFactory().grid

        for (cells in grid) {
            var line = arrayOf<Cell>()

            for (element in cells) {
                line += Cell(element)
            }

            sudokuGrid += line
        }

        val randNum = { x: Int -> floor((Math.random() * x + 1)).toInt() }

        var count = 10

        while (count != 0) {
            val cellId = randNum(SUDOKU_SIZE * SUDOKU_SIZE)

            val i = cellId / SUDOKU_SIZE
            var j = cellId % 9

            if (i == 9 || j == 9) continue
            if (j != 0) j--

            if (sudokuGrid[i][j].visible) {
                count--
                sudokuGrid[i][j].visible = false
            }
        }
    }

    fun updateValue(value: Int) {
        this[x][y].currentValue = value
    }

    fun clearCells() {
       for (cells in sudokuGrid) {
           for (cell in cells) {
               if (!cell.visible) cell.currentValue = 0
           }
       }
    }

    fun resetPosition() {
        x = -1
        y = -1
    }

    operator fun get(index: Int): Array<Cell> {
        return sudokuGrid[index]
    }
}