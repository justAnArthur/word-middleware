package com.fiit.logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MatrixOperations {

    /**
     * Determines if a brick intersects with a given matrix at the specified position.
     *
     * @param matrix the matrix to check for intersection
     * @param brick  the brick to check for intersection
     * @param x      the x position of the brick
     * @param y      the y position of the brick
     * @return true if the brick intersects with the matrix, false otherwise
     */
    public static boolean intersects(int[][] matrix, int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 &&
                        (outOfBounds(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Merges a brick with a given matrix at the specified position.
     *
     * @param filledFields the matrix to merge the brick with
     * @param brick        the brick to merge
     * @param x            the x position of the brick
     * @param y            the y position of the brick
     * @return the merged matrix with the brick incorporated
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);

        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY - 1][targetX] = brick[j][i];
                }
            }
        }

        return copy;
    }

    /**
     * Creates a deep copy of a given matrix.
     *
     * @param original the matrix to be copied
     * @return a new matrix which is a deep copy of the original matrix
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }

        return myInt;
    }

    /**
     * Checks if the given position is out of bounds in a matrix.
     *
     * @param matrix  the matrix to check
     * @param targetX the x-coordinate of the position to check
     * @param targetY the y-coordinate of the position to check
     * @return true if the position is out of bounds, false otherwise
     */
    private static boolean outOfBounds(int[][] matrix, int targetX, int targetY) {
        return targetX < 0 || targetY >= matrix.length || targetX >= matrix[targetY].length;
    }

    /**
     * Checks and removes any rows that are filled with non-zero values from the given matrix.
     *
     * @param matrix the matrix to check and remove rows from
     * @return an instance of the ClearRow class which contains information about the rows that were cleared, the updated matrix, and the score bonus
     */
    public static ClearRow checkRemoving(int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        List<Integer> clearedRows = new ArrayList<>();
        Deque<int[]> newRows = new ArrayDeque<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }

            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }

        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }

        int scoreBonus = 50 * clearedRows.size();

        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }
}
