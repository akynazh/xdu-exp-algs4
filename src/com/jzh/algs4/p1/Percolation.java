package com.jzh.algs4.p1;

import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {
    private int[][] matrix;
    private final int SIZE;

    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException();
        SIZE = N;
        matrix = new int[N+1][N+1]; // all 0, blocked
    }

    public void open(int i, int j) {
        if (i <= 0 || i > SIZE || j <= 0 || j > SIZE) throw new IndexOutOfBoundsException();
        matrix[i][j] = 1; // 1, open
    }

    public boolean isOpen(int i, int j) {
        return matrix[i][j] == 1;
    }

    public boolean isFull(int i, int j) {
        return matrix[i][j] == 0;
    }

    public boolean percolates() {
        QuickFindUF quickFindUF = new QuickFindUF(SIZE);

        return false;
    }

    public static void main(String[] args) {
        try {

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } finally {

        }
    }
}
