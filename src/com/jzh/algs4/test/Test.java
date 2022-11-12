package com.jzh.algs4.test;

public class Test {
    public static void main(String[] args) {
        int[] arr = new int[111];
        arr[1] = 111111;

        test1(arr);
        System.out.println(arr.length);
        System.out.println(arr[1]);

        test2(arr);
        System.out.println(arr.length);
        System.out.println(arr[1]);
    }
    public static void test1(int[] arr) {
        int[] arr1 = new int[1166];
        arr1[1] = 1166;
        arr = arr1;
    }
    public static void test2(int[] arr) {
        arr = new int[2266];
        arr[1] = 2266;
    }
}
