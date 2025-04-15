package sort;

import java.util.ArrayList;
import java.util.Arrays;

public class RadixSort {
    public static void radixSort(String[] a, int length) {
        ArrayList<String>[] buckets = new ArrayList[10];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        // 对三位数进行排序
        for (int i = length - 1; i >= 0; i--) {
            for (String s : a) {
                buckets[s.charAt(i) - '0'].add(s);
            }
            int index = 0;
            for (ArrayList<String> bucket : buckets) {
                for (String s : bucket) {
                    a[index++] = s;
                }
                bucket.clear();
            }
        }
    }

    public static void main(String[] args) {
        String[] phone = {
                "123", "142", "164", "224",
                "924", "331", "163"
        };

        radixSort(phone, 3);
        System.out.println(Arrays.toString(phone));
    }
}
