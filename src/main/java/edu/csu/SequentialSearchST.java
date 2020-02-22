package edu.csu;

import jdk.internal.dynalink.NoSuchDynamicMethodException;

public class SequentialSearchST<Key,Value> {
    private int n;
    private Node first;

    private class Node{
        private Key key;
        private Value value;
        private Node next;

        public Node(Key key,Value value,Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public SequentialSearchST(){}

    public int size(){
        return n;
    }

    public boolean isEmpty(){
        return n == 0;
    }

    public Value get(Key key){
        if (key == null) throw new IllegalArgumentException();
        for (Node x = first; x != null; x = x.next){
            if (key.equals(x.key))
                return x.value;
        }
        return null;
    }

    public boolean contains(Key key){
        if (key == null) throw new IllegalArgumentException();
        return get(key) !=null;
    }

    public void put(Key key,Value value){
        if (key == null) throw new IllegalArgumentException();
        if (value == null){
            delete(key);
            return;
        }
        for (Node x = first;x != null;x=x.next){
            if (key.equals(x.key))
                x.value = value;
        }

        first = new Node(key,value,first);
        n++;
    }

    public void delete(Key key){
        if (key == null) throw new IllegalArgumentException();
        delete(first,key);
    }

    public Node delete(Node node,Key key){
        if (node == null) throw new IllegalArgumentException();
        if (key.equals(node.key)){
            n--;
            return node.next;
        }
        node = delete(node.next,key);
        return node;
    }

    public Iterable<Key> keys(){
        Queue<Key> queue = new Queue<Key>();
        for (Node x = first;x!=null;x = x.next){
            queue.enqueue(x.key);
        }
        return queue;
    }
}
