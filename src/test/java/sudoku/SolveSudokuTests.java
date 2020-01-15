package org.bitbucket.sudoku;

import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;

import org.bitbucket.sudoku.Sudoku;
import org.junit.rules.Timeout;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Simple tests for checking solvability of Sudoku grids
 * @author James Ridey
 */
public class SolveSudokuTests {
  @Rule
  public Timeout globalTimeout = Timeout.seconds(1);

  private int[][] small = {
    { -1, -1, -1, -1 },
    { -1, -1, 3, -1 },
    { -1, 2, 4, -1 },
    { -1, -1, -1, -1 }
  };

  /**
   * 4 x 4 grid: should be solvable
   */
  @Test
  public void solveSmallSudokuTest()  {

    Sudoku sudoku = new Sudoku(small);

    IntStream stream = Arrays.stream(sudoku.solve()).flatMapToInt(Arrays::stream);
    assertFalse(stream.anyMatch(i -> i == -1));
    assertTrue(sudoku.isValid());
  }

  @Test
  public void solveSmallSudokuTestFast()  {
    Sudoku sudoku = new Sudoku(small);

    IntStream stream = Arrays.stream(sudoku.fastSolve()).flatMapToInt(Arrays::stream);
    assertFalse(stream.anyMatch(i -> i == -1));
    assertTrue(sudoku.isValid());
  }

  private int[][] normal = {
    { -1, -1, 2,  -1,  3, -1, -1, -1, 8 },
    { -1, -1, -1, -1, -1,  8, -1, -1, -1 },
    { -1,  3, 1,  -1,  2, -1, -1, -1, -1 },
    { -1,  6, -1, -1,  5, -1,  2,  7, -1 },
    { -1,  1, -1, -1, -1, -1, -1,  5, -1 },
    { 2,  -1, 4,  -1,  6, -1, -1,  3, 1 },
    { -1, -1, -1, -1,  8, -1,  6, -1, 5 },
    { -1, -1, -1, -1, -1, -1, -1,  1, 3 },
    { -1, -1, 5,  3,   1, -1,  4, -1, -1 }
  };

  @Test
  public void solveNormalSudokuTest() {
    Sudoku sudoku = new Sudoku(normal);

    IntStream stream = Arrays.stream(sudoku.solve()).flatMapToInt(Arrays::stream);
    assertFalse(stream.anyMatch(i -> i == -1));
    assertTrue(sudoku.isValid());
  }

  @Test
  public void solveNormalSudokuTestFast() {
    Sudoku sudoku = new Sudoku(normal);

    IntStream stream = Arrays.stream(sudoku.fastSolve()).flatMapToInt(Arrays::stream);
    assertFalse(stream.anyMatch(i -> i == -1));
    assertTrue(sudoku.isValid());
  }

  @Test
  public void solveLargeSudoku()  {
    int[][] g = {
      { -1, 15, -1, 1, -1, 2, 10, 14, 12, -1, -1, -1, -1, -1, -1, -1 },
      { -1, 6, 3, 16, 12, -1, 8, 4, 14, 15, 1, -1, 2, -1, -1, -1 },
      { 14, -1, 9, 7, 11, 3, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
      { 4, 13, 2, 12, -1, -1, -1, -1, 6, -1, -1, -1, -1, 15, -1, -1 },
      { -1, -1, -1, -1, 14, 1, 11, 7, 3, 5, 10, -1, -1, 8, -1, 12 },
      { 3, 16, -1, -1, 2, 4, -1, -1, -1, 14, 7, 13, -1, -1, 5, 15 },
      { 11, -1, 5, -1, -1, -1, -1, -1, -1, 9, 4, -1, -1, 6, -1, -1 },
      { -1, -1, -1, -1, 13, -1, 16, 5, 15, -1, -1, 12, -1, -1, -1, -1 },
      { -1, -1, -1, -1, 9, -1, 1, 12, -1, 8, 3, 10, 11, -1, 15, -1 },
      { 2, 12, -1, 11, -1, -1, 14, 3, 5, 4, -1, -1, -1, -1, 9, -1 },
      { 6, 3, -1, 4, -1, -1, 13, -1, -1, 11, 9, 1, -1, 12, 16, 2 },
      { -1, -1, 10, 9, -1, -1, -1, -1, -1, -1, 12, -1, 8, -1, 6, 7 },
      { 12, 8, -1, -1, 16, -1, -1, 10, -1, 13, -1, -1, -1, 5, -1, -1 },
      { 5, -1, -1, -1, 3, -1, 4, 6, -1, 1, 15, -1, -1, -1, -1, -1 },
      { -1, 9, 1, 6, -1, 14, -1, 11, -1, -1, 2, -1, -1, -1, 10, 8 },
      { -1, 14, -1, -1, -1, 13, 9, -1, 4, 12, 11, 8, -1, -1, 2, -1 }
    };
    Sudoku sudoku = new Sudoku(g);

    IntStream stream = Arrays.stream(sudoku.fastSolve()).flatMapToInt(Arrays::stream);

    assertFalse(stream.anyMatch(i -> i == -1));
    assertTrue(sudoku.isValid());
  }

  private int[][] unsolvable = {
    { 5, 1, 6, 8, 4, 9, 7, 3, 2 },
    { 3, -1, 7, 6, -1, 5, -1, -1, -1 },
    { 8, -1, 9, 7, -1, -1, -1, 6, 5 },
    { 1, 3, 5, -1, 6, -1, 9, -1, 7 },
    { 4, 7, 2, 5, 9, 1, -1, -1, 6 },
    { 9, 6, 8, 3, 7, -1, -1, 5, -1 },
    { 2, 5, 3, 1, 8, 6, -1, 7, 4 },
    { 6, 8, 4, 2, -1, 7, 5, -1, -1 },
    { 7, 9, 1, -1, 5, -1, 6, -1, 8}
  };

  @Test
  public void unsolvableSudokuTest()  {
    int[][] copy = new int[unsolvable.length][];
    for (int i = 0; i < unsolvable.length; i++) copy[i] = unsolvable[i].clone();

    Sudoku sudoku = new Sudoku(copy);

    int[][] grid = sudoku.solve();
    IntStream stream = Arrays.stream(grid).flatMapToInt(Arrays::stream);

    assertThat(grid, not(equalTo(unsolvable)));
    assertTrue(stream.anyMatch(i -> i == -1));
    assertTrue(sudoku.isValid());
  }

  @Test
  public void unsolvableSudokuTestFast()  {
    int[][] copy = new int[unsolvable.length][];
    for (int i = 0; i < unsolvable.length; i++) copy[i] = unsolvable[i].clone();

    Sudoku sudoku = new Sudoku(copy);

    int[][] grid = sudoku.fastSolve();
    IntStream stream = Arrays.stream(grid).flatMapToInt(Arrays::stream);

    assertThat(grid, not(equalTo(unsolvable)));
    assertTrue(stream.anyMatch(i -> i == -1));
    assertTrue(sudoku.isValid());
  }

}
