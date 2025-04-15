package sort;

import java.util.Arrays;

import static java.lang.Math.min;

public class MergeSort {
    public static void mergeSort(int[] a1) {
        int n =a1.length;
        int[] a2 = new int[n];
        // width是有序区间的宽度
        for (int width = 1; width < n;width *= 2) {
            // left待合并区间左边界，right右边界
            for (int left = 0; left < n; left += 2 * width) {
                int right = min(left + 2 * width - 1, n - 1);
                int m = min(left + width - 1, n - 1);
                merge(a1, left, m, m + 1, right, a2);
            }
            System.arraycopy(a2, 0, a1, 0 ,n);
        }
    }

    private static void split(int[] a1, int[] a2, int left, int right) {
        // 2.治
        if (right - left <= 32) {
            insertion(a1, left, right);
        }
        // 1.分
        int m = (left + right) >>> 1;
        split(a1, a2, left, m);
        split(a1, a2, m + 1, right);
        // 3.合
        merge(a1, left, m, m + 1, right, a2);
        System.arraycopy(a2, left, a1, left, right - left + 1);
    }

    public static void insertion(int[] a, int left, int right) {
        for (int low = left + 1; low < right; low++) {
            int t = a[low];
            int i = low - 1;
            while (i >= left && t < a[i]) {
                a[i + 1] = a[i];
                i--;
            }

            if (i != low - 1) {
                a[i + 1] = t;
            }
        }
    }

    /**
     *
     * @param a1 原始数组
     * @param i 第一个有序范围的开始
     * @param iEnd 第一个有序范围的结束
     * @param j 第二个有序范围的开始
     * @param jEnd 第二个有序范围的结束
     * @param a2 临时数组
     */
    private static void merge(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2) {
        int k = i;
        while (i <= iEnd && j <= jEnd) {
            if (a1[i] < a1[j]) {
                a2[k] = a1[i];
                i++;
            } else {
                a2[k] = a1[j];
                j++;
            }
            k++;
        }
        // 第一个区间没有元素了
        // 则把第二个区间的剩余元素复制到临时数组中
        // 因为这时可能第二个区间的没有走完
        if (i > iEnd) {
            System.arraycopy(a1, j, a2, k, jEnd - j + 1);
        }
        if (j > jEnd) {
            System.arraycopy(a1, i, a2, k, iEnd - i + 1);
        }
    }

    public static void main(String[] args) {
        int[] a = {3, 7, 4, 6, 8, 10};
        System.out.println(Arrays.toString(a));
        mergeSort(a);
        System.out.println(Arrays.toString(a));
    }
}
