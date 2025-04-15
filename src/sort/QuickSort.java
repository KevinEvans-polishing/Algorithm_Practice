package sort;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {
    public static void sort(int[] a) {
        quick(a, 0, a.length - 1);
    }

    private static void quick(int[] a, int left, int right) {
        if (left == right) {
            return;
        }
        int p = partition(a, left, right); // p代表基准点索引
        quick(a, left, p - 1);
        quick(a, p + 1, right);
    }


    private static int partition(int[] a, int left, int right) {
//        int pv = a[right]; // 基准点元素的值
//        int i = left;
//        int j = left;
//        while (j < right) {
//            if (a[j] < pv) { // 找到比基准点小的值,同时说明没找到大的
//                if (i != j) {
//                    swap(a, i , j);
//                }
//                i++;
//            }
//            j++;
//        }
//        swap(a, i, right);
//        return i;
        int randomIndex = ThreadLocalRandom.current().nextInt(right - left + 1) + left;
        swap(a, left, randomIndex);
        int pv = a[left];
        int i = left + 1;
        int j = right;
        while (i <= j) {
            while (i <= j && a[i] < pv) {
                i++;
            }
            while (i <= j && a[j] > pv) {
                j--;
            }
            if (i <= j) {
                swap(a, i, j);
                i++;
                j--;
            }
        }
        swap(a, j, right);
        return j;
    }


    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}

