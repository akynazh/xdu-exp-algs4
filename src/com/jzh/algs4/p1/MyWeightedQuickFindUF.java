package com.jzh.algs4.p1;

public class MyWeightedQuickFindUF extends MyQuickFindUF{
    protected int[] weight;

    public MyWeightedQuickFindUF(int n) {
        super(n);
        weight = new int[originCount];
        for (int i = 0; i < originCount; i++) {
            weight[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int rootP = findParent(p);
        int rootQ = findParent(q);
        if (rootP == rootQ) return;

        if (weight[rootP] < weight[rootQ]) {
            parent[rootP] = rootQ;
            weight[rootQ] += weight[rootP];
        } else {
            parent[rootQ] = rootP;
            weight[rootP] += weight[rootQ];
        }
        currentCount--;
    }
}
