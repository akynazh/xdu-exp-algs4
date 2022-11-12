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

带权值的合并：

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

不带权值的合并：

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

```
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
```

可以明显发现，带权值的合并查找算法比不带权值的合并算法所花时间小，当数据规模越来越大，差距进一步加大。

同时，随着N值的增大，对于空置概率p：

平均值：逐渐减小，带权值的合并查找算法都小于不带权值的合并查找算法；

方差：逐渐减小，带权值的合并查找算法都大于不带权值的合并查找算法；

置信区间：逐渐缩小，带权值的合并查找算法都小于不带权值的合并查找算法。

## 实验二、

### 题目描述

实现插入排序（Insertion Sort，IS），自顶向下归并排序（Top-down Mergesort，TDM），自底向上归并排序（Bottom-up Mergesort，BUM），随机快速排序（Random Quicksort，RQ），Dijkstra 3-路划分快速排序（Quicksort with Dijkstra 3-way Partition，QD3P）。

在你的计算机上针对不同输入规模数据进行实验，对比上述排序算法的时间性能。要求对于每次输入运行10次，记录每次时间，取平均值。

### 解决方法

插入排序：从前往后开始遍历，将后面的元素插到前面已经排序好的元素中去。

```java
public static void sort(int[] arr) {
    if (arr.length == 0 || arr.length == 1) return;
    for (int i = 1; i < arr.length; i++) {
        int num = arr[i], j = i - 1;
        while (j >= 0 && arr[j] > num)
            j--;
        for (int k = i; k > j + 1; k--)
            arr[k] = arr[k - 1];
        arr[j + 1] = num;
    }
}
```

自顶向下归并排序：从顶（整体）向下（局部）进行归并，递归地进行，每次归并时左右都是已排序的元素。

```java
public static void mergeSort(int[] arr, int lo, int hi) {
    if (hi <= lo) return;
    int mid = (lo + hi) / 2;
    mergeSort(arr, lo, mid);
    mergeSort(arr, mid + 1, hi);
    merge(arr, lo, mid, hi);
}
public static void merge(int[] arr, int lo, int mid, int hi) {
    int[] tmp = new int[hi - lo + 1];
    int cur = mid + 1, o_lo = lo;
    for (int i = 0; i < tmp.length; i++) {
        if (lo > mid && cur <= hi) tmp[i] = arr[cur++];
        else if (cur > hi && lo <= mid) tmp[i] = arr[lo++];
        else {
            if (arr[lo] <= arr[cur]) tmp[i] = arr[lo++];
            else tmp[i] = arr[cur++];
        }
    }
    for (int val : tmp) arr[o_lo++] = val;
}
```

自底向上归并排序：自底（局部）向上（整体）地进行归并，以块为单位进行归并，逐步最大块中元素个数，直到块个数为总个数一半为止。

注：归并的方法和自顶向下的归并排序一样，可以直接引用 TopDownMergeSort.merge 方法。

```java
public static void sort(int[] arr) {
    int N = arr.length;
    for (int sz = 1; sz < N; sz = sz << 1) {
        for (int lo = 0; lo < N - sz; lo += sz << 1) {
            TopDownMergeSort.merge(arr, lo, lo + sz - 1, Math.min(lo + (sz << 1) - 1, N - 1));
        }
    }
}
```

随机快速排序：不断选取枢轴划分将数组划分为左右部分数组（左小右大，或反之），递归地进行。

由于测试时数组的输入即为任意的指定，所以无需再次打乱数组。

```java
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
```

Dijkstra 3-路划分快速排序：相比于普通的快速排序，将与枢轴相等的元素也作为一个考虑元素，每次递归将数组划分为小于枢轴的，等于枢轴的，大于枢轴的三个部分。

```java
public static void quickSortWithD3P(int[] arr, int lo, int hi) {
    if (hi <= lo) return;
    int lt = lo, i = lo + 1, gt = hi, val = arr[lo];
    while (i <= gt) {
        if (arr[i] < val) Common.exch(arr, i++, lt++);
        else if (arr[i] > val) Common.exch(arr, i, gt--);
        else i++;
    }
    quickSortWithD3P(arr, lo, lt - 1);
    quickSortWithD3P(arr, gt + 1, hi);
}
```

### 实验结果与分析

