# 算法分析与设计实验报告

## 实验一：渗透问题

### 题目描述

使用合并-查找（union-find）数据结构，编写程序通过蒙特卡罗模拟（Monte Carlo simulation）来估计渗透阈值的值。

### 解决方法

随机open格点，每open一个格点，和该格点周围的已经open的格点并入同一个集合，即，它们有了相同的“祖先”。相关代码如下：

```java
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
```

当open的格点大于等于N个，系统有可能开始发生渗透，此时每open一个格点，需要开始判断系统是否渗透，判断方法是：判断最底层的任一格点是否和最顶层的任一格点处在同一个集合中，如果是，那么可以退出必然有一条从最顶层到最底层的通路，即，发生了渗透。相关代码如下：

```java
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
```

通过两种方法进行合并查找算法，不带权值的合并查找和带权值的合并查找算法。自己实现的合并查找算法主要代码如下：

查找部分：

通过递归的方法，寻找祖先并逐步合并。

```java
// 寻找祖先并逐步合并
public int findParent(int p) {
    if (parent[p] != p) {
        return parent[p] = findParent(parent[p]);
    }
    return p;
}
```

合并部分：(带权值的和不带权值的)

```java
// 合并2个格点
public void union(int p, int q) {
    int rootP = findParent(p);
    int rootQ = findParent(q);
    if (rootP == rootQ) return;
    parent[rootP] = rootQ;
    currentCount--;
}
```

```java
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
```

为了比较算法效率差异，在T=100，即每次实验次数为100下，通过逐步增大N值，计算不同N值情况下的平均运行时间，空置概率平均值，方差和置信区间。

### 实验结果与分析

实验结果如下：

########## N = 10, T = 100 时 ##########
-------------------------------------------
QuickFindUF算法：
-------------------------------------------
average use time: 0.000220s
mean: 0.6794000000000001
stddev: 0.1599293333333333
confidence: [0.6480538506666668, 0.7107461493333335]
-------------------------------------------

WeightedQuickFindUF算法：
-------------------------------------------
average use time: 0.000180s
mean: 0.6125000000000000
stddev: 0.3487944444444440
confidence: [0.5441362888888890, 0.6808637111111111]
-------------------------------------------

########## N = 50, T = 100 时 ##########
-------------------------------------------
QuickFindUF算法：
-------------------------------------------
average use time: 0.001820s
mean: 0.6255839999999999
stddev: 0.0056200158040816
confidence: [0.6244824769023999, 0.6266855230976000]
-------------------------------------------

WeightedQuickFindUF算法：
-------------------------------------------
average use time: 0.001725s
mean: 0.6066239999999997
stddev: 0.0094927911183674
confidence: [0.6047634129407997, 0.6084845870591997]
-------------------------------------------

########## N = 100, T = 100 时 ##########
-------------------------------------------
QuickFindUF算法：
-------------------------------------------
average use time: 0.018320s
mean: 0.6105490000000000
stddev: 0.0014421334333333
confidence: [0.6102663418470666, 0.6108316581529334]
-------------------------------------------

WeightedQuickFindUF算法：
-------------------------------------------
average use time: 0.015610s
mean: 0.5941875000000002
stddev: 0.0024944444318182
confidence: [0.5936985888913638, 0.5946764111086366]
-------------------------------------------

可以明显发现，带权值的合并查找算法比不带权值的合并算法所花时间小，当数据规模越来越大，差距进一步加大。

同时，随着N值的增大，对于空置概率p：

平均值：逐渐减小，带权值的合并查找算法都小于不带权值的合并查找算法；
方差：逐渐减小，带权值的合并查找算法都大于不带权值的合并查找算法；
置信区间：逐渐缩小，带权值的合并查找算法都小于不带权值的合并查找算法。