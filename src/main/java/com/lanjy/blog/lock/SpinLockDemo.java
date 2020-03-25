package com.lanjy.blog.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.lock
 * @类描述：自旋锁的demo：是药三分毒，知其弊明其利而用之
 * 自旋锁，是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁
 * 这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU
 * @创建人：lanjy
 * @创建时间：2020/3/25
 */
public class SpinLockDemo {
    //new一个原子引用线程,初始值为null
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /** 加锁  */
    public void mylock(){
        //获取当前线程
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"\t come in.........");

        //所谓自旋锁，就是线程不阻塞，一直尝试去获取锁
        //while()结果为true就一直循环，直到锁为释放，才能跳出当前循环
        while (!atomicReference.compareAndSet(null,thread)){
        }
        System.out.println(thread.getName()+"\t had mylock.........");
    }

    /** 释放锁  */
    public void myUnLock(){
        //获取当前线程
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+"\t invoked myUnLock.........");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        //线程AA进入卫生间，拿到锁并蹲5秒钟
        new Thread(() -> {
            spinLockDemo.mylock();

            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}

            spinLockDemo.myUnLock();
        },"thread_AA").start();

        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        //线程AA进入卫生间获取到锁1秒钟之后，线程BB来了，但是还要等4秒钟之后线程AA用完卫生间释放锁，线程BB才能拿到锁
        //问题来了，如果线程AA一直站着茅坑刷起了抖音，自己一个人爽，那么很多内急的线程就要一直在外面循环等待着锁（就是一堆线程都在死循环，影响系统性能）
        new Thread(() -> {
            spinLockDemo.mylock();
            spinLockDemo.myUnLock();
        },"thread_BB").start();

        //先让线程AA获取到锁，接着休眠5秒，
        //线程AA获取到锁的1秒钟之后，线程BB去尝试获取锁，获取不到就一直尝试，直到5秒钟之后线程AA释放锁，线程BB才获取到锁
        /*
        thread_AA	 come in.........
        thread_AA	 had mylock.........
        thread_BB	 come in.........
        thread_AA	 invoked myUnLock.........
        thread_BB	 had mylock.........
        thread_BB	 invoked myUnLock.........

        Process finished with exit code 0
        */
    }

}
