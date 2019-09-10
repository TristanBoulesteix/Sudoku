package com.tboul.sudoku.models

import com.tboul.sudoku.models.factories.GridFactory

class Grid {
    private var sudokuGrid: Array<Array<Cell>> = arrayOf()

    val valid: Boolean
        get() {
            for (cells in sudokuGrid) {
                for (cell in cells) {
                    if (!cell.valid && !cell.visible) return false
                }
            }
            return true
        }

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

        val attempts = 5
        val randNum = { (0..8).shuffled().first() }

        while (attempts > 0) {
            var row = randNum()
            var col = randNum()

            while (!sudokuGrid[row][col].visible) {
                row = randNum()
                col = randNum()
            }

            sudokuGrid[row][col].visible = false
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

    private fun solve() : Boolean {
        fun valueInCol(col: Int, value: Int): Boolean {
            for (abscissa in 0 until 10)
                if (sudokuGrid[abscissa][col].value == value) return true
            return false
        }

        fun valueInSquare(abs: Int, ord: Int, value: Int): Boolean {
            for (abscissa in abs until abs + 3) {
                for (ordinate in ord until ord + 3) {
                    if (sudokuGrid[abscissa][ordinate].value == value) {
                        return true
                    }
                }
            }

            return false
        }


        for (i in 0 until 81) {
            val row = i / 9
            val col = i % 9

            if (!sudokuGrid[row][col].visible) {
                for (value in 1 until 10) {
                    if (sudokuGrid[row].none { it.value == value }) {
                        if (!valueInCol(col, value)) {
                            if(!valueInSquare(3 * (row / 3), 3 * (col / 3), value)) {
                                sudokuGrid[row][col].currentValue = value

                                if (sudokuGrid.filter { it.none { it.visible } })
                            }
                        }
                    }
                }
            }
        }
    }
}