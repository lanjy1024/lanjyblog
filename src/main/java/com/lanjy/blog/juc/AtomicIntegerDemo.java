package com.lanjy.blog.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author：lanjy
 * @date：2020/3/28
 * @description：
 */
public class AtomicIntegerDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger);
    }
}
