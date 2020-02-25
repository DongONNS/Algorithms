package edu.csu;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.regexp.internal.RE;

import java.util.NoSuchElementException;

public class RedBlackBST<Key extends Comparable<Key>,Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;



    private class Node{
        private Key key;
        private Value val;
        private Node left,right;
        private boolean color;
        private int size;

        public Node(Key key,Value val,boolean color,int size){
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }


    }

    public RedBlackBST(){}

    private boolean isRed(Node x){
        if (x == null) throw new IllegalArgumentException();
        return x.color == RED;
    }
    private int size(Node x){
        if (x == null) throw new IllegalArgumentException();
        return x.size;
    }
    public int size(){
        return size(root);
    }
    public boolean isEmpty(){
        return root == null;
    }
    public Value get(Key key){
        if (key == null) throw new IllegalArgumentException();
        return get(root,key);
    }
    private Value get(Node x,Key key){
        while(x != null){
            int cmp =key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }
    public boolean contains(Key key){
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    public void put(Key key,Value value){
        if (key == null) throw new IllegalArgumentException();
        if (value == null){
            delete(key);
            return ;
        }
        root = put(root,key,value);
        root.color = BLACK;
    }
    private Node put(Node h,Key key,Value val){
        if (h == null) return new Node(key,val,RED,1);
        int cmp = key.compareTo(h.key);
        if (cmp < 0)    h.left = put(h.left,key,val);
        else if (cmp > 0)    h.right = put(h.right,key,val);
        else    h.val = val;

        if (isRed(h.right) && !isRed(h.left))       h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))    h =rotateRight(h);
        if (isRed(h.left) && isRed(h.right))        flipColors(h);
        h.size =size(h.left) + size(h.right);

        return h;
    }

    public Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        h.size =size(h.left) + size(h.right) + 1;
        return x;
    }
    public Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }
    public void flipColors(Node h){
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    public void deleteMin(){
        if (isEmpty()) throw new NoSuchElementException();
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node h){
        if (h.left == null) return null;

        if (!isRed(h.left) && !isRed(h.left.left)){
            h = moveRedLeft(h);
        }

        h.left = deleteMin(h.left);
        return balance(h);
    }

    public void deleteMax(){
        if (isEmpty()) throw new NoSuchElementException();
        if (!isRed(root.left) && !isRed(root.right)){
            root.color = RED;
        }
        root = deleteMax(root);
        if (!isEmpty())
            root.color = BLACK;
    }
    private Node deleteMax(Node h){
        if (isRed(h.left)){
            h = rotateRight(h);
        }
        if (h.right == null) return null;
        if (!isRed(h.right) && !isRed(h.right.left)){
            h = moveRedRight(h);
        }
        h.right = deleteMax(h.right);
        return balance(h);
    }

    public void delete(Key key){
        if (isEmpty())      throw new NoSuchElementException();
        if (key == null)    throw new IllegalArgumentException();
        if (isRed(root.left) && isRed(root.right)){
            root.color = RED;
        }
        root = delete(root,key);
        if (!isEmpty()) root.color = BLACK;
    }
    private Node delete(Node h,Key key){
        if (key.compareTo(h.key) < 0){
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left,key);
        }else {
            if (isRed(h.left))
                rotateRight(h);
            if (key.compareTo(h.key) == 0 && h.right == null)
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin(h.right);
            } else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    private Node moveRedLeft(Node h){
        flipColors(h);
        if (isRed(h.right.left)){
            h.right = rotateRight(h.right);
            h = rotateLeft(h.left);
            flipColors(h);
        }
        return h;
    }
    private Node moveRedRight(Node h){
        flipColors(h);
        if (isRed(h.left.left)){
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }
    private Node balance(Node h){
        if (isRed(h.right))                             h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))        h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))            flipColors(h);

        h.size = 1 + size(h.left) + size(h.right);
        return h;
    }

    public int height(){
        return height(root);
    }
    private int height(Node x){
        if (x == null) return -1;
        return  1 + Math.max(height(x.left),height(x.right));
    }

    public Key min(){
        if (isEmpty())  throw new NoSuchElementException();
        return  min(root).key;
    }
    private Node min(Node h){
        if (h.left == null)     return h;
        else                    return min(h.left);
    }

    public Key max(){
        if (isEmpty())  throw new NoSuchElementException();
        return  max(root).key;
    }
    private Node max(Node h){
        if (h.right == null)    return h;
        else                    return max(h.right);
    }

    public Key floor(Key key){
        if (key == null)    throw new IllegalArgumentException();
        if (isEmpty())      throw new NoSuchElementException();
        Node x = floor(root,key);
        if (x == null)  return null;
        else            return x.key;
    }
    private Node floor(Node x,Key key){
        if (x == null)  return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)   return x;
        if (cmp < 0)    return floor(x.left,key);
        Node t = floor(x.right,key);
        if (t != null)  return t;
        else            return x;
    }

    public Key ceiling(Key key){
        if (key == null)        throw new IllegalArgumentException();
        if (isEmpty())          throw new NoSuchElementException();
        Node x = ceiling(root,key);
        if (x == null)  return null;
        else            return x.key;
    }
    private Node ceiling(Node x, Key key){
        if (x == null)  return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)   return x;
        if (cmp > 0)    return ceiling(x.right,key);
        Node t = ceiling(x.left,key);
        if (t != null)  return t;
        else            return x;
    }

    public Key select(int k){
        if (k < 0 || k > size()) throw new IllegalArgumentException();
        Node x = select(root,k);
        return x.key;
    }
    private Node select(Node x,int k){
        int t = size(x.left );
        if (t > k)      return select(x.left,k);
        if (t < k)      return select(x.right,k - t - 1);
        else            return x;
    }

    public int rank(Key key){
        if (key == null)    throw new IllegalArgumentException();
        return rank(root,key);
    }
    private int rank(Node x,Key key){
        if (x == null)  return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)            return rank(x.left,key);
        else if (cmp > 0)       return 1 + size(x.left) + rank(x.right,key);
        else                    return size(x.left);
    }

    public Iterable<Key> keys(){
        if (isEmpty())  throw new NoSuchElementException();
        return keys(min(),max());
    }
    public Iterable<Key> keys(Key lo,Key hi){
        if (hi == null || lo == null) throw new IllegalArgumentException();
        Queue<Key> queue = new Queue<Key>();
        keys(root,queue,lo,hi);
        return queue;
    }
    private void keys(Node x,Queue<Key> queue,Key lo,Key hi){
        if (x == null) return;
        int cmplo =lo.compareTo(x.key);
        int cmphi =hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left,queue,lo,hi);
        if (cmplo <= 0  && cmphi >= 0)  queue.enqueue(x.key);
        if (cmphi > 0)  keys(x.right,queue,lo,hi);
    }

    public int size(Key lo,Key hi){
        if (lo == null || hi == null)
            throw new IllegalArgumentException();
        if (lo.compareTo(hi) > 0)   return 0;
        if (contains(hi))   return rank(hi) - rank(lo) +1;
        else                return rank(hi) - rank(lo);
    }

    /*
    ------------------------------------------------------
    用于检验
    ------------------------------------------------------
     */
    private boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    private boolean is23() { return is23(root); }
    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (x != root && isRed(x) && isRed(x.left))
            return false;
        return is23(x.left) && is23(x.right);
    }

    private boolean isBalanced() {
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }
}
