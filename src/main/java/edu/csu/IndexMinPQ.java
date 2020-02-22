package edu.csu;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {

    private int maxN;//优先队列元素的最大数量
    private int n;      //优先队列中元素的数量
    private int[] pq;   //使用基于1的索引建立二叉堆
    private int[] qp;   //pq数组的倒数，qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys; //keys[i]代表i的优先级

    /**
     * 初始化索引优先队列
     * @param maxN 优先队列的元素数量
     */
    public IndexMinPQ(int maxN){
        if (maxN < 0) throw new IllegalArgumentException("  ");
        this.maxN = maxN;
        n = 0;
        keys = (Key[])new Comparable[maxN+1];
        pq = new int[maxN+1];
        qp = new int[maxN+1];
        for (int i = 0; i<=maxN;i++){
            qp[i] = -1;
        }
    }

    /**
     * 判断队列是否为空
     * @return true说明队列非空，否则队列为空
     */
    public boolean isEmpty(){
        return n==0;
    }

    /**
     * 返回队列的大小
     * @return 队列中元素的数量
     */
    public int size(){
        return n;
    }

    /**
     * 判断队列是否包含指定元素
     * @param k 缩影
     * @return 若为true说明包含相应的元素，否则不包含
     */
    public boolean contains(int k){
        validateIndex(k);
        return qp[k] != -1;
    }

    /**
     * 向队列中插入元素
     * @param k 索引
     * @param key 索引相应的元素
     */
    public void insert(int k,Key key) {
        validateIndex(k);
        if (contains(k)) throw new IllegalArgumentException("the argu is already exist");
        n++;
        qp[k] = n;          //qp表示的是索引k在二叉堆中的位置
        pq[n] = k;          //pq表示的是二叉堆中相应位置的元素对应的索引；
        keys[k] = key;
        swim(n);
    }

    public int min(){
        if (n == 0) throw new NoSuchElementException();
        return pq[1];
    }

    public Key minKey(){
        if (n == 0) throw new NoSuchElementException();
        return keys[pq[1]];
    }

    public int delMin(){
        if (n == 0) throw new NoSuchElementException();
        int min = pq[1];
        exch(1,n--);
        sink(1);
        assert min == pq[n+1];
        qp[min] = -1;
        pq[n+1] = -1;
        keys[min] = null;
        return min;
    }

    public Key KeyOf(int k){
        validateIndex(k);
        if (!contains(k)) throw new NoSuchElementException();
        return keys[k];
    }

    public void changeKey(int i,Key key){
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException();
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    public void change(int i,Key key){
        changeKey(i,key);
    }

    public void decreaseKey(int i,Key key){
        validateIndex(i);
        if (!contains(i)) throw new IllegalArgumentException("there is no"+i+"in this pq");
        if (keys[i].compareTo(key) == 0) throw new IllegalArgumentException(key+ "is equal to the key in the priority queue");
        if (keys[i].compareTo(key)< 0) throw new IllegalArgumentException(key+"is greater than the key in the priority queue");
        keys[i] = key;
        swim(qp[i]);
    }

    public void increaseKey(int i,Key key){
        validateIndex(i);
        if (!contains(i)) throw new IllegalArgumentException("there is no"+i+"in this pq");
        if (keys[i].compareTo(key) == 0) throw new IllegalArgumentException(key+ "is equal to the key in the priority queue");
        if (keys[i].compareTo(key)> 0) throw new IllegalArgumentException(key+"is samller than the key in the priority queue");
        keys[i] = key;
        sink(qp[i]);
    }

    public void delete(int i){
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException();
        int index = qp[i];
        exch(index,n--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

/*
--------------------------------------------------------------
辅助函数
--------------------------------------------------------------
*/
    private void validateIndex(int k){
        if (k < 0) throw new IllegalArgumentException("the argu is negative ");
        if (k >= maxN) throw new IllegalArgumentException("the argu is out of bound");
    }

    private boolean greater(int i,int j){
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void exch(int i,int j){
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[j]] = i;
        qp[pq[i]] = j;
    }

    private void swim(int k){
        while(k > 1 && greater(k/2,k)){
            exch(k,k/2);
            k = k/2;
        }
    }

    private void sink(int k){
        while(2 * k < n){
            int j = 2 * k;
            if (j < n && greater(j,j+1)) j++;
            if (!greater(k,j)) break;
            exch(k,j);
            k = j;
        }
    }

    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }
    private class HeapIterator implements Iterator<Integer>{

        private IndexMinPQ<Key> copy;

        public HeapIterator(){
            copy = new IndexMinPQ<Key>(pq.length-1);
            for (int i = 1;i<=n;i++){
                copy.insert(pq[i],keys[pq[i]]);
            }
        }
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}