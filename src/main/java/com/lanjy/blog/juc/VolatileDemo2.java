package com.lanjy.blog.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author：lanjy
 * @date：2020/3/26
 * @description：volatile不保证原子性的验证
 * 开启多个线程，同时对有volatile修饰的变量进行修改，无法保证结果一致性
 * 如何解决原子性
 * 1、加sync
 * 2、使用JUC下的原子类
 */
public class VolatileDemo2 {
    public static void main(String[] args) throws InterruptedException {
        PhoneRepertory phone = new PhoneRepertory();
        //模拟并发：for循环开启20个线程,每个线程对手机库存减1000
        //正常情况是20个线程执行完之后，手机库存为0,但由于volatile无法保证原子性，最终就可能是0，有可能不是0
        //原子性，即最终一致性
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    //不保证原子性
                    //phone.sell();
                    //使用AtomicInteger，保证原子性
                    phone.sellPhone();
                }
            }).start();
        }

        //等待20个项目都执行完毕之后，去查手机库存
        //Thread.activeCount() > 2，线程数大于2（程序启动时有两个线程，main线程和gc线程）
        while(Thread.activeCount() > 2){
            //Thread.yield();线程让步；业务代码使用这个函数需慎重。
            Thread.yield();
        }
        //或者直加个线程休眠10s
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName()+"\t 最终的库存是："+phone.atomicInteger);

    }
}

/**
 * 手机库存
 */
class PhoneRepertory{
    //volatile无法保证原子性
    volatile int number = 20000;
    public void sell(){
        number--;
    }

    //AtomicInteger 是一个 java.util.concurrent(简称JUC) 包提供的一个原子类，
    // 通过这个类可以对 Integer 进行一些原子操作。
    // 主要是依赖于 sun.misc.Unsafe 提供的一些 native 方法保证操作的原子性。
    AtomicInteger atomicInteger = new AtomicInteger(20000);
    public void sellPhone(){
        atomicInteger.getAndDecrement();
    }

}