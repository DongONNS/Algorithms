package edu.csu;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first;   //栈顶
    private int n;              //栈中元素的数量

    private static class Node<Item>{
        private Item item;
        private Node<Item> next;
    }

    /**
     * 初始化栈
     * @return
     */
    public Stack(){
        first = null;
        n = 0;
    }

    /**
     * 判断栈是否为空
     * @return 若返回true 说明栈元素为空，否则非空
     */
    public boolean isEmpty(){
        return first == null;
    }

    /**
     * 获取集合的大小
     * @return  返回集合元素的大小
     */
    public int size(){
        return n;
    }

    /**
     * 向栈中添加元素
     * @param item  待添加的元素
     */
    public void push(Item item){
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        n ++;
   }

    /**
     * 返回并删除栈顶元素
     * @return 返回栈顶元素
     */
    public Item pop(){
        if (isEmpty()) throw new NoSuchElementException("栈为空");
        Item item = first.item;
        first = first.next;
        n--;
        return item;
   }

    /**
     * 返回但是不删除栈顶元素的值
     * @return 返回栈顶元素的值
     */
    public Item peek(){
        if (isEmpty()) throw new NoSuchElementException("栈为空");
        return first.item;
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


    private class ListIterator implements Iterator<Item>{

        private Node<Item> current;
        public ListIterator(Node<Item> first){
            current = first;
        }

        public boolean hasNext() {
            return current == null;
        }


        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("栈为空");
            Item item =current.item;
            current = current.next;
            return item;
         }

        public void remove() {
            throw new UnsupportedOperationException("不支持的操作");
        }
    }
}
