package edu.csu;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinPQ<Key> implements Iterable<Key> {

    private Key[] pq;   //存储1-n之间的元素
    private int n;      //优先队列中元素的数量
    private Comparator<Key> comparator;  //可选择的比较器

    public MinPQ(int initcapacity) {
        pq =(Key[]) new Object[initcapacity + 1];
        n = 0;
    }

    public MinPQ(){
        this(1);
    }

    public MinPQ(int initcapacity,Comparator<Key> comparator){
        this.comparator = comparator;
        pq = (Key[])new Object[initcapacity+1];
        n = 0;
    }

    public MinPQ(Comparator comparator){
        this(1,comparator);
    }


    public MinPQ(Key[] keys){
        n = keys.length;
        pq = (Key[]) new Object[keys.length+1];
        for (int i = 0 ;i <= keys.length;i++){
            pq[i+1] = keys[i];
        }
        for (int i = n/2;i>0;i--){
            sink(i);
        }
        assert isMinHeap();
    }

    public boolean isEmpty(){
        return n == 0;
    }

    public int size(){
        return n;
    }

    public Key min(){
        if (isEmpty()) throw new NoSuchElementException("priority queeu underflow");
        return pq[1];
    }

    private void resize(int capacity){
        assert capacity > n;
        Key[] temp = (Key[])new java.lang.Object[capacity+1];
        for (int i = 1 ;i <= n;i++){
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public void insert(Key x){
        if (n == pq.length-1) resize(2 * pq.length);
        pq[++n] = x;
        swim(n);
        assert isMinHeap();
    }

    public Key delMin(){
        if (isEmpty()) throw new NoSuchElementException("priority is empty");
        Key min = pq[1];
        exch(1,n--);
        sink(1);
        pq[n+1] = null;
        if ((n > 0) && (n == pq.length / 4)) resize(pq.length/2);
        assert isMinHeap();
        return min;

    }

    private void swim(int i){
        while(i > 1 && greater(i/2,i)){
            exch(i,i/2);
            i = i/2;
        }
    }

    private void sink(int i){
        while (2 * i < n) {
            int j = 2 * i;
            if (i < n && greater(j,j+1)) j++;
            if (!greater(i,j)) break;
            exch(i,j);
            i = j;
        }
    }

    private boolean greater(int i,int j){
        if (comparator == null){
            return ((Comparable<Key>)pq[i]).compareTo(pq[j]) > 0;
        }
        return comparator.compare(pq[i],pq[j]) > 0;
    }

    private void exch(int i,int j){
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }


    private boolean isMinHeap(){
        for (int i = 1;i <= n;i++){
            if (pq[i] == null) return false;
        }
        for (int i = n+1;i<=pq.length;i++){
            if (pq[i] != null) return false;
        }
        if (pq[0] != null) return false;
        return isMinHeapOrdered(1);
    };

    private boolean isMinHeapOrdered(int k){
        if (k > n ) return false;
        int left  = 2 * k;
        int right = 2 * k + 1;
        if (left <= n &&  greater(k,left)) return false;
        if (right <= n && greater(k,right)) return false;
        return isMinHeapOrdered(left) && isMinHeapOrdered(right);
    }

    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator<Key> implements Iterator<Key>{

        private MinPQ<Key> copy;

        public HeapIterator(){
            if (comparator == null){
                copy = new MinPQ<Key>(size());
            } else {
                copy = new MinPQ<Key>(size(), (Comparator<Key>) comparator);
            }
            for (int i = 1 ;i <= n;i++){
                copy.insert((Key) pq[i]);
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException("priprity is empty");
            return copy.delMin();
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported operation");
        }
    }
}
