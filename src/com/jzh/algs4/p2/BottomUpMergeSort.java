package com.jzh.algs4.p2;

public class BottomUpMergeSort {
    public static void sort(int[] arr) {
        int N = arr.length;
        for (int sz = 1; sz < N; sz = sz << 1) {
            for (int lo = 0; lo < N - sz; lo += sz << 1) {
                TopDownMergeSort.merge(arr, lo, lo + sz - 1, Math.min(lo + (sz << 1) - 1, N - 1));
            }
        }
    }
}
