package com.jzh.algs4.p1;

public class Percolation {
    private final MyQuickFindUF grid;
    private final int N;

    public Percolation(int N, boolean weighted) {
        if (N <= 0) throw new IllegalArgumentException();
        this.N = N;
        if (weighted) {
            grid = new MyWeightedQuickFindUF(N);
        } else {
            grid = new MyQuickFindUF(N);
        }
    }

    // 判断格点坐标是否合理
    public void validate(int i, int j) {
        if (i <= 0 || j > N) throw new IndexOutOfBoundsException();
    }

    // 获取格点对应的下标
    public int getIdx(int i, int j) {
        validate(i, j);
        return (i - 1) * N + j;
    }

    // 打开一个格点，并检查相邻4个方向，看是否能合并
    public void open(int i, int j) {
        validate(i, j);
        grid.open(getIdx(i, j));
        int[] dx = {-1, 0, 0, 1}, dy = {0, 1, -1, 0};
        for (int k = 0; k < 4; k++) {
            try {
                int m = i + dx[k], n = j + dy[k];
                validate(m, n);
                if (grid.isOpen(getIdx(m, n)) && !grid.connected(getIdx(m, n), getIdx(i, j))) {
                    grid.union(getIdx(m, n), getIdx(i, j));
                }
            } catch (Exception ignored) {
            }
        }
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return grid.isOpen(getIdx(i, j));
    }

    // 如果当前格点和最顶层的任一格点处在同一集合中则full了
    public boolean isFull(int i, int j) {
        validate(i, j);
        for (int k = 1; k <= N; k++) {
            if (grid.connected(getIdx(i, j), getIdx(1, k))) return true;
        }
        return false;
    }

    // 如果最底层格点和最顶层的任一格点处在同一集合中则percolates了
    public boolean percolates() {
        if (grid.getCurrentCount() > N * (N - 1)) return false;
        for (int i = 1; i <= N; i++) {
            if (isOpen(N, i) && isFull(N, i)) return true;
        }
        return false;
    }

    public double getRatio() {
        return (double) (grid.getOriginCount() - grid.getCurrentCount()) / (grid.getOriginCount());
    }
}
