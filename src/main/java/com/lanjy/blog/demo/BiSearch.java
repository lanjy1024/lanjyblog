package com.lanjy.blog.demo;

/**
 * @author：lanjy
 * @date：2020/6/19
 * @description：
 */
public class BiSearch {
    public static void main(String[] args) {

    }

    public static int biSearch(int []array,int a){
        int lo=0;
        int hi=array.length-1;
        int mid;
        while(lo<=hi){
            mid=(lo+hi)/2;//中间位置
            if(array[mid]==a){
                return mid+1;
            }else if(array[mid]<a){ //向右查找
                lo=mid+1;
            }else{ //向左查找
                hi=mid-1;
            }
        }
        return -1;
    }
}
