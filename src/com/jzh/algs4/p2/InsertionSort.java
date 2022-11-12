package com.jzh.algs4.p2;

public class InsertionSort {
    public static void sort(int[] arr) {
        if (arr.length == 0 || arr.length == 1) return;
        for (int i = 1; i < arr.length; i++) {
            int num = arr[i], j = i - 1;
            while (j >= 0 && arr[j] > num)
                j--;
            for (int k = i; k > j + 1; k--)
                arr[k] = arr[k - 1];
            arr[j + 1] = num;
        }
    }
}
