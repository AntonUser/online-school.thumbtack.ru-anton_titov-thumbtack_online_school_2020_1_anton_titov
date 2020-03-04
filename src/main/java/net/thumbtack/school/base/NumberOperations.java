package net.thumbtack.school.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

//REVU: попробуй выполнить команду Ctrl + Alt + L
// после ее выполнения должно произойти автоформатирование
// все тела циклов, if-условий должны быть в {} и начинаться с новой строки
public class NumberOperations {
    public static Integer find(int[] array, int value) {

        for (int i = 0; i < array.length; i++) {
            //REVU например тут будет
            //if (array[i] == value) {
            //  return i;
            //}
            if (array[i] == value) return i;
        }
        return null;
    }

    public static Integer find(double[] array, double value, double eps) {

        for (int i = 0; i < array.length; i++) {
            if (Math.abs(array[i]) <= value + eps && Math.abs(array[i]) >= value - eps) return i;
        }
        return null;
    }

    public static Double calculateDensity(double weight, double volume, double min, double max) {
        Double dou = weight / volume;
        if (dou.compareTo(min) >= 0 && dou.compareTo(max) <= 0) return dou;
        else return null;
    }

    public static Integer find(BigInteger[] array, BigInteger value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) return i;
        }
        return null;
    }

    public static BigDecimal calculateDensity(BigDecimal weight, BigDecimal volume, BigDecimal min, BigDecimal max) {
        BigDecimal bd = weight.divide(volume);
        if (bd.compareTo(max) <= 0 && bd.compareTo(min) >= 0)
            return bd;
        return null;
    }
}
