package com.baidu.BlockingQueue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueDemo {
    public static void main(String[] args) {
        //SynchronousQueue他是一个非公平锁
        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t put 1");
                synchronousQueue.put("1");  //put阻塞
                System.out.println(Thread.currentThread().getName()+"\t put 2");
                synchronousQueue.put("2");
                System.out.println(Thread.currentThread().getName()+"\t put 3");
                synchronousQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA").start();

        new Thread(()->{
            try {
                Thread.sleep(5000);
                System.out.println(synchronousQueue.take());
                Thread.sleep(5000);
                System.out.println(synchronousQueue.take());
                Thread.sleep(5000);
                System.out.println(synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();
    }
}
