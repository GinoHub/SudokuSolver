package org.bitbucket.sudoku;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.bitbucket.sudoku.Sudoku;

/**
 * Simple tests for checking validity of Sudoku grids
 */
public class ValidSudokuTests {

  /**
   * 1 x 1 grid: should ve valid
   */
  @Test
  public void validSudokuTests()  {

    int[][] g = { { 1 } };
    Sudoku sudoku1 = new Sudoku(g);

    assertEquals(true, sudoku1.isValid());
  }

  /**
   * 4 x 4 grid should be valid
   */
  @Test
  public void validSudokuTests2()  {
    int[][] g = {
      { 1, 4, 2, 3},
      { 2, 3, 4, 1},
      { 4, 1, 3, 2},
      { 3, 2, 1, 4}
    };
    Sudoku sudoku1 = new Sudoku(g);

    assertEquals(true, sudoku1.isValid());
  }

}
