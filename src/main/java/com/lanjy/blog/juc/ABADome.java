package com.lanjy.blog.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author：lanjy
 * @date：2020/3/29
 * @description：由CAS引发的ABA问题以及ABA问题的解决
 */
public class ABADome {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    //new AtomicStampedReference的时候设置初始值并设定版本号，每次修改的时候版本号加1，从而规避ABA问题；有点类似乐观锁
    static AtomicStampedReference atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
        System.out.println("=================ABA问题产生=====================");
        new Thread(()->{
            boolean compareAndSet = atomicReference.compareAndSet(100, 101);
            System.out.println(Thread.currentThread().getName()+"\t"+compareAndSet+"\t"+atomicReference.get());
            compareAndSet = atomicReference.compareAndSet(101,100);
            System.out.println(Thread.currentThread().getName()+"\t"+compareAndSet+"\t"+atomicReference.get());
            System.out.println(Thread.currentThread().getName()+"\t"+"完成了一次ABA操作");
        },"t1").start();

        new Thread(()->{
            //线程2暂停1秒钟，确保线程1已经完成了一次ABA操作
            try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
            boolean compareAndSet = atomicReference.compareAndSet(100, 101);
            System.out.println(Thread.currentThread().getName()+"\t"+compareAndSet+"\t"+atomicReference.get());
        },"t2").start();

        try {TimeUnit.SECONDS.sleep(3);} catch(InterruptedException e) {e.printStackTrace();}

        System.out.println("=================ABA问题解决=====================");
        //两个线程同时去修改一个变量，并且同时拿到版本号为1，线程3执行比较快，执行了两次更新了100->101->100，且版本号已经更新为3了，
        // 线程4才开始进行更新，但是版本号已经变成3了，与线程拿到的版本号为1，不一致，所以线程4进行的更新操作失败；
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t第1次获取到的版本号："+atomicStampedReference.getStamp());
            try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
            boolean b = atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()+"\t第2次获取到的版本号："
                    +atomicStampedReference.getStamp()
                    +"\t"+b+"\t"
                    +atomicStampedReference.getReference());
            b = atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()+"\t第3次获取到的版本号："
                    +atomicStampedReference.getStamp()
                    +"\t"+b+"\t"
                    +atomicStampedReference.getReference());


        },"t3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();

            System.out.println(Thread.currentThread().getName()+"\t第1次获取到的版本号："+stamp);
            //线程4暂停3秒钟，确保线程3已经完成了一次ABA操作
            try {TimeUnit.SECONDS.sleep(3);} catch(InterruptedException e) {e.printStackTrace();}
            boolean b = atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"\t第2次获取到的版本号："
                    +atomicStampedReference.getStamp()
                    +"\t"+b+"\t"
                    +atomicStampedReference.getReference());
        },"t4").start();
    }
}
