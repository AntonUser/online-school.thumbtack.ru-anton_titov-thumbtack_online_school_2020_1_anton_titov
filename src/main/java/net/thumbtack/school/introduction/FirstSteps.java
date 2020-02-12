package net.thumbtack.school.introduction;

import java.util.Arrays;

public class FirstSteps {

    public int sum(int x, int y) {
        return x + y;
    }

    public int mul(int x, int y) {
        return x * y;
    }

    public int div(int x, int y) {
        return x / y;
    }

    public int mod(int x, int y) {
        return x % y;
    }

    public boolean isEqual(int x, int y) {
        if (x == y) return true;
        else return false;
    }

    public boolean isGreater(int x, int y) {
        return x > y;
    }

    public boolean isInsideRect(int xLeft, int yTop, int xRight, int yBottom, int x, int y) {
        return x >= xLeft && x <= xRight && y >= yTop && y <= yBottom;
    }

    public int sum(int[] array) {
        if (array.length == 0) {
            return 0;
        } else return Arrays.stream(array).sum();
    }

    public int mul(int[] array) {
        int mul = 1;
        if (array.length == 0) {
            return 0;
        }
        for (int elem : array) mul *= elem;
        return mul;
    }

    public int min(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) min = array[i];
        }
        return min;
    }

    public int max(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int elem : array)
            if (elem > max) max = elem;

        return max;
    }

    public double average(int[] array) {
        if (array.length == 0) return 0;
        return (double) sum(array) / (double) array.length;
    }

    public boolean isSortedDescendant(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] <= array[i]) return false;
        }
        return true;
    }

    public void cube(int[] array) {
        for (int i = 0; i < array.length; i++)
            array[i] = array[i] * array[i] * array[i];
    }

    public boolean find(int[] array, int value) {
        for (int elem : array)
            if (value == elem) return true;

        return false;
    }

    public void reverse(int[] array) {
        int buf;
        for (int i = 0; i < (int) array.length / 2; i++) {
            buf = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = buf;
        }
    }

    public boolean isPalindrome(int[] array) {

        for (int i = 0; i < array.length / 2; i++)
            if (array[i] != array[array.length - 1 - i])
                return false;

        return true;
    }

    public int sum(int[][] matrix) {
        int sum = 0;
        for (int[] elem : matrix)
            sum += sum(elem);

        return sum;
    }

    public int max(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int[] elem : matrix)
            if (max < max(elem)) max = max(elem);

        return max;
    }

    public int diagonalMax(int[][] matrix) {
        int max = Integer.MIN_VALUE, j = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][j] > max) max = matrix[i][j];
            j++;
        }
        return max;
    }

    public boolean isSortedDescendant(int[][] matrix) {

        for (int[] elem : matrix)
            if (!isSortedDescendant(elem)) return false;
        return true;
    }
}
