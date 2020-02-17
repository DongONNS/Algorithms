package edu.csu;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;

    private class Node<Item>{
        Item item;
        Node<Item> next;
    }

    /**
     * 初始化背包
     */
    public Bag(){
        first = null;
        n = 0;
    }

    /**
     * 判断背包是否为空
     * @return 如果返回true则说明背包为空，否则 非空
     */
    public boolean isEmpty(){
        return first == null;
    }

    /**
     * 获取背包的大小
     * @return 返回背包中元素的数量
     */
    public int size(){
        return n;
    }

    /**
     * 向背包集合中添加元素
     * @param item 待添加的元素
     */
    public void add(Item item){
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }


    /**
     * 遍历背包的元素的迭代器
     * @return
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    /**
     * 一个没有实现删除方法的迭代器
     * @param <Item>
     */
    private class ListIterator<Item> implements Iterator<Item>{
        private Node<Item> current;

        public ListIterator(Node<Item> first){
            current = first;
        }

        public boolean hasNext() {
            return current == null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("背包为空");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("不支持的操作类型");
        }
    }
}
