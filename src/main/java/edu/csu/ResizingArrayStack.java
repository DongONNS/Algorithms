package edu.csu;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] a;         // 存储元素的数组
    private int n;            // 栈中元素的数量


    /**
     * 初始化一个空的栈
     */
    public ResizingArrayStack() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * 判断栈是否为空
     * @return 返回true表示栈为空，否则非空
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * 返回集合中元素的数量
     * @return 栈中元素的数量
     */
    public int size() {
        return n;
    }

    // 调整保存数据的数组大小
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }



    /**
     * 向栈中添加元素
     * @param item 待添加的元素
     */
    public void push(Item item) {
        if (n == a.length) resize(2*a.length);    // 将数组扩容成原来的两倍
        a[n++] = item;                            // 添加元素
    }

    /**
     * 删除并返回栈中的第一个元素
     * @return 返回最新添加的一个元素
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[n-1];
        a[n-1] = null;
        n--;
        // 收缩数组的大小
        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }


    /**
     * 返回最新添加的元素
     * @return 返回最新添加进战中的元素
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[n-1];
    }

    /**
     * 返回一个迭代器
     * @return 将栈中的元素按后进先出进行输出的迭代器
     */
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    // 迭代器的嵌套类
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;

        public ReverseArrayIterator() {
            i = n-1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }





}
