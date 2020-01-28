package net.thumbtack.school.introduction;
import java.util.Arrays;

public class FirstSteps {

    public int sum(int x, int y){
        return x + y;
    }
    //Возвращает сумму чисел x и y.

    public int mul(int x, int y){
        return x * y;
    }
    //Возвращает произведение чисел x и y.

    public int div(int x, int y){
        return x / y;
    }
    //Возвращает частное от деления чисел x и y. Гарантируется, что y != 0.

    public int mod(int x, int y){
        return x % y;
    }
    // Возвращает остаток от деления чисел x и y. Гарантируется, что y != 0.

    public boolean isEqual(int x, int y){
        if (x == y)return true;
        else return false;
    }
    //Возвращает true, если  x равен y, иначе false.

    public boolean isGreater(int x, int y){
        if(x > y) return true;
        else return false;
    }
    // Возвращает true, если  x больше y, иначе false.

    public boolean isInsideRect(int xLeft, int yTop, int xRight, int yBottom, int x, int y){
       if(x >= xLeft && x <= xRight && y >= yTop && y <= yBottom) return true;
       else return false;
    }
    // Прямоугольник с горизонтальными и вертикальными сторонами, задан двумя точками - левой верхней (xLeft, yTop) и
    // правой нижней (xRight, yBottom). На плоскости OXY ось X направлена вправо, ось Y - вниз. Дана еще одна точка с координатами (x, y).
    // Гарантируется, что xLeft < xRight и yTop < yBottom. Метод должен возвращать true, если точка лежит внутри прямоугольника ,
    // иначе false. Если точка лежит на границе прямоугольника, то считается, что она лежит внутри него.

    public int sum(int[] array){
        if(array.length == 0){return 0;}
        else return Arrays.stream(array).sum();
    }
    // Возвращает сумму чисел, заданных одномерным массивом array. Для пустого одномерного массива возвращает 0.

    public int mul(int[] array){
        int mul = 1;
        if(array.length == 0){return 0;}
        else{
            for(int i = 0; i < array.length; i++)
            mul*=array[i];
            return mul;
            }
    }
    //Возвращает произведение чисел, заданных одномерным массивом array. Для пустого одномерного массива возвращает 0.

    public int min(int[] array){
        int min = Integer.MAX_VALUE;
        if(array.length==0)return Integer.MAX_VALUE;
        else{   for (int i = 0; i < array.length; i++){
                if(array[i] < min) min=array[i];}
                return min;
        }
    }
    // Возвращает минимальное из чисел, заданных одномерным массивом array. Для пустого одномерного массива возвращает Integer.MAX_VALUE.

    public int max(int[] array){
        int max = Integer.MIN_VALUE;
        if(array.length==0)return Integer.MIN_VALUE;
        else{   for (int i = 0; i < array.length; i++){
            if(array[i] > max) max=array[i];}
            return max;
        }
    }
    // Возвращает максимальное из чисел, заданных одномерным массивом array. Для пустого одномерного массива возвращает Integer.MIN_VALUE.

    public double average(int[] array){
        double sum = 0;
        if(array.length==0)return 0;
        else {
            for (int i = 0; i < array.length; i++){
                sum += array[i];
            }
      return sum/array.length;
        }
    }
    // Возвращает среднее значение для чисел, заданных одномерным массивом array. Для пустого одномерного массива возвращает 0.

    public boolean isSortedDescendant(int[] array){
        boolean check = true;
        if(array.length==0)return true;
        else{
        for(int i = 1; i < array.length; i++) {
        if(array[i-1] <= array[i])check = false;
        }
        return check;}
    }
    // Возвращает true, если одномерный массив array строго упорядочен по убыванию, иначе false. Пустой одномерный массив считается упорядоченным.

    public void cube(int[] array){
        for (int i = 0; i < array.length; i++)
            array[i] = (int)Math.pow(array[i],3);
    }
    // Возводит все элементы одномерного массива array в куб.

    public boolean find(int[]array, int value){
       boolean check = false;
        for (int i=0; i < array.length; i++){
            if(value == array[i])check = true;}
        return  check;
    }
    //Возвращает true, если в одномерном массиве array имеется элемент, равный value, иначе false.

    public void reverse(int[]array){
        int buf;
        if(array.length!=0||array.length!=1){
        for (int i = 0; i < (int)array.length/2; i++)
        {
            buf = array[i];
            array[i] = array[array.length-1-i];
            array[array.length - 1 - i] = buf;
        }
    }}
    //Переворачивает одномерный массив array, то есть меняет местами 0-й и последний, 1-й и предпоследний и т.д. элементы.

    public boolean isPalindrome(int[]array){
        boolean check = true;
        if(array.length == 0 || array.length == 1)return true;
        else{
            for (int i = 0; i < array.length/2; i++)
            {
                if(array[i] != array[array.length-1 - i]) check = false;
            }
            return check;}
    }
    //Возвращает true, если одномерный массив является палиндромом, иначе false. Пустой массив считается палиндромом.

    public int sum(int[][] matrix){
        int sum = 0;
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
    //Возвращает сумму чисел, заданных двумерным массивом matrix.

    public int max(int[][] matrix){
        int max = Integer.MIN_VALUE;
        if(matrix.length == 0)return max;
        else {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                if(matrix[i][j] > max)max = matrix[i][j];
            }
        }
        return max;}
        }
   // Возвращает максимальное из чисел, заданных двумерным массивом matrix. Для пустого двумерного массива возвращает Integer.MIN_VALUE.

    public int diagonalMax(int[][] matrix){
        int max = Integer.MIN_VALUE;
        if(matrix.length == 0)return max;
        else {
            for(int i = 0; i < matrix.length; i++)
            {
                for(int j = 0; j < matrix[i].length; j++)
                {
                    if(i==j){
                        if(matrix[i][j] > max) max = matrix[i][j];
                            }
                }
            }
            return max;}

    }
    //Возвращает максимальное из чисел, находящихся на главной диагонали квадратного двумерного массива matrix. Для пустого двумерного массива возвращает Integer.MIN_VALUE.

    public boolean isSortedDescendant(int[][] matrix){
        boolean check = true;
        if(matrix.length==0)return false;
        else {
        for(int i = 0; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j - 1] <= matrix[i][j]) check = false;
            }
        }
        return check;
    }}
   // Возвращает true, если все строки двумерного массива matrix строго упорядочены по убыванию, иначе false. Пустая строка считается упорядоченной. Разные строки массива matrix могут иметь разное количество элементов. При написании метода рекомендуется внутри него вызвать метод из п. 13.
}
