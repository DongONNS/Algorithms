package edu.csu;

public class Shell {
    private Shell(){};
    //按照 1 4 13 40 ......的递增序列进行希尔排序;
    public static void sort(Comparable[] a){
        int n = a.length;
        int h =1;
        while(h < n/3) h = 3 * h +1;
        while(h >= 1){
            for (int i = h;i<n;i++){
                for (int j = i;j>=h && less(a[j],a[j-1]);j-=h){
                    exch(a,j,j-1);
                }
            }
            assert isHSorted(a,h);
        }
        assert isSorted(a);
    }

/*
---------------------------------------------------
辅助函数
---------------------------------------------------
 */
    //判断v是否小于w
    private static boolean less(Comparable v,Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Object[] a,int i,int j){
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

/*
------------------------------------------------------
检查数组是否有序
------------------------------------------------------
 */
    private static boolean isSorted(Comparable[] a){
        for (int i = 1;i < a.length;i++){
            if (less(a[i],a[i-1])) return false;
        }
        return true;
    }

    private static boolean isHSorted(Comparable[] a,int h){
        for (int i =h;i<a.length;i++){
            if (less(a[i],a[i-h])) return false;
        }
        return true;
    }

    private static void  show(Comparable[] a){
        for (int i = 0;i<a.length;i++){
            StdOut.println(a[i]);
        }
    }

}
