package edu.csu;

public class Quick3Way {
    private Quick3Way(){}
    public static void sort(Comparable[] a){
        StdRandom.shuffle(a);
        sort(a,0,a.length-1);
        assert isSorted(a);
    }

    public static void sort(Comparable[] a,int lo,int hi){
        if (hi <= lo) return;
        int lt = lo,gt = hi;
        Comparable v = a[lo];
        int i =lo + 1;
        while(i <= gt){
            int cmp = a[i].compareTo(v);
            if(cmp < 0) exch(a,lt++,i++);
            if (cmp > 0) exch(a,i,gt--);
            else i++;
        }
        sort(a,lo,lt-1);
        sort(a,gt+1,hi);
        assert isSorted(a,lo,hi);
    }

    private static void exch(Comparable[] a,int i,int j){
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean less(Comparable[] a,int i,int j){
        return a[i].compareTo(a[j]) < 0;
    }

    private static boolean isSorted(Comparable[] a){
        return isSorted(a,0,a.length-1);
    }

    private static boolean isSorted(Comparable[] a,int lo,int hi){
        for (int i = lo+1;i<=hi;i++){
            if (less(a,i,i-1)) return false;
        }
        return true;
    }

    public static void show(Comparable[] a){
        for (int i = 0;i<a.length;i++){
            StdOut.println(a[i]);
        }
    }
}
