package com.baidu.JUC;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 使用CAS会产生ABA的问题如何解决？
 * 使用原子引用+版本号
 * JUC提供了AtomicStampedReference类，
 */
public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        System.out.println("=====================ABA问题========================");
        new Thread(()->{
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();
        new Thread(()->{
            //暂停1秒钟t2线程，保证上面的t1线程完成了一次ABA操作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100,2019)+"\t"+atomicReference.get());
        },"t2").start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=================ABA解决问题=========================");
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+"第一次版本号："+stamp);
            //暂停一秒t3
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()+"\t"+"第二次版本号："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()+"\t"+"第三次版本号："+atomicStampedReference.getStamp());
        },"t3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+"第一次版本号："+stamp);
            //暂停三秒t4线程，等待t3线程完成一次ABA操作
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"\t"+"修改成功否："+b+"\t当前最新实际版本号："+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t"+"当前实际最新值"+atomicStampedReference.getReference());
            },"t4").start();
    }
}
