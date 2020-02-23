package edu.csu;

import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;

import java.util.NoSuchElementException;

public class BinarySearchST<Key extends Comparable<Key>,Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] values;
    private int n;

    public BinarySearchST(){
        this(INIT_CAPACITY);
    }
    public BinarySearchST(int capacity){
        keys = (Key[])new Comparable[capacity];
        values = (Value[])new Comparable[capacity];
    }

    public void resize(int capacity){
        assert capacity >= n;
        Key[] tempk = (Key[])new Comparable[capacity];
        Value[] tempv = (Value[])new Comparable[capacity];
        for (int i = 0;i<keys.length;i++){
            tempk[i] = keys[i];
            tempv[i] = values[i];
        }
        keys = tempk;
        values = tempv;
    }

    public int size(){
        return n;
    }
    public boolean isEmpty(){
        return size() == 0;
    }

    public Value get(Key key){
        if (key == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        int i = rank(key);
        if(i<n && keys[i].equals(key)) return values[i];
        return null;
    }
    public boolean contains(Key key){
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    public int rank(Key key){
        if (key == null) throw new IllegalArgumentException();
        int lo = 0,hi = keys.length-1;
        int medium = lo + (hi-lo)/2;
        while(lo <= hi){
            if (key.compareTo(keys[medium]) < 0) hi = medium-1;
            else if (key.compareTo(keys[medium]) > 0) lo = medium+1;
            else return medium;
        }
        return lo;
    }


    public void put(Key key,Value value){
        if (key == null) throw new IllegalArgumentException();
        if (value == null) {
            delete(key);
            return;
        }

        int i = rank(key);

        if (i<n && keys[i].compareTo(key)==0){
            values[i] = value;
            return;
        }

        //否则需要插入新的元素
        if (n==keys.length) resize(2 * keys.length);

        for (int j = n;j > i;j--){
            keys[j+1] = keys[j];
            values[j + 1] = values[j];
        }
        keys[i] = key;
        values[i] = value;
        n++;
        check();
    }

    public void delete(Key key){
        if (key == null) throw new IllegalArgumentException();
        int i = rank(key);
        if (i == n || keys[i].compareTo(key) != 0) return;
        for (int j = i;j < n-1;j++){
            keys[j] = keys[j+1];
            values[j] = values[j+1];
        }
        n--;
        keys[n] = null;
        values[n] = null;

        if (n > 0 && n == keys.length/4) resize(keys.length/2);
        assert check();
    }

    public Key min(){
        if (isEmpty()) throw new NoSuchElementException();
        return keys[0];
    }
    public Key max(){
        if (isEmpty()) throw new NoSuchElementException();
        return keys[n-1];
    }
    public void deleteMin(){
        if (isEmpty()) throw new NoSuchElementException();
        delete(min());
    }
    public void deleteMax(){
        if (isEmpty()) throw new NoSuchElementException();
        delete(max());
    }

    public Key select(int k){
        if (k < 0 || k >keys.length){
            throw new IllegalArgumentException();
        }
        return keys[k];
    }

    public Key floor(Key key){
        if (key == null) throw new IllegalArgumentException();
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) return keys[i];
        if ( i == 0) return null;
        return keys[i-1];
    }
    public Key ceiling(Key key){
        if (key == null) throw new IllegalArgumentException();
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) return keys[i];
        if (i == n) return null;
        return keys[i+1];
    }

    public int size(Key lo,Key hi){
        if (lo == null) throw new IllegalArgumentException();
        if (hi == null) throw new IllegalArgumentException();
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    public Iterable<Key> keys(){
        return keys(min(),max());
    }
    public Iterable<Key> keys(Key lo,Key hi){
        if (lo == null) throw new IllegalArgumentException();
        if (hi == null) throw new IllegalArgumentException();

        Queue<Key> queue = new Queue<Key>();
        if (lo.compareTo(hi) > 0) return queue;
        for (int i = rank(lo);i < rank(hi);i++){
            queue.enqueue(keys[i]);
        }
        if (contains(hi)) queue.enqueue(keys[rank(hi)]);
        return queue;
    }

    private boolean check(){
        return isSorted() && rankCheck();
    }
    private boolean isSorted(){
        for (int i = 1;i < size();i++){
            if (keys[i].compareTo(keys[i-1]) < 0) return false;
        }
        return true;
    }
    private boolean rankCheck(){
        for (int i = 0;i < size();i++){
            if (i != rank(select(i))) return false;
        }
        for (int i = 0;i < size();i++){
            if (keys[i].compareTo(select(rank(keys[i])))!= 0) return false;
        }
        return true;
    }


}
