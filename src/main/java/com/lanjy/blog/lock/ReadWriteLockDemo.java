package com.lanjy.blog.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.lock
 * @类描述：读写锁 ReentrantReadWriteLock
 * 多个线程同时读取一个资源类不会有问题，为了满足并发量，读取共享资源可以同时进行。
 * 但是，如果有一个线程在写入资源，那么，就不应该允许其他线程对资源进行读或者写操作；
 * 此时，便需要一个读写锁来对线程做控制；
 *
 * 读——读——可以共存
 * 读——写——不可共存
 * 写——写——不可共存
 * @创建人：lanjy
 * @创建时间：2020/3/26
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //有5个线程对资源进行写操作
        for(int i = 1; i <= 5; i++){
            final String threadName = "写线程_"+i;
            final String key = i+"";
            final String value = "写线程_"+i+",写入的数据";
            new Thread(()->{
                myCache.put(key,value);
            },threadName).start();
        }

        for(int i = 1; i <= 5 ; i++){
            final String threadName = "读线程_"+i;
            final String key = ""+i;
            new Thread(()->{
                myCache.get(key);
            },threadName).start();
        }
    }


}

/**
 * 模拟一个缓存资源
 */
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    /**
     * 往缓存中加入数据
     */
    public void put(String key,Object object){
        rwLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key);
        //模拟网络延迟
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put(key,object);
        System.out.println(Thread.currentThread().getName()+"\t 写入完毕："+object);
        rwLock.writeLock().unlock();
    }

    /**
     * 从缓存中获取数据
     */
    public Object get(String key){
        rwLock.readLock().lock();
        System.out.println(Thread.currentThread().getName()+"\t 正在从缓存中获取数据,key："+key);
        //模拟网络延迟
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName()+"\t 从缓存中获取到数据："+o);
        rwLock.readLock().unlock();
        return o;
    }
}