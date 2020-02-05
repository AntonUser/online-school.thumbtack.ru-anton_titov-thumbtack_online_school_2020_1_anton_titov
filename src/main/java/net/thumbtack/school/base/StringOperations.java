package net.thumbtack.school.base;

import java.util.Arrays;

public class StringOperations {
    public static int getSummaryLength(String[] strings){
        int length = 0;
        for (String elem:strings){
        length += elem.length();
        }
        return length;
    }
    //Возвращает суммарную длину строк, заданных массивом strings.

    public static String getFirstAndLastLetterString(String string){
        return string.substring(0,1) + string.substring(string.length()-1);
    }
    //Возвращает двухсимвольную строку, состоящую из начального и конечного символов заданной строки.

    public static boolean isSameCharAtPosition(String string1, String string2, int index){
        if(string1.charAt(index) == string2.charAt(index))return true;
        else return false;
    }
    //Возвращает true, если обе строки в позиции index содержат один и тот же символ, иначе false.

    public static boolean isSameFirstCharPosition(String string1, String string2, char character){
       if(string1.indexOf(Character.toString(character)) == string2.indexOf(Character.toString(character))) return true;
       else return false;
    }
    //Возвращает true, если в обеих строках первый встреченный символ character находится в
    // одной и той же позиции. Просмотр строк ведется от начала.

    public static boolean isSameLastCharPosition(String string1, String string2, char character){
        if(string1.lastIndexOf(Character.toString(character)) == string2.lastIndexOf(Character.toString(character))) return true;
        else return false;
    }
    //Возвращает true, если в обеих строках первый встреченный символ character находится в
    // одной и той же позиции. Просмотр строк ведется от конца.

    public static boolean isSameFirstStringPosition(String string1, String string2, String str){
        if(string1.indexOf(str) == string2.indexOf(str))return true;
        else return false;
    }
    //Возвращает true, если в обеих строках первая встреченная подстрока str начинается в одной и
    // той же позиции. Просмотр строк ведется от начала.

    public static boolean isSameLastStringPosition(String string1, String string2, String str){
        if(string1.lastIndexOf(str) == string2.lastIndexOf(str))return true;
        else return false;
    }
    //Возвращает true, если в обеих строках первая встреченная подстрока str начинается в одной и той же позиции.
    // Просмотр строк ведется от конца.

    public static boolean isEqual(String string1, String string2){
        if(string1.equals(string2))return true;
        else return false;
    }
   // Возвращает true, если строки равны.

    public static boolean isEqualIgnoreCase(String string1, String string2){
        if(string1.equalsIgnoreCase(string2))return true;
        else return false;
    }
    //Возвращает true, если строки равны без учета регистра (например, строки “abc” и “aBC” в этом смысле равны).

    public static boolean isLess(String string1, String string2){
        if(string1.compareTo(string2) < 0) return true;
            else return false;
    }
   // Возвращает true, если строка string1 меньше строки string2.

    public static boolean isLessIgnoreCase(String string1, String string2){
        if(string1.compareToIgnoreCase(string2) < 0)return true;
        else return false;
    }
    //Возвращает true, если строка string1 меньше строки string2 без учета регистра
    // (например, строка “abc” меньше строки “ABCd” в этом смысле).

    public static String concat(String string1, String string2){
        return string1 + string2;
    }
    //Возвращает строку, полученную путем сцепления двух строк.

    public static boolean isSamePrefix(String string1, String string2, String prefix){
        if(string1.startsWith(prefix) && string2.startsWith(prefix))return true;
        else return false;
    }
   // Возвращает true, если обе строки string1 и string2 начинаются с одной и той же подстроки prefix.

    public static boolean isSameSuffix(String string1, String string2, String suffix){
        if (string1.endsWith(suffix) && string2.endsWith(suffix))return true;
        else return false;
    }
    //Возвращает true, если обе строки string1 и string2 заканчиваются одной и той же подстрокой suffix.

    public static String getCommonPrefix(String string1, String string2){
        int i = 0;
        while (i < (Math.min(string1.length(),string2.length()))){
                if(string1.charAt(i) != string2.charAt(i)) break;
                i++;
        }
        if(i > 0)return string1.substring(0,i);
        else return "";
    }
    //Возвращает самое длинное общее “начало” двух строк. Если у строк нет общего начала, возвращает пустую строку.

    public static String reverse(String string){
         return new StringBuffer(string).reverse().toString();
    }
    //Возвращает перевернутую строку.

    public static boolean isPalindrome(String string){
        if(string.equals(new StringBuffer(string).reverse().toString()))return true;
        else return false;
    }
    //Возвращает true, если строка является палиндромом, то есть читается слева направо так же, как и справа налево.

    public static boolean isPalindromeIgnoreCase(String string){
        if(string.equalsIgnoreCase(new StringBuffer(string).reverse().toString()))return true;
        else return false;
    }
    //Возвращает true, если строка является палиндромом, то есть читается слева направо так же, как и справа налево, без учета регистра.

    public static String getLongestPalindromeIgnoreCase(String[] strings){
        int i, length = 0, number = 0;
        for (i = 0; i < strings.length; i++){
            if(strings[i].equalsIgnoreCase(new StringBuffer(strings[i]).reverse().toString())){
                if(strings[i].length()>length) {
                    length = strings[i].length();
                    number = i;
                }
            }
        }
        return strings[number];
    }
    //Возвращает самый длинный палиндром (без учета регистра) из массива заданных строк. Если в массиве нет палиндромов, возвращает пустую строку.

