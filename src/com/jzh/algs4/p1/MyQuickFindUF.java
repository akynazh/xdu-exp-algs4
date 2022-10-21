package com.jzh.algs4.p1;

public class MyQuickFindUF {
    protected int[] parent; // 格点对应的祖先下标，初始化为自己的下标
    protected int originCount;
    protected int currentCount; // 当前格点数量
    protected boolean[] status; // 格点是否open

    public MyQuickFindUF(int n) {
        // N * N 网格
        originCount = n * n;
        currentCount = originCount;
        parent = new int[currentCount + 1]; // 从1开始
        status = new boolean[currentCount + 1];
        for (int i = 1; i <= currentCount; i++) {
            parent[i] = i;
            // status[i] = false; // 最开始时所有格点都关闭
        }
    }

    // 判断两个点是否有相同祖先
    public boolean connected(int p, int q) {
        return parent[p] == parent[q];
    }

    // 寻找祖先并逐步合并
    public int findParent(int p) {
        if (parent[p] != p) {
            return parent[p] = findParent(parent[p]);
        }
        return p;
    }

    // 合并2个格点
    public void union(int p, int q) {
        int rootP = findParent(p);
        int rootQ = findParent(q);
        if (rootP == rootQ) return;
        parent[rootP] = rootQ;
        currentCount--;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public int getOriginCount() {
        return originCount;
    }

    public void open(int p) {
        status[p] = true;
    }

    public boolean isOpen(int p) {
        return status[p];
    }
}
