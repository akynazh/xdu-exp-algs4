package com.jzh.algs4.p2;

import java.util.Arrays;
import java.util.Collections;

public class RandomQuickSort {
    public static void sort(int[] arr) {
        if (arr.length == 0 || arr.length == 1) return;
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int lo, int hi) {
        if (hi <= lo) return;
        int piv = partition(arr, lo, hi);
        quickSort(arr, lo, piv - 1);
        quickSort(arr, piv + 1, hi);
    }

    public static int partition(int[] arr, int lo, int hi) {
        int val = arr[lo], i = lo, j = hi;
        while (i < j) {
            while (i < j && arr[j] >= val) j--;
            arr[i] = arr[j];
            while (i < j && arr[i] <= val) i++;
            arr[j] = arr[i];
        }
        arr[i] = val;
        return i;
    }
}