    public static boolean hasSameSubstring(String string1, String string2, int index, int length){
        if((string1.length() - index) < length || (string2.length() - index) < length)return false;
        else if (string1.substring(index, index + length).equals(string2.substring(index,index + length)))return true;
        else return false;
      }
    //Возвращает true, если обе строки содержат один и тот же фрагмент длиной length, начиная с позиции index.

    public static boolean isEqualAfterReplaceCharacters(String string1, char replaceInStr1, char replaceByInStr1, String string2, char replaceInStr2, char replaceByInStr2){
       return string1.replace(replaceInStr1, replaceByInStr1).equals(string2.replace(replaceInStr2,replaceByInStr2));
    }
    //Возвращает true, если после замены в string1 всех вхождений replaceInStr1 на replaceByInStr1 и
    // замены в string2 всех вхождений replaceInStr2 на replaceByInStr2 полученные строки равны.

    public static boolean isEqualAfterReplaceStrings(String string1, String replaceInStr1, String replaceByInStr1, String string2, String replaceInStr2, String replaceByInStr2){
        return  string1.replace(replaceInStr1, replaceByInStr1).equals(string2.replace(replaceInStr2, replaceByInStr2));
    }
    //Возвращает true, если после замены в string1 всех вхождений строки replceInStr1 на replaceByInStr1 и замены в
    // string2 всех вхождений replceInStr2 на replaceByInStr2 полученные строки равны.

    public static boolean isPalindromeAfterRemovingSpacesIgnoreCase(String string){
        //StringBuffer sb = new StringBuffer(sb);
        return string.replaceAll("\\s","").equalsIgnoreCase(new StringBuffer(string).reverse().toString().replaceAll("\\s",""));
    }
    //Возвращает true, если строка после выбрасывания из нее всех пробелов является палиндромом, без учета регистра.

    public static boolean isEqualAfterTrimming(String string1, String string2){
        return string1.trim().equals(string2.trim());
    }
    //Возвращает true, если две строки равны, если не принимать во внимание все пробелы в начале и конце каждой строки.

    public static String makeCsvStringFromInts(int[] array) {

            StringBuilder sb = new StringBuilder();
            if (array.length == 0) return "";
            for (int elem : array) {
                sb.append(elem + ",");
            }
            int end = sb.length() - 1;
            sb.deleteCharAt(end);
            return sb.toString();

    }
    //Для заданного массива целых чисел создает текстовую строку, в которой числа разделены знаком “запятая”
    // (т.н. формат CSV - comma separated values). Для пустого массива возвращается пустая строка.

    public static String makeCsvStringFromDoubles(double[] array){
            StringBuilder sb = new StringBuilder();
            if (array.length == 0) return "";
            for (double elem : array) {
                sb.append(String.format("%.2f", elem) + ",");
            }
             sb.delete(sb.length()-1,sb.length());
            return sb.toString();
    }
    //Для заданного массива вещественных чисел создает текстовую строку, в которой числа разделены знаком “запятая”,
    // причем каждое число записывается с двумя знаками после точки. Для пустого массива возвращается пустая строка.

    public static StringBuilder makeCsvStringBuilderFromInts(int[] array){
        StringBuilder sb = new StringBuilder();
        if (array.length == 0) return sb;
           for (int elem : array) {
                sb.append(elem + ",");
            }
            int end = sb.length() - 1;
            sb.deleteCharAt(end);
            return sb;
    }
    //То же, что и в упражнении 25, но возвращает StringBuilder.

    public static StringBuilder makeCsvStringBuilderFromDoubles(double[] array){
        StringBuilder sb = new StringBuilder();
        if (array.length == 0) return sb;

            for (double elem : array) {
                sb.append(String.format("%.2f", elem) + ",");
            }
            sb.delete(sb.length()-1,sb.length());
            return sb;
    }
   // То же, что и в упражнении 26, но возвращает StringBuilder.

    public static StringBuilder removeCharacters(String string, int[] positions){
        StringBuilder sb = new StringBuilder(string);
        for(int i = 0; i < positions.length; i++ )
            sb.deleteCharAt(positions[i]-i);
        return sb;
    }
    //Удаляет из строки символы, номера которых заданы в массиве positions.
    // Предполагается, что будут передаваться только допустимые номера, упорядоченные по возрастанию.
    // Номера позиций для удаления указаны для исходной строки. Возвращает полученный в результате StringBuilder.

    public static StringBuilder insertCharacters(String string, int[] positions, char[] characters){
        StringBuilder sb = new StringBuilder(string);
        for(int i = 0; i < positions.length; i++ )
            sb.insert(positions[i]+i, characters[i]);

        return sb;
    }
    //Вставляет в строку символы. Массивы positions и characters
    // имеют одинаковую длину. В позицию positions[i] в исходной
    // строке string вставляется символ characters[i]. Если в массиве positions один и
    // тот же номер позиции повторяется несколько раз, это значит, что в указанную позицию
    // вставляется несколько символов, в том порядке, в котором они перечислены в массиве characters.
    // Предполагается, что будут передаваться только допустимые номера, упорядоченные по неубыванию.
    // Возвращает полученный в результате StringBuilder.
}
