package com.jzh.algs4.p1;

public class MyQuickFindUF {
    private int[] id; // site id
    private int count; // site count
    private boolean[] status; // site open or not

    // initial
    public MyQuickFindUF(int n) {
        count = n;
        id = new int[n];
        status = new boolean[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            status[i] = false; // at the beginning, all full
        }
    }

    // return the count of site
    public int count() {
        return count;
    }

    // get site's id
    public int find(int p) {
        validate(p);
        return id[p];
    }

    // whether the site is opened
    public boolean opened(int p) {
        validate(p);
        return status[p];
    }

    // open a site
    public void open(int p) {
        validate(p);
        status[p] = true;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    // check if 2 sites are connected
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return id[p] == id[q];
    }

    // union 2 sites
    public void union(int p, int q) {
        validate(p);
        validate(q);
        int pID = id[p];
        int qID = id[q];
        if (pID == qID) return;
        for (int i = 0; i < id.length; i++)
            if (id[i] == pID) id[i] = qID;
        count--;
    }
}
