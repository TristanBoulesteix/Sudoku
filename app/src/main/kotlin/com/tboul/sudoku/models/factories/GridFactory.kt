package com.tboul.sudoku.models.factories

import com.tboul.sudoku.utils.SUDOKU_SIZE
import kotlin.math.floor
import kotlin.math.sqrt

object GridFactory {
    private const val numColumnsAndRows = SUDOKU_SIZE
    private val squareRoot = sqrt(numColumnsAndRows.toDouble()).toInt()

    val grid = Array(numColumnsAndRows) { IntArray(numColumnsAndRows) }

    init {
        fillDiagonal()
        fillRemaining(0, squareRoot)

        for (i in 0 until numColumnsAndRows) {
            for (j in 0 until numColumnsAndRows)
                print(grid[i][j].toString() + " ")
            println()
        }
        println()
    }

    private fun fillDiagonal() {
        var i = 0
        while (i < numColumnsAndRows) {
            fillBox(i, i)
            i += squareRoot
        }
    }

    private fun fillRemaining(x: Int, y: Int): Boolean {
        var i = x
        var j = y

        if (j >= numColumnsAndRows && i < numColumnsAndRows - 1) {
            i += 1
            j = 0
        }
        if (i >= numColumnsAndRows && j >= numColumnsAndRows)
            return true

        if (i < squareRoot) {
            if (j < squareRoot)
                j = squareRoot
        } else if (i < numColumnsAndRows - squareRoot) {
            if (j == i / squareRoot * squareRoot)
                j += squareRoot
        } else {
            if (j == numColumnsAndRows - squareRoot) {
                i += 1
                j = 0
                if (i >= numColumnsAndRows)
                    return true
            }
        }

        for (num in 1..numColumnsAndRows) {
            if (checkIfSafe(i, j, num)) {
                grid[i][j] = num
                if (fillRemaining(i, j + 1))
                    return true

                grid[i][j] = 0
            }
        }
        return false
    }

    private fun checkIfSafe(i: Int, j: Int, num: Int): Boolean {
        return unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % squareRoot, j - j % squareRoot, num)
    }

    private fun unUsedInRow(i: Int, num: Int): Boolean {
        for (j in 0 until numColumnsAndRows)
            if (grid[i][j] == num)
                return false
        return true
    }

    private fun unUsedInCol(j: Int, num: Int): Boolean {
        for (i in 0 until numColumnsAndRows)
            if (grid[i][j] == num)
                return false
        return true
    }

    private fun fillBox(row: Int, col: Int) {
        var num: Int
        for (i in 0 until squareRoot) {
            for (j in 0 until squareRoot) {
                do {
                    num = randomGenerator(numColumnsAndRows)
                } while (!unUsedInBox(row, col, num))

                grid[row + i][col + j] = num
            }
        }
    }

    private fun randomGenerator(num: Int): Int {
        return floor(Math.random() * num + 1).toInt()
    }

    private fun unUsedInBox(rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0 until squareRoot) {
            for (j in 0 until squareRoot) {
                if (grid[rowStart + i][colStart + j] == num) return false
            }
        }
        return true
    }
}