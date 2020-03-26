package net.thumbtack.school.ttschool;


import java.util.*;

public class MatrixNonSimilarRows {
    private int[][] matrix;

    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = matrix;
    }


    public Set<int[]> getNonSimilarRows() {
        Set<Integer> add;
        //REVU: попробуй использовать наоборот Map<Set<Integer>, int[]>
        Map<int[], Set<Integer>> map = new HashMap<>();

        for (int[] ints : matrix) {
            add = new HashSet<>();
            for (int anInt : ints) {
                add.add(anInt);
            }
            //REVU: может использовать другой метод, не put?
            map.put(ints, add);
        }
        // тогда эта часть упростится до map.values() и преобразованию в Set
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                if (map.containsKey(matrix[i]) && map.containsKey(matrix[j])) {
                    if (map.get(matrix[i]) != null &&
                            map.get(matrix[j]) != null &&
                            map.get(matrix[i]).equals(map.get(matrix[j])) &&
                            i != j) {
                        map.remove(matrix[j]);
                    }
                }
            }
        }
        return new HashSet<>(map.keySet());
    }
}


