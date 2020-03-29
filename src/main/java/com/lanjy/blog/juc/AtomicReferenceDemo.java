package com.lanjy.blog.juc;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author：lanjy
 * @date：2020/3/29
 * @description：原子引用类
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference();
        User lisi = new User("李四",22);
        User zhangsan = new User("张三",22);
        atomicReference.set(lisi);
        System.out.println(atomicReference.compareAndSet(lisi,zhangsan)+"\t" +atomicReference.get());
        System.out.println(atomicReference.compareAndSet(lisi,zhangsan)+"\t" +atomicReference.get());
    }
}

@Data
class User{
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
