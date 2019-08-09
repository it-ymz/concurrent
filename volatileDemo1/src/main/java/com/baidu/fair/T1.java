package com.baidu.fair;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T1 {

    public static void main(String[] args) {
        /**
         * 在使用ReentrantLock锁的时候注意：
         * new ReentrantLock() 如果不传值默认是false不公平锁,
         * new ReentrantLock(true)如果传true的话，创建的是公平锁
         *
         * 公平锁和非公平锁的区别：
         * 公平锁：是先来后到，例子：打饭的时候，每一个人都有序的排序打饭，不插队
         * 非公平锁：加塞，例子：打饭的时候，插队打饭，来的迟的挤到别人前面
         */
        Phone phone = new Phone();
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t3 = new Thread(phone,"t3");
        Thread t4 = new Thread(phone,"t4");

        t3.start();
        t4.start();
    }
}

class Phone implements Runnable{
    public synchronized void sendSMS() throws  Exception{
        System.out.println(Thread.currentThread().getId()+"\t"+" invoked sendSMS");
        sendEmail();
    }

    public synchronized void sendEmail() throws  Exception{
        System.out.println(Thread.currentThread().getId()+"\t"+" ###########invoked sendEmail");
    }


    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock(); //在使用lock()锁的时候用，只要锁能够配对成功，添加几把锁都可以执行成功
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t"+"invoked get()");
            set();
        }finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t"+"invoked set()");
        }finally {
            lock.unlock();
        }
    }
}
