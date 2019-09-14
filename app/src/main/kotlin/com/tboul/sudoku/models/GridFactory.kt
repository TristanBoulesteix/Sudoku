package com.tboul.sudoku.models

import com.tboul.sudoku.utils.DIFFICULTY
import com.tboul.sudoku.utils.SUDOKU_SIZE
import kotlin.math.floor
import kotlin.math.sqrt

object GridFactory {
    private val squareRoot = sqrt(SUDOKU_SIZE.toDouble()).toInt()

    fun getGrid(difficulty: DIFFICULTY): Grid {
        var sudokuGrid: Array<Array<Cell>> = arrayOf()

        val gridStruct = Array(SUDOKU_SIZE) { IntArray(SUDOKU_SIZE) }

        fillDiagonal(gridStruct)
        fillRemaining(
            gridStruct,
            0,
            squareRoot
        )

        for (i in 0 until SUDOKU_SIZE) {
            for (j in 0 until SUDOKU_SIZE)
                print(gridStruct[i][j].toString() + " ")
            println()
        }
        println()

        for (cells in gridStruct) {
            var line = arrayOf<Cell>()

            for (element in cells) {
                line += Cell(element)
            }

            sudokuGrid += line
        }

        val randNum = { x: Int -> floor((Math.random() * x + 1)).toInt() }

        var count = difficulty.value

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

        return Grid(sudokuGrid)
    }

    private fun fillDiagonal(gridStruct: Array<IntArray>) {
        var i = 0
        while (i < SUDOKU_SIZE) {
            fillBox(gridStruct, i, i)
            i += squareRoot
        }
    }

    private fun fillRemaining(gridStruct: Array<IntArray>, x: Int, y: Int): Boolean {
        var i = x
        var j = y

        if (j >= SUDOKU_SIZE && i < SUDOKU_SIZE - 1) {
            i += 1
            j = 0
        }
        if (i >= SUDOKU_SIZE && j >= SUDOKU_SIZE)
            return true

        if (i < squareRoot) {
            if (j < squareRoot)
                j = squareRoot
        } else if (i < SUDOKU_SIZE - squareRoot) {
            if (j == i / squareRoot * squareRoot)
                j += squareRoot
        } else {
            if (j == SUDOKU_SIZE - squareRoot) {
                i += 1
                j = 0
                if (i >= SUDOKU_SIZE)
                    return true
            }
        }

        for (num in 1..SUDOKU_SIZE) {
            if (checkIfSafe(gridStruct, i, j, num)) {
                gridStruct[i][j] = num
                if (fillRemaining(gridStruct, i, j + 1))
                    return true

                gridStruct[i][j] = 0
            }
        }
        return false
    }

    private fun checkIfSafe(gridStruct: Array<IntArray>, i: Int, j: Int, num: Int): Boolean {
        return unUsedInRow(gridStruct, i, num) &&
                unUsedInCol(gridStruct, j, num) &&
                unUsedInBox(
                    gridStruct,
                    i - i % squareRoot,
                    j - j % squareRoot,
                    num
                )
    }

    private fun unUsedInRow(gridStruct: Array<IntArray>, i: Int, num: Int): Boolean {
        for (j in 0 until SUDOKU_SIZE)
            if (gridStruct[i][j] == num)
                return false
        return true
    }

    private fun unUsedInCol(gridStruct: Array<IntArray>, j: Int, num: Int): Boolean {
        for (i in 0 until SUDOKU_SIZE)
            if (gridStruct[i][j] == num)
                return false
        return true
    }

    private fun fillBox(gridStruct: Array<IntArray>, row: Int, col: Int) {
        var num: Int
        for (i in 0 until squareRoot) {
            for (j in 0 until squareRoot) {
                do {
                    num = randomGenerator(SUDOKU_SIZE)
                } while (!unUsedInBox(
                        gridStruct,
                        row,
                        col,
                        num
                    )
                )

                gridStruct[row + i][col + j] = num
            }
        }
    }

    private fun randomGenerator(num: Int): Int {
        return floor(Math.random() * num + 1).toInt()
    }

    private fun unUsedInBox(gridStruct: Array<IntArray>, rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0 until squareRoot) {
            for (j in 0 until squareRoot) {
                if (gridStruct[rowStart + i][colStart + j] == num) return false
            }
        }
        return true
    }
}