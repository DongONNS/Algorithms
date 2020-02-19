package edu.csu;

import java.util.Comparator;

public class Insertion {
    private Insertion(){};

    public static void sort(Comparable[] a){
        int n = a.length;
        for (int i = 1;i < n;i++){
            for (int j = i;j > 0 && less(a[j],a[j-1]);j--){
                exch(a,j,j-1);
            }
            assert isSorted(a,0,i);
        }
        assert isSorted(a);
    }

    public static void sort(Comparable[] a,Comparator comparator){
        int n = a.length;
        for (int i =1;i<n;i++){
            for (int j = i;j > 0 && less(a[j],a[j-1],comparator);j--){
                exch(a,j,j-1);
            }
            assert isSorted(a,0,i,comparator);
        }
        assert isSorted(a,comparator);
    }

    public static void sort(Comparable[] a,int lo,int hi){
        for (int i = lo+1;i< hi; i++){
            for (int j = i; j>lo && less(a[j],a[j-1]);j--){
                exch(a,j,j-1);
            }
        }
        assert isSorted(a,lo,hi);
    }

    public static void sort(Comparable[] a,int lo,int hi,Comparator comparator){
        for (int i = lo+1;i < hi;i++){
            for (int j = i;j > lo && less(a[j],a[j-1],comparator);j--){
                exch(a,j,j-1);
            }
        }
        assert isSorted(a,lo,hi,comparator);
    }

/*
---------------------------------------
如下为辅助函数
---------------------------------------
 */
    //判断v是否小于w;
    public static boolean less(Comparable v,Comparable w){
        return v.compareTo(w) < 0;
    }

    //根据比较器判断v是否小于w
    public static boolean less(Object v,Object w,Comparator comparator){
        return comparator.compare(v,w) < 0;
    }

    private static void exch(Object[] a,int i,int j){
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static void exch(int[] a,int i,int j){
        int swap = a[i];
        a[i] = a[j];
        a[j] = a[i];
    }

/*
-----------------------------------------------------
    检查数组是否有序
-----------------------------------------------------
 */

    private static boolean isSorted(Comparable[] a){
        return isSorted(a,0,a.length);
    }

    private static boolean isSorted(Comparable[] a,int lo,int hi){
        for (int i = lo+1;i < hi;i++){
            if (less(a[i],a[i-1])) return false;
        }
        return true ;
    }

    private static boolean isSorted(Comparable[] a,Comparator comparator){
        return isSorted(a,0,a.length,comparator);
    }

    private static boolean isSorted(Comparable[] a,int lo,int hi,Comparator comparator){
        for (int i = lo+1;i < hi;i++){
            if (less(a[i],a[i-1],comparator)) return false;
        }
        return true;
    }

    private static void show(Comparable[] a){
        for (int i=0;i<a.length;i++){
            StdOut.println(a[i]);
        }
    }
}
