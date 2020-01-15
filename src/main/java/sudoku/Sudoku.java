package org.bitbucket.sudoku;

import javafx.util.Pair;

import java.util.*;

public class Sudoku {

    /**
     * Constructor for Sudoku
     *
     * @param g The grid that defines the sudoku
     */
    public Sudoku(int[][] g) {
        theGrid = g;
        completeGrid = true;
    }

    /**
     * Secondary constructor for Sudoku
     *
     * @param g The grid that defines the sudoku
     * @param e The value that denotes an empty cell
     */
    public Sudoku(int[][] g, int e) {
        theGrid = g;
        completeGrid = false;
        map = new HashMap<>();

        if (theGrid == null)
            return;

        for (int i = 0; i < theGrid.length; i++) {
            if (theGrid[i].length > 0) {
                for (int k = 0; k < theGrid[i].length; k++) {
                    if (theGrid[i][k] == e)
                        theGrid[i][k] = -1;
                }
            }

        }
    }

    /**
     * The n x m grid that defines the Sudoku
     */
    private int[][] theGrid;
    private boolean completeGrid;
    private Map<Pair<Integer, Integer>, ArrayList<Integer>> map;

    /**
     * Check validity of a Sudoku grid
     *
     * @return true if and only if theGrid is a valid Sudoku. Checks:
     * <ul>
     * <li> The size of theGrid isn't null. </li>
     * <li> All rows and columns are the same size (and therefore square). </li>
     * <li> The size is a square number (k<sup style="font-size:100%;">2</sup>). </li>
     * <li> All numbers are continuous, between 1 and k<sup style="font-size:100%;">2</sup>. </li>
     * </ul>
     */
    public boolean isValid() {
        return theGrid != null && checkSize() && checkNumbers();
    }

    /**
     * Attempt to compute a solution to the Sudoku
     *
     * @return A grid with possibly less empty cells than in theGrid (but not more)
     * @note If there is no empty cell in the result, then the Sudoku is solved,
     * otherwise it is not
     */
    public int[][] solve() {
        int[][] solvedGrid = theGrid;
        int prev = -1;
        int curr = 0;

        while (prev < curr) {
            prev = curr;
            curr = 0;
            for (int i = 0; i < theGrid.length; i++) {
                for (int k = 0; k < theGrid.length; k++) {
                    if (solvedGrid[i][k] == -1) { //empty cell
                        solvedGrid[i][k] = solveCell(i, k, Optional.empty()).get(0);
                        if (solvedGrid[i][k] != -1) {//found a solution!
                            curr++;
                            view(solvedGrid);
                        }
                    }
                }
            }
        }

        return theGrid = solvedGrid;
    }


    /**
     * Attempt to efficiently compute a solution to the Sudoku
     *
     * @return A grid with possibly less empty cells than in theGrid (but not more)
     * @note If there is no empty cell in the result, then the Sudoku is solved,
     * otherwise it is not
     */
    public int[][] fastSolve() {
        view(theGrid);

        for (int i = 0; i < theGrid.length; i++) {
            for (int k = 0; k < theGrid[i].length; k++) {
                if (theGrid[i][k] == -1) {
                    ArrayList<Integer> values = solveCell(i, k, Optional.empty());

                    if (values == null || values.isEmpty()) //invalid grid;
                        return null;

                    if (values.size() == 1) {
                        theGrid[i][k] = values.get(0);//one solution
                        view(theGrid);
                    } else {
                        map.put(new Pair<>(i, k), values);//save values for later
                    }
                }
            }
        }

        while (!map.isEmpty()) {
            ArrayList<Pair<Integer, Integer>> points = uniqueValueCheck();
            if (!(points == null) && !points.isEmpty()) {
                for (Pair<Integer, Integer> pair : points) {
                    map.remove(pair);
                }
            }

        }

        for (Pair<Integer, Integer> pair : map.keySet()) {
            int r = pair.getKey();
            int c = pair.getValue();

            ArrayList<Integer> values = solveCell(r, c, Optional.of(map.get(pair)));

            if (values == null || values.isEmpty())
                return null; //invalid.

            if (values.size() == 1) {
                theGrid[r][c] = values.get(0);
                view(theGrid);
            } else {
                map.replace(new Pair<>(r, c), values);
            }
        }


        return theGrid;

    }