```
-----------------------------------------
array size: 100
InsertionSort     ->      1ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      |     0ms
TopDownMergeSort  ->      1ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      |     0ms
BottomUpMergeSort ->      3ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      |     0ms
RandomQuickSort   ->      1ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      |     0ms
QuickSortWithD3P  ->      1ms      0ms      1ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      |     0ms
-----------------------------------------

-----------------------------------------
array size: 1000
InsertionSort     ->      2ms      1ms      1ms      1ms      1ms      2ms      0ms      0ms      0ms      0ms      |     0ms
TopDownMergeSort  ->      0ms      0ms      0ms      0ms      0ms      0ms      1ms      0ms      0ms      0ms      |     0ms
BottomUpMergeSort ->      0ms      1ms      0ms      0ms      0ms      0ms      1ms      0ms      0ms      0ms      |     0ms
RandomQuickSort   ->      0ms      1ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      |     0ms
QuickSortWithD3P  ->      0ms      2ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      |     0ms
-----------------------------------------

-----------------------------------------
array size: 10000
InsertionSort     ->     13ms     15ms     12ms     14ms     13ms     13ms     12ms     14ms     13ms     13ms      |    13ms
TopDownMergeSort  ->      1ms      0ms      1ms      1ms      1ms      1ms      1ms      1ms      1ms      1ms      |     0ms
BottomUpMergeSort ->      2ms      1ms      2ms      1ms      2ms      1ms      1ms      1ms      2ms      1ms      |     1ms
RandomQuickSort   ->      1ms      0ms      1ms      0ms      1ms      1ms      0ms      1ms      0ms      1ms      |     0ms
QuickSortWithD3P  ->      1ms      1ms      0ms      1ms      1ms      0ms      1ms      1ms      1ms      0ms      |     0ms
-----------------------------------------

-----------------------------------------
array size: 100000
InsertionSort     ->   1271ms   1222ms   1247ms   1255ms   1237ms   1222ms   1207ms   1234ms   1219ms   1236ms      |  1235ms
TopDownMergeSort  ->     13ms     17ms     16ms     16ms      8ms     10ms     10ms     10ms     10ms      9ms      |    11ms
BottomUpMergeSort ->     13ms     10ms     10ms     12ms     11ms     11ms     11ms     13ms     11ms     10ms      |    11ms
RandomQuickSort   ->      6ms      8ms      6ms      7ms      6ms      6ms      7ms      6ms      8ms      6ms      |     6ms
QuickSortWithD3P  ->      6ms      6ms      7ms      7ms      6ms      8ms      7ms      6ms      6ms      7ms      |     6ms
-----------------------------------------

-----------------------------------------
array size: 1000000
InsertionSort     ->  116852ms  118249ms  116646ms  116994ms  121737ms  120262ms  116222ms  117394ms  115078ms  116368ms      | 117580ms
TopDownMergeSort  ->    127ms    106ms    102ms    105ms    105ms    109ms    163ms    108ms    103ms    106ms      |   113ms
BottomUpMergeSort ->    118ms    115ms    122ms    119ms    121ms    121ms    120ms    119ms    121ms    118ms      |   119ms
RandomQuickSort   ->     86ms     80ms     82ms     82ms     84ms     84ms     81ms     83ms     81ms     82ms      |    82ms
QuickSortWithD3P  ->     61ms     63ms     61ms     64ms     65ms     63ms     63ms     63ms     62ms     64ms      |    62ms
-----------------------------------------
```

分析结果可知，当数据规模较小时，几个算法速度几乎没有差异，当数据规模逐渐增大，各个算法差异有了非常明显的差异：

快速排序和归并排序远远快于插入排序；

自顶向下和自底向上的插入排序差异不大，普通的快速排序和三路快速排序差异不大；

当数据规模为1000000，而我定义的随机数范围是0到10000，此时重复元素较多，发现三路快速排序比普通的快速排序要快一点。

### 问题回答

1. Which sort worked best on data in constant or increasing order (i.e., already sorted data)? Why do you think this sort worked best?

当数据成这种规律排列时，使用插入排序将会是最有效的，它以最简单的方式对元素进行排序，一轮遍历即可完成，不需要无意义地进行多次递归。

2. Did the same sort do well on the case of mostly sorted data? Why or why not?

