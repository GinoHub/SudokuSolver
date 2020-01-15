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
public class ValidSudokuParameterisedTests {

  /**
   *  The collection of tests as an Array of (grid x expected result)
   */
  /* *INDENT-OFF* */
  @Parameters
  public static Collection<Object[]> data() {
    // testcases wil de displayed as test[0], test[1] and so on
    return Arrays.asList(new Object[][] {
      { new int[][] { }                         , true    },
      { new int[][] { { } }                     , false   },
      { new int[][] { { 1 } }                   , true    },
      { new int[][] { { 1 , 2 }, { 2 , 1 } }    , false   },
      { new int[][] { { 1 , 2 }, { 1 , 1 } }    , false   },
      { new int[][] { { 1 }, { 1 , 2 } }        , false   },

      /* Ethan Hillas tests <ethan.hillas@students.mq.edu.au> */
      {  new int[][] {
                  {1, 2},
                  {1, 2}
          }                                     , false   },
      { new int[][] {
              {1, 4, 2, 3, 5, 6, 7, 8, 9},
              {2, 3, 4, 1, 5, 6, 7, 8, 9},
              {4, 1, 3, 2, 5, 6, 7, 8, 9},
              {3, 2, 1, 4, 5, 6, 7, 8, 9},
              {1, 4, 2, 3, 5, 6, 7, 8, 9},
              {2, 3, 4, 1, 5, 6, 7, 8, 9},
              {4, 1, 3, 2, 5, 6, 7, 8, 9},
              {3, 2, 1, 4, 5, 6, 7, 8, 9},
              {3, 2, 1, 4, 5, 6, 7, 8, 9}
          }                                     ,  false  },
       { new int[][] {
               {1, 5, 2, 3},
               {2, 3, 4, 1},
               {4, 1, 3, 2},
               {3, 2, 1, 4}
           }                                    , false   },
       { new int[][] {
               {1, 4, 2, 3},
               {2, 3, 4, 1},
               {4, 0, 3, 2},
               {3, 2, 1, 4}
           }                                    , false   },//
       { new int[][] {
               {1, 4, 2, 3},
               {2, 3, 4, 1},
               {4, 1, 4, 2},
               {3, 2, 1, 4}
           }                                    , false   },
       { new int[][] {
               {1, 4, 2, 3},
               {2, 3, 4, 1},
               {4, 1, 3, 2},
               {3, 2, 1, 4}
           }                                    , true   },
       { new int[][] {
               {1, 5, 2, 3},
               {2, 3, 4, 1},
               {4, 1, 3, 2},
               {3, 2, 1, 4}
           }                                    , false  },
        { new int[][] {
                {1, 4, 2, 3},
                {2, 3, 4, 1},
                {4, 1, 0, 2},
                {3, 2, 1, 4}
            }                                   ,  false },
        { new int[][] {
                {1, 4, 2, 3},
                {2, 3, 4, 1},
                {4, 4, 3, 2},
                {3, 2, 1, 4}
            }                                   ,  false },
        { new int[][]  {
                {1, 4, 2, 3},
                {2, 3, 4, 1},
                {4, 1, 3, 2},
                {3, 2, 1, 4}
            }                                   ,  true  },
        { new int[][] {
                {1, 5, 2, 3},
                {2, 3, 4, 1},
                {4, 1, 3, 2},
                {3, 2, 1, 5}
            }                                   ,  false },
        { new int[][] {
                {1, 4, 2, 0},
                {2, 3, 4, 1},
                {4, 1, 0, 2},
                {3, 2, 1, 4}
            }                                   ,  false },
        { new int[][] {
                {1, 4, 2, 2},
                {2, 3, 4, 1},
                {4, 4, 3, 2},
                {3, 2, 1, 4}
            }                                   ,  false },
        { new int[][] {
                {1, 5, 4, 8, 7, 3, 2, 9, 6},
                {3, 8, 6, 5, 9, 2, 7, 1 ,4},
                {7, 2, 9, 6, 4, 1, 8, 3, 5},
                {8, 6, 3, 7, 2, 5, 1, 4, 9},
                {9, 7, 5, 3, 1, 4, 6, 2, 8},
                {4, 1, 2, 9, 6, 8, 3, 5, 7},
                {6, 3, 1, 4, 5, 7, 9, 8, 2},
                {5, 9, 8, 2, 3, 6, 4, 7, 1},
                {2, 4, 7, 1, 8, 9, 5, 6, 3}
            }                                  ,  true  },

        /*  Yifan Zhou test yifan.zhou1@students.mq.edu.au */
        { new int[][] {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {5, 6, 7, 8, 9, 1, 2, 3, 4},
                {6, 7, 8, 9, 1, 2, 3, 4, 5},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {8, 9, 1, 2, 3, 4, 5, 6, 7},
                {9, 1, 2, 3, 4, 5, 6, 7, 8}

            }                               ,   false  }
    });
  }
  /* *INDENT-ON* */

  // Test parameters
  // @link{https://github.com/junit-team/junit4/wiki/Parameterized-tests}
  private int[][] grid;
  private boolean validStatus;

  /**
   *  Constructor for the tests
   */
  public ValidSudokuParameterisedTests(int[][] input, boolean expectedValidStatus) {
    grid = input;
    validStatus = expectedValidStatus;
  }

  /**
   * Run all the tests
   */
  @Test
  public void test()  {
    assertEquals(validStatus, new Sudoku(grid).isValid());
  }
}
