package edu.csu;

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>,Value> {
    private Node root;
    private class Node{
        private Key key;
        private Value val;
        private Node left;
        private Node right;
        private int size;
        public Node(Key key,Value val,int size){
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }
    public BST(){}

    public boolean isEmpty(){
        return size() == 0;
    }
    public int size(){
        return size(root);
    }
    private int size(Node x){
        if (x == null) return 0;
        else return x.size;
    }


    public boolean contains(Key key){
        if (key == null) throw new IllegalArgumentException();
        return getKey(key) != null;
    }
    public Value getKey(Key key ){
        return get(root,key);
    }
    private Value get(Node x,Key key){
        if (key == null) throw new IllegalArgumentException();
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)        return get(x.left,key );
        else if (cmp > 0)   return get(x.right,key);
        else                return x.val;
    }


    public void put(Key key,Value value){
        if (key == null) throw new IllegalArgumentException();
        if (key == null){
            delete(key);
            return ;
        }
        root = put(root,key,value);
        assert check();
    }
    private Node put(Node x,Key key,Value value){
        if (x == null) return new Node(key,value,1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left,key,value);
        else if (cmp > 0) x.right = put(x.right,key,value);
        else {
            x.val = value;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMin(){
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMin(root);
        assert check();
    }
    private Node deleteMin(Node x){
        if (x.left == null) return x.right;
        if (x.left != null) deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMax(){
        if(isEmpty()) throw new NoSuchElementException();
        deleteMax(root);
        assert check();
    }
    private Node deleteMax(Node x){
        if (x.right == null) return x.left;
        if (x.right != null) deleteMax(x.right);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void delete(Key key){
        if (key == null) throw new NoSuchElementException();
        root = delete(root,key);
        assert check();
    }
    private Node delete(Node x,Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp > 0) x.right = delete(x.right,key);
        else if (cmp < 0) x.left = delete(x.left,key);
        else{
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Key min(){
        if (isEmpty()) throw new NoSuchElementException();
        return min(root).key;
    }
    private Node min(Node x){
        if (x.left == null) return x;
        else    return   min(x.left);
    }
    public Key max(){
        if (isEmpty()) throw new NoSuchElementException();
        return max(root).key;
    }
    private Node max(Node x){
        if (x.right == null) return x;
        else      return max(x.right);
    }

    public Key floor(Key key){
        if (key == null) throw new IllegalArgumentException();
        if(isEmpty()) throw new IllegalArgumentException();
        Node x = floor(root, key);
        if (x == null) return null;
        else return x.key;
    }
    private Node floor(Node x,Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)  return floor(x.left,key);
        Node t = floor(x.right,key);
        if (t != null) return t;
        else return x;
    }

    public Key ceiling(Key key){
        if (key == null) throw new IllegalArgumentException();
        if (isEmpty()) throw new IllegalArgumentException();
        Node x = ceiling(root,key);
        if (x == null) return null;
        else return x.key;
    }
    private Node ceiling(Node x,Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0){
            Node t = ceiling(x.left,key);
            if (t != null)       return t;
            else return x;
        }
        return ceiling(x.right,key);
    }

    public Key select(int k){
        if (k < 0 || k > size()) throw new IllegalArgumentException();
        Node x = select(root,k);
        return x.key;
    }
    private Node select(Node x,int k){
        if (x == null) return null;
        int t = size(x.left);
        if (t > k)       return select(x.left,k);
        else if (t < k)  return select(x.right,k - t -1);
        else             return x;
    }

    public int rank(Key key){
        if (key == null) throw new IllegalArgumentException();
        return rank(key,root);
    }
    private int rank(Key key,Node x){
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0 ) return rank(key,x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key,x.right);
        else return size(x.left);
    }

    public Iterable<Key> keys(){
        if (isEmpty())  throw new NoSuchElementException();
        return keys(min(),max() );
    }
    private Iterable<Key> keys(Key lo,Key hi){
        if (lo == null || hi == null) throw new IllegalArgumentException();
        Queue<Key> queue = new Queue<Key>();
        keys(root,queue,lo,hi);
        return queue;
    }
    private void keys(Node x,Queue queue,Key lo,Key hi ) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmplo > 0) keys(x.right, queue, lo, hi);
    }

    public int size(Key lo,Key hi){
        if (lo==null && hi==null) throw new IllegalArgumentException();
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    public int height(){
        return height(root);
    }
    private int height(Node x){
        if (x == null) throw new IllegalArgumentException();
        return 1 + Math.max(height(x.left),height(x.right));
    }

    public Iterable<Key> levelOrder(){
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while(!queue.isEmpty()){
            Node x = queue.dequeue();
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    public boolean check(){
        if (!isBST()) StdOut.println("not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        return isBST() && isRankConsistent() && isSizeConsistent();
    }

    private boolean isBST(){
        return isBST(root,null,null);
    }
    private boolean isBST(Node x,Key min,Key max){
        if (x ==null) return true;
        if (min != null && min.compareTo(x.key) < 0) return false;
        if (max != null && max.compareTo(x.key) > 0) return false;
        return isBST(x.left,min,x.key) && isBST(x.right,x.key,max);
    }
    private boolean isSizeConsistent(){
        return isSizeConsistent(root);
    }
    private boolean isSizeConsistent(Node x){
        if (x == null) return true;
        if (size(x) != size(x.left) + size(x.right)) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }
    private boolean isRankConsistent(){
        for (int i = 0;i < size();i++){
            if (i != rank(select(i))) return false;
        }
        for (Key key : keys()){
            if (key.compareTo(select(rank(key)))!=0) return false;
        }
        return true;
    }

}
