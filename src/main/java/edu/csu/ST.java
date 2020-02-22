package edu.csu;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ST<Key extends Comparable<Key>,Value> implements Iterable<Key> {

    private TreeMap<Key,Value> st;

    public ST(){
        st = new TreeMap<Key, Value>();
    }

    public Value get(Key key){
        if (key == null) throw new IllegalArgumentException();
        return st.get(key);
    }

    public void put(Key key,Value value){
        if (key == null) throw new IllegalArgumentException();
        if (value==null) st.remove(key);
        else st.put(key,value);
    }

    public void delete(Key key){
        if (key == null) throw new IllegalArgumentException();
        st.remove(key);
    }

    public void remove(Key key){
        if (key == null) throw new IllegalArgumentException();
        st.remove(key);
    }

    public boolean contains(Key key){
        if (key == null) throw new IllegalArgumentException();
        return st.containsKey(key);
    }

    public int size(){
        return st.size();
    }

    public boolean isEmpty(){
        return st.size() == 0;
    }

    public Iterable<Key> keys(){
        return st.keySet();
    }

    public Iterator<Key> iterator() {
        return st.keySet().iterator();
    }

    public Key min(){
        if (isEmpty()) throw new IllegalArgumentException();
        return st.firstKey();
    }

    public Key max(){
        if (isEmpty()) throw new IllegalArgumentException();
        return st.lastKey();
    }
    public Key ceiling(Key key){
        if (key == null) throw new IllegalArgumentException();
        Key k = st.ceilingKey(key);
        if (k == null) throw new NoSuchElementException();
        return k;
    }

    public Key floor(Key key){
        if (key == null) throw new IllegalArgumentException();
        Key k = st.floorKey(key);
        if (k == null) throw new NoSuchElementException();
        return k;
    }
}
