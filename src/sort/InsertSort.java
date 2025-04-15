package sort;

import java.util.Arrays;

public class InsertSort {
    public static void insertSort(int[] a) {
        for (int low = 1; low < a.length; low++) {
            int t = a[low];
            int i = low - 1;
            while (i >= 0 && t < a[i]) {
                a[i + 1] = a[i];
                i--;
            }

            if (i != low - 1) {
                a[i + 1] = t;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 2, 7, 6, 4, 5};
        System.out.println(Arrays.toString(a));
        insertSort(a);
        System.out.println(Arrays.toString(a));
    }
}
