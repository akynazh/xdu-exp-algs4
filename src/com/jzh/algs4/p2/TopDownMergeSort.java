package com.jzh.algs4.p2;

public class TopDownMergeSort {
    public static void sort(int[] arr) {
        if (arr.length == 0 || arr.length == 1) return;
        mergeSort(arr, 0, arr.length - 1);
    }
    public static void mergeSort(int[] arr, int lo, int hi) {
        if (hi <= lo) return;
        int mid = (lo + hi) / 2;
        mergeSort(arr, lo, mid);
        mergeSort(arr, mid + 1, hi);
        merge(arr, lo, mid, hi);
    }
    public static void merge(int[] arr, int lo, int mid, int hi) {
        int[] tmp = new int[hi - lo + 1];
        int cur = mid + 1, o_lo = lo;
        for (int i = 0; i < tmp.length; i++) {
            if (lo > mid && cur <= hi) tmp[i] = arr[cur++];
            else if (cur > hi && lo <= mid) tmp[i] = arr[lo++];
            else {
                if (arr[lo] <= arr[cur]) tmp[i] = arr[lo++];
                else tmp[i] = arr[cur++];
            }
        }
        for (int val : tmp) arr[o_lo++] = val;
    }
}
