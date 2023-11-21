package business;

import java.util.Arrays;

public class Validation {

    public Validation() {
    }

    public static boolean isSolution(int[][] currentMatrix, int[][] finalMatrix) {
        return Arrays.deepEquals(currentMatrix, finalMatrix);
    }

    public static boolean isValidPosition(int row, int col, int numRows, int numCols) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

}
