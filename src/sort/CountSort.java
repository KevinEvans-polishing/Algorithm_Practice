package sort;

import java.util.Arrays;

public class CountSort {
    public static void countSort(int[] a) {
        int max = a[0];
        int min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
            }
            if (min > a[i]) {
                min = a[i];
            }
        }
        int[] count = new int[max - min + 1];
        for (int v : a) { // v原始数组的元素
            count[v - min]++;
        }
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                a[index++] = i + min;
                count[i]--;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {3, 7, 4, 6, 8, 10};
        System.out.println(Arrays.toString(a));
        countSort(a);
        System.out.println(Arrays.toString(a));
    }
}
