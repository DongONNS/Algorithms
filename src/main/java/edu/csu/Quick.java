package edu.csu;

public class Quick {

    private Quick(){}

    public static void sort(Comparable[] a){
        StdRandom.shuffle(a);
        sort(a,0,a.length-1);
        assert isSorted(a);
    }

    public static void sort(Comparable[] a,int lo,int hi){
        if (hi <= lo) return;
        int j = partition(a,lo,hi);
        sort(a,lo,j-1);
        sort(a,j+1,hi);
        assert isSorted(a,lo,hi);
    }

    private static int partition(Comparable[] a,int lo,int hi){
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while(true){
            while(less(a[++i],v)){
                if (i == hi) break;
            }
            while(less(v,a[--j])){
                if (j == lo) break;
            }
            if (i >= j) break;

            exch(a,i,j);
        }
        exch(a,lo,j);
        return j;
    }

    public static Comparable select(Comparable[] a,int k){
        if (k < 0 || k > a.length-1)
            throw new IllegalArgumentException("index is out of the bound");

        StdRandom.shuffle(a);
        int lo = 0,hi =a.length-1;
        while(lo < hi){
            int i = partition(a,lo,hi);
            if (i > k) hi = i -1;
            else if (i < k) lo =i + 1;
            else return a[i];
        }
        return a[lo];
    }

    private static boolean less(Comparable v,Comparable w){
        if (v == w) return false;
        return v.compareTo(w) < 0;
    }

    private static void exch(Object[] a,int i,int j){
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static void show(Comparable[] a){
        for (int i = 0;i < a.length;i++){
            StdOut.println(a[i]);
        }
    }

    private static boolean isSorted(Comparable[] a){
        return isSorted(a,0,a.length-1);
    }

    private static boolean isSorted(Comparable[] a,int lo,int hi){
        for (int i = lo+1;i <= hi;i++){
            if (less(a[i],a[i-1])) return false;
        }
        return true;
    }
}
