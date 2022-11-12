package com.jzh.algs4.p2;

public class QuickSortWithD3P {
    public static void sort(int[] arr) {
        if (arr.length == 0 || arr.length == 1) return;
        quickSortWithD3P(arr, 0, arr.length - 1);
    }
    public static void quickSortWithD3P(int[] arr, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi, val = arr[lo];
        while (i <= gt) {
            if (arr[i] < val) Common.exch(arr, i++, lt++);
            else if (arr[i] > val) Common.exch(arr, i, gt--);
            else i++;
        }
        quickSortWithD3P(arr, lo, lt - 1);
        quickSortWithD3P(arr, gt + 1, hi);
    }
}
