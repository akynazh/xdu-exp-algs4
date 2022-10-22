package com.jzh.algs4.p1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class PercolationStats {
    private final int N;
    private final int T;
    private final ArrayList<Double> ratios = new ArrayList<>();
    private final ArrayList<Double> useTimes = new ArrayList<>();
    public PercolationStats(int N, int T) {
        this.N = N;
        this.T = T;
    }

    public void startTest(boolean weighted) {
        int tries = T, seq = 1;
        while (tries != 0) {
            long startTime = System.currentTimeMillis();
            Percolation percolation = new Percolation(N, weighted);
            // monte carlo
            do {
                int i = new Random().nextInt(N) % N + 1, j = new Random().nextInt(N) % N + 1;
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                }
            } while (!percolation.percolates());
            double ratio = percolation.getRatio();
            long endTime = System.currentTimeMillis();
            double useTime = (double)(endTime - startTime) / 1000;
//            System.out.printf("test%d: ratio: %.16f\n", seq, ratio);
            ratios.add(ratio);
            useTimes.add(useTime);
            seq++;
            tries--;
        }

        writeAns(weighted);
    }

    public void writeAns(boolean weighted) {
        double avgUseTime = mean(useTimes);
        double ansMean = mean(ratios);
        double ansStddev = stddev(ansMean);
        double ansConfidenceLo = confidenceLo(ansMean, ansStddev);
        double ansConfidenceHigh = confidenceHi(ansMean, ansStddev);
        try (PrintStream ps = new PrintStream(new FileOutputStream("res/p1/res.txt", true))) {
            System.setOut(new PrintStream(ps));
            if (weighted) {
                System.out.println("WeightedQuickFindUF算法：");
            } else {
                System.out.println("QuickFindUF算法：");
            }
            System.out.println("-------------------------------------------");
            System.out.printf("average use time: %fs\n", avgUseTime);
            System.out.printf("mean: %.16f\n", ansMean);
            System.out.printf("stddev: %.16f\n", ansStddev);
            System.out.printf("confidence: [%.16f, %.16f]\n", ansConfidenceLo, ansConfidenceHigh);
            System.out.println("-------------------------------------------");
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在");
        }
    }

    public double mean(ArrayList<Double> arr) {
        double sum = 0.0;
        for (Double a : arr) {
            sum += a;
        }
        return sum / arr.size();
    }

    public double stddev(double ansMean) {
        double sum = 0.0;
        for (Double ratio : ratios) {
            sum += (ratio - ansMean) * (ratio - ansMean);
        }
        return sum / (N - 1);
    }

    public double confidenceLo(double ansMean, double ansStddev) {
        return ansMean - (1.96 * ansStddev) / Math.sqrt(T);
    }

    public double confidenceHi(double ansMean, double ansStddev) {
        return ansMean + (1.96 * ansStddev) / Math.sqrt(T);
    }

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                System.out.println("需要两个参数：N，T");
            }
            int N = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);
            try (PrintStream ps = new PrintStream(new FileOutputStream("res/p1/res.txt", true))) {
                System.setOut(new PrintStream(ps));
                System.out.printf("########## N = %d, T = %d 时 ##########\n", N, T);
                System.out.println("-------------------------------------------");
            } catch (FileNotFoundException e) {
                System.out.println("文件不存在");
            }
            PercolationStats percolationStats = new PercolationStats(N, T);
            percolationStats.startTest(false);
            percolationStats.startTest(true); // weighted
        } catch (IllegalArgumentException e) {
            System.out.println("N或T值不合法");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("坐标越界");
        }
    }
}
