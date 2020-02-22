package edu.csu;

public class Heap {

    private Heap(){}

    public static void sort(Comparable[] pq){
        int n = pq.length;
        for (int i = n/2;i >= 1;i++){
            sink(pq,i,n);
        }
        while(n > 1){
            exch(pq,1,n--);
            sink(pq,1,n);
        }
    }

    private static void sink(Comparable[] pq,int k,int n){
        while(2 * k < n){
            int j = 2 * k;
            if (j < n && less(pq, j,j+1)) j++;
            if (!less(pq,k,j)) break;
            exch(pq,k,j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq,int i,int j){
        return pq[i-1].compareTo(pq[j-1]) < 0;
    }

    private static void exch(Object[] pq,int i,int j){
        Object swap = pq[i-1];
        pq[i-1] = pq[j-1];
        pq[j-1] = swap;
    }

    public static void show(Comparable[] pq){
        for (int i = 0;i<pq.length;i++){
            StdOut.println(pq[i]);
        }
    }
}
