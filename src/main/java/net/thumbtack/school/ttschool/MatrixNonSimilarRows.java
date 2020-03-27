package net.thumbtack.school.ttschool;


import java.util.*;

public class MatrixNonSimilarRows {
    private int[][] matrix;

    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = matrix;
    }

    public Set<int[]> getNonSimilarRows() {
        Set<Integer> add;
        Map<Set<Integer>, int[]> map = new HashMap<>();

        for (int[] ints : matrix) {
            add = new HashSet<>();
            for (int anInt : ints) {
                add.add(anInt);
            }
            map.putIfAbsent(add, ints);
        }

        return new HashSet<>(map.values());
    }
}


