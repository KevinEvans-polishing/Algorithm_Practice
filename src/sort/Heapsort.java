package sort;

import java.util.Arrays;

public class Heapsort {
    public static void heapSort(int[] a) {
        heapify(a, a.length);
        for (int right = a.length - 1; right > 0; right--) {
            swap(a, 0, right);
            down(0, a, right);
        }
    }

    private static void heapify(int[] a, int size) {
        // 最后一个非叶子节点的索引是size / 2 - 1(规律，不用过多关注)
        for (int i = size / 2 - 1; i >= 0; i--) {
            down(i, a, size);
        }
    }

    private static void down(int parent, int[] array, int size) {
        while (true) {
            int left = parent * 2 + 1;
            int right = left + 1;// left + 1
            int max = parent;

            if (left < size && array[left] > array[max]) {
                max = left;
            }
            if (right < size && array[right] > array[max]) {
                max = right;
            }
            if (max == parent) {
                break;
            }
            swap(array, max, parent);
            parent = max;
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 2, 7, 6, 4, 5};
        System.out.println(Arrays.toString(a));
        heapSort(a);
        System.out.println(Arrays.toString(a));
    }
}
