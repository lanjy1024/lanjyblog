package com.lanjy.blog.juc;


import java.util.concurrent.TimeUnit;

/**
 * @类描述：验证volatile的可见性
 * volatile关键字是java虚拟机提供的轻量级的同步机制；
 * 有以下3个特点：
 * 1、保证可见性；
 * 2、不保证原子性；(操作结果一致性)
 * 3、禁止指令重排；
 * @创建人：lanjy
 * @创建时间：2020/3/26
 */
public class VolatileDemo1 {
    public static void main(String[] args) {
        MyData myData = new MyData();
        //开启一个线程去修改MyData中number的值
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t come in .....");
            //让线程休眠3秒，模拟计算机底层运算的时间消耗
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.setNumber(55);
            System.out.println(Thread.currentThread().getName() + "\t updated number ok :" + myData.getNumber());
        },"线程_AA").start();

        //主线程读取MyData中number的值,如果线程_AA修改了MyData中number的值，而主线程的number的值还一直是初始值，就会在这里死循环
        while(myData.getNumber() == 0){

        }
        System.out.println(Thread.currentThread().getName() + "\t myData.getNumber() :" + myData.getNumber());
    }

}

/**
 * 验证volatile关键字的可见性
 * 假设有两个线程同时获取number的初始值，一个线程修改了number的值，另一个线程中的number的值会不会也被修改;
 * 如果number没有volatile修饰，则线程间无法通信，线程1修改了值，另一个线程中的值不会被同步。
 * 这里涉及到线程通信的原理，JMM内存模型的原理
 *
 * volatile关键字可以实现线程间的通信，这便是所谓的可见性
 *
 * 创建线程时，JVM会为每个线程分配一个独立的内存空间，JMM规定所有变量的都存储在主内存中，主内存是共享内存区，所有线程都可以访问。
 * 线程对变量的操作步骤：
 * 1、从主内存中拷贝一份变量的副本放到自己的工作内存中
 * 2、操作自己工作内存中的变量之后再写会主内存中
 *
 *
 */
class MyData{

    private volatile int number = 0;
//    private int number = 0;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}