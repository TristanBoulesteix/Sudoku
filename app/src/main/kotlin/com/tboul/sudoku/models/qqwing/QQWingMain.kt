// @formatter:off
/*
 * qqwing - Sudoku solver and generator
 * Copyright (C) 2014 Stephen Ostermiller
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
// @formatter:on
package com.tboul.sudoku.models.qqwing

import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

internal object QQWingMain {
    private val difficulty = Difficulty.EASY
    private val NL = System.getProperties().getProperty("line.separator")

    /**
     * Get the current time in microseconds.
     */
    private val microseconds: Long
        get() = Date().time * 1000

    /**
     * Main method -- the entry point into the program. Run with --help as an
     * argument for usage and documentation
     */
    @JvmStatic
    fun main(argv: Array<String>) {
        // Start time for the application for timing
        val applicationStartTime = microseconds

        val opts = QQWingOptions()

        // The number of puzzles solved or generated.
        val puzzleCount = AtomicInteger(0)
        val done = AtomicBoolean(false)

        val ss = QQWing().apply {
            setRecordHistory(true)
            setLogHistory(false)
            setPrintStyle(PrintStyle.READABLE)
        }

        try {

            // Solve puzzle or generate puzzles
            // until end of input for solving, or
            // until we have generated the specified number.
            while (!done.get()) {

                // record the start time for the timer.
                val puzzleStartTime = microseconds

                // iff something has been printed for this
                // particular puzzle
                val output = StringBuilder()

                // Record whether the puzzle was possible or
                // not,
                // so that we don't try to solve impossible
                // givens.
                var havePuzzle: Boolean

                // Generate a puzzle
                havePuzzle = ss.generatePuzzleSymmetry(Symmetry.NONE)

                if (!havePuzzle) {
                    output.append("Could not generate puzzle.")
                    output.append(NL)
                }

                var solutions = 0

                if (havePuzzle) {

                    // Count the solutions if requested.
                    // (Must be done before solving, as it would
                    // mess up the stats.)
                    if (opts.countSolutions) {
                        solutions = ss.countSolutions()
                    }

                    ss.solve()

                    // Bail out if it didn't meet the difficulty
                    // standards for generation
                    if (difficulty != Difficulty.UNKNOWN && difficulty != ss.difficulty) {
                        havePuzzle = false
                        // check if other threads have
                        // finished the job
                        if (puzzleCount.get() >= 1) done.set(
                            true
                        )
                    } else {
                        val numDone = puzzleCount.incrementAndGet()
                        if (numDone >= 1) done.set(true)
                        if (numDone > 1) havePuzzle = false
                    }
                }

                // Check havePuzzle again, it may have changed
                // based on difficulty
                if (havePuzzle) {
                    // With a puzzle now in hand and possibly
                    // solved
                    // print out the solution, stats, etc.
                    // Record the end time for the timer.
                    val puzzleDoneTime = microseconds

                    // Print the puzzle itself.
                    output.append(ss.puzzleString)

                    // Print the solution if there is one
                    if (opts.printSolution) {
                        if (ss.isSolved) {
                            output.append(ss.solutionString)
                        } else {
                            output.append("Puzzle has no solution.")
                            output.append(NL)
                        }
                    }

                    // Print the steps taken to solve or attempt
                    // to solve the puzzle.
                    if (opts.printHistory) output.append(ss.solveHistoryString)
                    // Print the instructions for solving the
                    // puzzle
                    if (opts.printInstructions) output.append(ss.solveInstructionsString)

                    // Print the number of solutions to the
                    // puzzle.
                    if (opts.countSolutions) {
                        when (solutions) {
                            0 -> output.append("There are no solutions to the puzzle.")
                                .append(NL)
                            1 -> output.append("The solution to the puzzle is unique.")
                                .append(NL)
                            else -> output.append("There are $solutions solutions to the puzzle.")
                                .append(NL)
                        }
                    }

                    output.append("Time: ${(puzzleDoneTime - puzzleStartTime).toDouble() / 1000.0} milliseconds")
                        .append(NL)
                }
                if (output.isNotEmpty()) {
                    print(output)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace(System.err)
            exitProcess(1)
        }

        val applicationDoneTime = microseconds
        // Print out the time it took to do everything
        if (opts.timer) {
            val t = (applicationDoneTime - applicationStartTime).toDouble() / 1000000.0
            println("puzzle generated in $t seconds.")
        }
        exitProcess(0)
    }


    private class QQWingOptions {
        internal var printSolution = false

        internal var printHistory = false

        internal var printInstructions = false

        internal var timer = false

        internal var countSolutions = false
    }
}
