package sort;

public class BubbleSort {
    public void bubble(int[] a) {
        int j = a.length - 1; // 未排序区域的右边界
        while (true) {
            int x = 0;
            for (int i = 0; i < j; i++) {
                if (a[i] > a[i + 1]) {
                    int t = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = t;
                    x = i;
                }
            }
            j = x;
            if (j == 0) {
                break;
            }
        }
    }
}
