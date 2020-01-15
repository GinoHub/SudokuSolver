package org.bitbucket.sudoku;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**
 * Test suite for checking validity of some Sudoku grids
 */
@RunWith( Parameterized.class )
public class AdditionalValidSudokuParameterisedTests {

  /**
   *  The collection of tests as an Array of (grid x expected result)
   */
  /* *INDENT-OFF* */
  @Parameters(name = "{index}: valid should be {1} -- {2}")
  public static Collection<Object[]> data() {
    // testcases wil de displayed as test[0], test[1] and so on
    return Arrays.asList(new Object[][] {
      { new int[][] { } ,                   true , "Empty grid is a valid Sudoku"    },
      { new int[][] { {1 , 2}, {2 , 1} } ,  false, "Size is not square"},
      { new int[][] { {0, 1, 2, 3},
                      {2, 3, 4, 1},
                      {1, 2, 3, 0},
                      {3, 4, 0, 2}} ,       false, "grid contains values outside {1,2,3,4}"}

    });
  }
  /* *INDENT-ON* */

  // Test parameters
  // @link{https://github.com/junit-team/junit4/wiki/Parameterized-tests}
  private int[][] grid;
  private boolean validStatus;
  private String information;
  /**
   *  Constructor for the tests
   */
  public AdditionalValidSudokuParameterisedTests(
    int[][] input,
    boolean expectedValidStatus,
    String reason) {
    grid = input;
    validStatus = expectedValidStatus;
    information = reason;
  }

  /**
   * Run all the tests
   */
  @Test
  public void test()  {
    assertEquals(validStatus, new Sudoku(grid).isValid());
  }
}
