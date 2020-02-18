package edu.csu;

public class UF {
    private int[] parent;
    private int[] size;
    private int count;

    /**
     * 初始化空的联合查找数据结构
     * @param n UF中元素的数量
     */
    public UF(int n){
        if (n<0) throw new IllegalArgumentException("illegal argument");
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i=0;i<n;i++){
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * 查找p所在连通分量的根节点
     * @param p 待查找的元素
     * @return p所在树的根节点
     */
    public int find(int p){
        validate(p);
        while (p != parent[p]){
            parent[p] = parent[parent[p]]; //此处应该不需要将其父节点进行更行 甚至多访问了一次数组
            p = parent[p];
        }
        return p;
    }

    /**
     * 获取森林中元素的数量
     * @return 森林中元素的数量
     */
    public int count(){
        return count;
    }

    /**
     * 判断两个元素是否连通
     * @param p 带判断的元素
     * @param q 带判断的元素
     * @return 若返回true 说明属于同一个连通分量，否则不属于同一个连通分量
     */
    public boolean connected(int p,int q){
        return find(p) == find(q);
    }

    /**
     * 合并包含元素p 和 q的连通分量
     * @param p 待合并的元素
     * @param q 待合并的元素
     */
    public void union(int p,int q){
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        //将元素数量较少的连通分量指向元素较多的连通分量
        if (size[rootP] < size[rootQ]){
            parent[rootP] = rootQ;
            size[rootQ] +=size[rootP];
        } else if (size[rootP] > size[rootQ]){
            parent[rootQ] = rootP;
            size[rootQ] += size[rootP];
        }
        count --;
    }

    /**
     * 证实p是一个有效的索引
     * @param p
     */
    public void validate(int p){
        int n = parent.length;
        if (p<0 || p>n){
            throw new IllegalArgumentException("index " + p + "is not between 0 to" + (n-1));
        }
    }



}
