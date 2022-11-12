package com.jzh.algs4.p2;

public class Common {
    public static boolean isSorted(int[] arr) {
        if (arr.length == 0 || arr.length == 1) return true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i+1]) return false;
        }
        return true;
    }
    public static void exch(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