当大多数元素已经排好序，使用插入排序未必是最高效的，因为它可能需要花大量时间在元素的移动上，这在数据规模很大的情况下将耗费大量时间，即使只有少部分元素需要插入。

3. In general, did the ordering of the incoming data affect the performance of the sorting algorithms? Please answer this question by referencing specific data from your table to support your answer.

会影响。当输入数组是已排序数组时，结果如下：

```
-----------------------------------------
array size: 10000
InsertionSort     ->      1ms      0ms      1ms      0ms      1ms      0ms      0ms      0ms      0ms      0ms      |     0ms
TopDownMergeSort  ->      1ms      1ms      1ms      1ms      0ms      2ms      0ms      1ms      1ms      0ms      |     0ms
BottomUpMergeSort ->      1ms      2ms      1ms      1ms      1ms      1ms      1ms      0ms      1ms      0ms      |     0ms
RandomQuickSort   ->     10ms      9ms     11ms      9ms     10ms     10ms     10ms     11ms      9ms     10ms      |     9ms
QuickSortWithD3P  ->      2ms      1ms      1ms      2ms      2ms      2ms      1ms      2ms      2ms      1ms      |     1ms
-----------------------------------------
```

可以发现插入排序是最快的，快速排序受到了影响，慢于插入排序。

4. Which sort did best on the shorter (i.e., n = 1,000) data sets? Did the same one do better on the longer (i.e., n = 10,000) data sets? Why or why not? Please use specific data from your table to support your answer.

分析结果可知，当数据规模较小时，几个算法速度几乎没有差异：

```
-----------------------------------------
array size: 1000
InsertionSort     ->      2ms      1ms      1ms      1ms      1ms      2ms      0ms      0ms      0ms      0ms      |     0ms
TopDownMergeSort  ->      0ms      0ms      0ms      0ms      0ms      0ms      1ms      0ms      0ms      0ms      |     0ms
BottomUpMergeSort ->      0ms      1ms      0ms      0ms      0ms      0ms      1ms      0ms      0ms      0ms      |     0ms
RandomQuickSort   ->      0ms      1ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      |     0ms
QuickSortWithD3P  ->      0ms      2ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      0ms      |     0ms
-----------------------------------------
```

当数据规模逐渐增大，各个算法差异有了非常明显的差异：

快速排序和归并排序远远快于插入排序；

自顶向下和自底向上的插入排序差异不大，普通的快速排序和三路快速排序差异不大；

当数据规模为1000000，而我定义的随机数范围是0到10000，此时重复元素较多，发现三路快速排序比普通的快速排序要快一点。

```
-----------------------------------------
array size: 100000
InsertionSort     ->   1271ms   1222ms   1247ms   1255ms   1237ms   1222ms   1207ms   1234ms   1219ms   1236ms      |  1235ms
TopDownMergeSort  ->     13ms     17ms     16ms     16ms      8ms     10ms     10ms     10ms     10ms      9ms      |    11ms
BottomUpMergeSort ->     13ms     10ms     10ms     12ms     11ms     11ms     11ms     13ms     11ms     10ms      |    11ms
RandomQuickSort   ->      6ms      8ms      6ms      7ms      6ms      6ms      7ms      6ms      8ms      6ms      |     6ms
QuickSortWithD3P  ->      6ms      6ms      7ms      7ms      6ms      8ms      7ms      6ms      6ms      7ms      |     6ms
-----------------------------------------

-----------------------------------------
array size: 1000000
InsertionSort     ->  116852ms  118249ms  116646ms  116994ms  121737ms  120262ms  116222ms  117394ms  115078ms  116368ms      | 117580ms
TopDownMergeSort  ->    127ms    106ms    102ms    105ms    105ms    109ms    163ms    108ms    103ms    106ms      |   113ms
BottomUpMergeSort ->    118ms    115ms    122ms    119ms    121ms    121ms    120ms    119ms    121ms    118ms      |   119ms
RandomQuickSort   ->     86ms     80ms     82ms     82ms     84ms     84ms     81ms     83ms     81ms     82ms      |    82ms
QuickSortWithD3P  ->     61ms     63ms     61ms     64ms     65ms     63ms     63ms     63ms     62ms     64ms      |    62ms
-----------------------------------------
```