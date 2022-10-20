package com.jzh.algs4.p1;

import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {
    private MyQuickFindUF sites;

    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException();
        sites = new MyQuickFindUF(N * N);
    }

    public void open(int i, int j) {
        if (i <= 0 || i > SIZE || j <= 0 || j > SIZE) throw new IndexOutOfBoundsException();
        int idx = (i - 1) * SIZE + j;

    }

    public boolean isOpen(int i, int j) {

    }

    public boolean isFull(int i, int j) {

    }

    public boolean percolates() {
        for (int i = 1; i <= SIZE; i++) {
            for (int j = 1; j <= SIZE; j++) {
                int firstLineSiteIdx = i, lastLineSiteIdx = (SIZE - 1) * SIZE + j;
                if (quickFindUF.connected(firstLineSiteIdx, lastLineSiteIdx)) return true;
            }
        }
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
