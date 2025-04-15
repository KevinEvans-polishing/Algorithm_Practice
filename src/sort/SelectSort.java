package sort;

import java.util.Arrays;

public class SelectSort {
    public static void select(int[] a) {
        // 1. 选择轮数：元素个数-1
        // 2. 每次交换的索引位置 初始值为a.length - 1, 每次递减
        for (int right = a.length - 1; right > 0; right--) {
            int max = right;
            for (int i = 0; i < right; i++) {
                if (a[max] < a[i]) {
                    max = i;
                }
            }
            if (max != right) {
                swap(a, max, right);
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = {5, 6, 3, 4, 2 ,1};
        System.out.println(Arrays.toString(a));
        select(a);
        System.out.println(Arrays.toString(a));
    }
}
