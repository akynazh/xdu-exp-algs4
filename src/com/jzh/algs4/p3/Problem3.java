
package com.jzh.algs4.p3;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Problem3 {
    public static void main(String[] args) throws FileNotFoundException {
//        Scanner in = new Scanner(System.in);
        Scanner in = new Scanner(new File("data/usa.txt"));
        int pointNum = in.nextInt();
        int edgeNum = in.nextInt();
        Point[] points = new Point[pointNum];
        for (int i = 0; i < points.length; i++) {
            int idx = in.nextInt();
            points[idx] = new Point(in.nextDouble(), in.nextDouble());
        }
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(pointNum);
        for (int i = 0; i < edgeNum; i++) {
            int v = in.nextInt();
            int w = in.nextInt();
            double d = points[v].getDistance(points[w]);
            DirectedEdge edge = new DirectedEdge(v, w, d);
            graph.addEdge(edge);
            edge = new DirectedEdge(w, v, d);
            graph.addEdge(edge);
        }

        in = new Scanner(System.in);
        System.out.print("from: ");
        int from = in.nextInt();
        System.out.print("to: ");
        int to = in.nextInt();
        DijkstraSP r = new DijkstraSP(graph, points, from, to);
        if (r.hasPathTo(to)) {
            Stack<DirectedEdge> st = r.pathTo(to);
            double distance = 0;
            if (st != null) {
                int size = st.size();
                while (st.size() > 0) {
                    DirectedEdge e = st.pop();
                    if (st.size() == size - 1) System.out.print("path: " + e.from());
                    System.out.print("->" + e.to());
                    distance += points[e.from()].getDistance(points[e.to()]);
                }
                System.out.printf("\nconsume: %f\n", distance);
            }
        } else {
            System.out.println("find no path");
        }
    }
}
