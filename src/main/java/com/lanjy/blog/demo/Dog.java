package com.lanjy.blog.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author：lanjy
 * @date：2020/7/22
 * @description：
 */
@Data
@AllArgsConstructor
public class Dog {
    private String name;
    private int age;


    public static void main(String[] args) {
        List<Dog> daoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            daoList.add(new Dog("旺财"+i,1+i));
        }

    }
}
