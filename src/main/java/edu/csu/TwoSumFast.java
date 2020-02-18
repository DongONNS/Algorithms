package edu.csu;

import java.util.Arrays;

public class TwoSumFast {
    /**
     * 计算数组中和为0的整数对
     */
    public int count(int[] a){
        Arrays.sort(a);
        int N = a.length;
        int cnt = 0;
        for (int i = 0;i<N;i++){
            if (BinarySearch.rank(-a[i],a) > i)
                cnt++;
        }
        return cnt;
    }
}
