package com.baidu.fair;

import java.util.concurrent.atomic.AtomicReference;

public class SprinLockDemo {

    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    //自旋加锁  不需要使用sync，不需要使用lock  它采用的是使用循环的方式，判断原子引用中当前线程是否为null，当为空，那么就可以往主内存中写入当前线程
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in");
        while (!atomicReference.compareAndSet(null,thread)){

        }
    }

    //MyUnLock()是为了放锁，通过atomicReference中的compareAndSet判断期望值是否有了，有了那么就设为空，等待下一个人使用
    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t"+"invoked myUnLock()");
    }
    public static void main(String[] args) {
        SprinLockDemo sprinLockDemo = new SprinLockDemo();
        new Thread(()->{
            sprinLockDemo.myLock();
            try {
                Thread.sleep(5000);
                System.out.println("11111111");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sprinLockDemo.myUnLock();
        },"AA").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            sprinLockDemo.myLock();
            sprinLockDemo.myUnLock();
        },"BB").start();
    }
}
