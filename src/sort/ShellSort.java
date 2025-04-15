package sort;

public class ShellSort {
    public static void shellSort(int[] a) {
        for (int gap = a.length >> 1; gap >= 1; gap >>= 1) {
            for (int low = gap; low < a.length; low++) {
                int t = a[low];
                int i = low - gap;
                while (i >= 0 && t < a[i]) {
                    a[i + gap] = a[i];
                    i -= gap;
                }

                if (i != low - gap) {
                    a[i + gap] = t;
                }
            }
        }
    }
}
