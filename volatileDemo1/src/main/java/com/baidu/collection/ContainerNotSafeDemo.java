package com.baidu.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ContainerNotSafeDemo {

    //hashMap
    public static void main(String[] args) {
        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }


    /**
     * HashSet
     */
    public static void setNotSafe(){
        Set<String> set = new CopyOnWriteArraySet<>();
        new HashSet<>();

        for (int i = 0; i <30 ; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

    /**
     * 在单线程的环境下，arryalist就不会出错
     */
    public static void listNotsafe(){
        List<String> list = new CopyOnWriteArrayList<>();
        //Collection;  //他是list接口的父接口，也就是基接口
        //Collections; //他是一个类，他是集合接口的辅助类

        /**
         * 为什么说ArrayList的add()线程不安全？
         * 在ArrayList类的底层Add()方法没有添加sync关键字
         * add()方法为了保证并发性没有添加sync
         *
         *
         */
        for (int i = 1; i <=30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }

        //Exception in thread "3" java.util.ConcurrentModificationException
        //出现了java.util.ConcurrentModificationException的这个异常，就是ArrayList线程不安全在高并发的访问下出现的异常

        //稍微总结一下：经常出现的异常
        //在金融,电商这些项目中会经常出现java.util.ConcurrentModificationException这个异常  Modification修改

        /**
         * ArrayList 是在jdk1.2以后出来的，Vector是在jdk1.0以后出来的
         *
         * 1.故障现象
         *        java.util.ConcurrentModificationException
         *
         * 2.导致原因
         *
         *
         * 3.解决方案
         *   3.1:可以使用new Vector()这个类来保证线程安全，它的add方法实现了sync
         *
         * 4.优化建议(同样的错误不犯第2次错)
         */
    }
}
