package edu.csu;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first;          //队列的头指针
    private Node<Item> last;           //队列的尾指针
    private int n;                     //队列中元素的数量

    private static class Node<Item> {
        Item item;                  //节点中的元素
        Node<Item> next;            //下一个元素
    }

    /**
     * 初始化队列
     */
    public Queue(){
        first = null;
        last = null;
        n = 0;
    }

    /**
     * 判断集合是否为空
     * @return 如果返回true 说明集合元素为空，否则说明集合非空
     */
    public boolean isEmpty(){
        return first == null;
    }

    /**
     * 获取队列的大小
     * @return  返回队列中元素的数量
     */
    public int size(){
        return n;
    }


    /**
     * 返回但不删除队列中的第一个元素
     * @return  返回但不删除集合中的第一个元素
     */
    public Item  peek(){
        if (isEmpty())  throw new NoSuchElementException("栈为空");
        return first.item;
    }

    /**
     * 向队列中添加一个元素
     * @param item 待添加的元素
     */
    public void enqueue(Item item){
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }

    /**
     * 删除队列中的元素
     * @return 按照先进先出的顺序删除并返回队列中最先进入的元素;
     */
    public Item dequeue(){
        if (isEmpty()) throw new NoSuchElementException("栈为空");
        Item  item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;
        return item;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Item item : this){
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    private class ListIterator<Item> implements Iterator<Item>{
        private Node<Item> current;

        public ListIterator(Node<Item> first){
            current = first;
        }

        public boolean hasNext() {
            return first == null;
        }

        public Item next() {
            if (!hasNext()) throw  new NoSuchElementException("栈为空");
            Item  item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("不支持该操作");
        }
    }


}
