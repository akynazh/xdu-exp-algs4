package com.jzh.algs4.p2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Problem2 {
    private static final int[] SIZES = {100, 1000, 10000, 100000, 1000000};

    public static void main(String[] args) {
        for (int size : SIZES) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = new Random().nextInt(10000);
            }
//            Arrays.sort(arr);
            System.out.println("-----------------------------------------");
            System.out.printf("array size: %d\n", size);
            for (int i = 0; i < 5; i++) {
                ArrayList<Long> consume_arr = new ArrayList<>();
                boolean allSuccessFlag = true;
                for (int j = 0; j < 10; j++) {
                    int[] arr1 = Arrays.copyOf(arr, arr.length);
                    long now = System.currentTimeMillis();
                    if (i == 0) {
                        if (j == 0) System.out.print("InsertionSort     -> ");
                        InsertionSort.sort(arr1);
                    } else if (i == 1) {
                        if (j == 0) System.out.print("TopDownMergeSort  -> ");
                        TopDownMergeSort.sort(arr1);
                    } else if (i == 2) {
                        if (j == 0) System.out.print("BottomUpMergeSort -> ");
                        BottomUpMergeSort.sort(arr1);
                    } else if (i == 3) {
                        if (j == 0) System.out.print("RandomQuickSort   -> ");
                        RandomQuickSort.sort(arr1);
                    } else {
                        if (j == 0) System.out.print("QuickSortWithD3P  -> ");
                        QuickSortWithD3P.sort(arr1);
                    }
                    long consume = System.currentTimeMillis() - now;
                    if (Common.isSorted(arr1)) {
                        System.out.printf(" %5dms ", consume);
                        consume_arr.add(consume);
                    } else {
                        allSuccessFlag = false;
                        break;
                    }
                }
                if (allSuccessFlag) {
                    long sum = 0;
                    for (long c : consume_arr) sum += c;
                    System.out.printf("     | %5dms\n", sum / 10);
                } else {
                    System.out.print("    fail\n");
                }
            }
            System.out.println("-----------------------------------------\n");
            InsertionSort.sort(arr);
        }
    }

}