    private ArrayList<Pair<Integer, Integer>> uniqueValueCheck() {
        ArrayList<Pair<Integer, Integer>> values = new ArrayList<>();

        for (Pair<Integer, Integer> pair : map.keySet()) {

            Integer value = uniqueCellCheck(pair);
            if (value != null) {
                theGrid[pair.getKey()][pair.getValue()] = value;
                view(theGrid);
                values.add(pair);
            }
        }
        return values;
    }

    private Integer uniqueCellCheck(Pair<Integer, Integer> pair) {

        out:
        for (Integer item : map.get(pair)) {

            for (Pair<Integer, Integer> newPair : map.keySet()) { //can add > if too long!!!


                if (pair.getKey().equals(newPair.getKey()) && !pair.getValue().equals(newPair.getValue())) {

                    if (map.get(newPair).contains(item)) {
                        continue out;
                    }
                }

                if (newPair.getKey() > pair.getKey())
                    return item;
            }
        }
        return null;
    }


    private ArrayList<Integer> solveCell(int rowNum, int colNum, Optional<ArrayList<Integer>> posVal) {
        ArrayList<Integer> values;


        if (posVal.isPresent()) {

            if (posVal.get().isEmpty())
                return null;

            values = posVal.get();
        } else {
            values = new ArrayList<>();

            for (int i = 1; i <= theGrid.length; i++)
                values.add(i);
        }


        int[] row = getRow(rowNum);

        for (int cell : row) {
            if (cell != -1)
                values.remove((Integer) cell);
        }

        if (values.size() == 1)//1 solution!
            return values;


        int[] col = getCol(colNum);

        for (int cell : col) {
            if (cell != -1)
                values.remove((Integer) cell);
        }

        if (values.size() == 1)//1 solution!
            return values;

        int[] sqr = getSqr(rowNum, colNum);

        for (int cell : sqr) {
            if (cell != -1)
                values.remove((Integer) cell);
        }

        if (values.size() == 1)//1 solution!
            return values;

        //can be a unique value!


        return values; //no single solution found.

    }


    /**
     * Checks that the numbers in theGrid are continuous from 1 to k<sup style="font-size:100%;">2</sup>.
     *
     * @return true if all rows, columns and squares are valid.
     */

    private boolean checkNumbers() {
        for (int i = 0; i < theGrid.length; i++) {
            if (!(valid(getRow(i)) && valid(getCol(i)) && valid(getSqr(i / (int) Math.sqrt(theGrid.length), ((int) Math.sqrt(theGrid.length) * i) % theGrid.length))))
                return false;
        }
        return true;
    }

    private boolean valid(int[] check) {
        int i = 0;
        Arrays.sort(check);
        for (int number : check) {
            if (completeGrid) {
                if (number != ++i)
                    return false;
            } else {
                if (number != ++i && number != -1)
                    return false;
            }
        }
        return true;
    }

    /**
     * Checks that all rows and columns are of equal length and equal to a square number (k^2).
     *
     * @return true if both the rows and columns are equal size and square numbers.
     */
    private boolean checkSize() {
        if (theGrid == null)
            return false;

        if (isPerfectSquare()) {

            for (int[] lines : theGrid) { //check the length of each inner array match the main array.
                if (lines == null)
                    return false;

                if (lines.length != theGrid.length)
                    return false;
            }
            return true;
        }
        return false;
    }

    private boolean isPerfectSquare() { //if the length of theGrid is a perfect square (k^2)
        double sqrt = Math.sqrt(theGrid.length);
        int a = (int) sqrt;

        return Math.pow(sqrt, 2) == Math.pow(a, 2);
    }

    private int[] getRow(int rowNum) {
        return theGrid[rowNum].clone();
    }

    private int[] getCol(int colNum) {
        int[] col = new int[theGrid.length];

        for (int i = 0; i < col.length; i++)
            col[i] = theGrid[i][colNum];

        return col;
    }

    private int[] getSqr(int rowNum, int colNum) {
        int[] sqr = new int[theGrid.length];
        int squareSize = (int) Math.sqrt(theGrid.length); //grid size already checked in checkSize(), is perfect square.

        for (int i = 0; i < sqr.length; i++)
            sqr[i] = theGrid[(rowNum / squareSize * squareSize) + (i / squareSize)][((colNum / squareSize * squareSize) % theGrid.length) + (i % squareSize)];

        return sqr;
    }

    private void view(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[i].length; k++) {
                System.out.print(a[i][k] + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int emptyCellCount() {
        int count = 0;
        for (int[] cells : theGrid)
            for (int cell : cells)
                if (cell == -1)
                    count++;

        return count;
    }
}
