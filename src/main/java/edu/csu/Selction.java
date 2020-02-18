package edu.csu;

import com.sun.security.auth.callback.TextCallbackHandler;

import java.util.Comparator;

public class Selction {
    //这个类不能实例化
    private Selction(){}

    /**
     * 把数组按自然排序进行升序排序;
     * @param a 待排序的数组
     */
    public static void sort(Comparable[] a){
        int n = a.length;
        for (int i=0;i < n;i++) {
            int min = i;
            for (int j = i+1;j < n;j++){
                if (less(a[j],a[min])) min = j;
            }
            exch(a,i,min);
            assert isSorted(a,0,i);
        }
        assert isSorted(a);
    }

    //按照比较器顺序排序数组
    public static void sort(Object[] a, Comparator comparator){
        int n = a.length;
        for (int i = 0;i < n;i++){
            int min = i;
            for (int j = i+1;j < n;j++){
                if (less(comparator,a[j],a[min])) min = j;
            }
            exch(a,i,min);
            assert isSorted(a,comparator,0,i);
        }
        assert isSorted(a,comparator);
    }

    //比较数组元素大小
    private static boolean less(Comparable v,Comparable w){
        return v.compareTo(w) < 0;
    }

    //通过比较器比较两个数的大小
    private static boolean less(Comparator comparator,Object v,Object w){
        return comparator.compare(v,w) < 0;
    }

    //交换数组中的两个有序
    private static void exch(Object[] a,int i,int j){
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    //比较数组是否有序
    private static boolean isSorted(Comparable[] a){
        return isSorted(a,0,a.length-1);
    }

    //比较数组是否有序
    private static boolean isSorted(Comparable[] a,int lo ,int hi){
        for (int i = lo+1;i <= hi; i++){
            if (less(a[i],a[i-1])) return false;
        }
        return true;
    }

    //通过比较器判断数组是否有序
    private static boolean isSorted(Object[] a,Comparator comparator){
        return isSorted(a,comparator,0,a.length-1);
    }

    //通过比较器判断数组是否有序
    private static boolean isSorted(Object[] a,Comparator comparator,int lo,int hi){
        for (int i = lo+1;i <= hi;i++){
            if (less(comparator,a[i],a[i-1]))  return false;
        }
        return true;
    }

    //按标准输出输出数组
    private static void show(Comparable[] a){
        for (int i=0;i<a.length;i++){
            StdOut.println(a[i]);
        }
    }
}
