package com.jzh.algs4.p3;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;

import java.util.Stack;

public class DijkstraSP {
    private final double[] distToG; // 顶点a到起点的步行距离
    private final double[] distToF; // 顶点a到起点的步行距离 + 顶点a到终点的欧氏距离
    private final DirectedEdge[] edgeTo;
    private final IndexMinPQ<Double> pq;
    private final Point[] points;
    private final int from;
    private final int to;

    public DijkstraSP(EdgeWeightedDigraph G, Point[] points, int from, int to) {
        this.distToG = new double[G.V()];
        this.distToF = new double[G.V()];
        this.edgeTo = new DirectedEdge[G.V()];
        this.pq = new IndexMinPQ<>(G.V());
        this.points = points;
        this.from = from;
        this.to = to;

        for (int v = 0; v < G.V(); v++) {
            distToG[v] = Double.POSITIVE_INFINITY;
            distToF[v] = Double.POSITIVE_INFINITY;
        }
        distToG[from] = 0.0;
        distToF[from] = 0.0;

        pq.insert(from, distToF[from]);
        while (!pq.isEmpty()) {
            relax(G, pq.delMin());
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distToG[w] > distToG[v] + e.weight()) {
                distToG[w] = distToG[v] + e.weight();
                distToF[w] = distToG[w] + points[v].getDistance(points[w]);
                edgeTo[w] = e;

                if (pq.contains(w)) pq.changeKey(w, distToF[w]);
                else pq.insert(w, distToF[w]);
            }
            if (w == to) {
                while(!pq.isEmpty()) pq.delMin();
                break;
            }
        }
    }

    public double distTo(int v) {
        return distToG[v];
    }

    boolean hasPathTo(int v) {
        return distToG[v] < Double.POSITIVE_INFINITY;
    }

    public Stack<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
}
