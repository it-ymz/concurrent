package com.baidu.collection;

//线程操作资源类，高内聚，低耦合
//分布式缓冲底层是map及其子接口

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * java.util.concurrentLocks这个lock锁的处理高并发并发包
 * 接口有lock,readWriteLock
 */

/**
 * 写操作是独占，原子+独占，整个过程必须是一个完整的统一体，中间不许被分割，被打断
 */
class MyCache{//资源类

    //往缓冲里面写东西一般都要加上volatile,保证可见性
    private volatile Map<String,Object> map = new HashMap<>();

    //创建juc底下的读写锁，为了实现读写分离，写的时候只能有一个线程，而读的时候可以有多个线程访问共享
    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    //private Lock lock = new ReentrantLock();
    //lock每次只能操作一个线程，现在操作的是写只有一个线程，而读的话，就需要多个线程共享

    public void put(String key,Object value){
        rwlock.writeLock().lock(); //写锁开启
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在写入"+key);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成"+key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwlock.writeLock().unlock();//写锁释放资源
        }

    }

    public void get(String key){
        rwlock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwlock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {

    //线程操作资源类
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        //五个线程写
        for (int i = 0; i <5 ; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }

        //五个线程读
        for (int i = 0; i <5 ; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }
    }
}
