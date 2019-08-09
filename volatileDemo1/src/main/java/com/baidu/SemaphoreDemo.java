package com.baidu;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);//模拟3个停车位，默认是使用的非公平锁
        for (int i = 0; i <6 ; i++) { //模拟6辆汽车
            new Thread(()->{
                try {
                    semaphore.acquire(); //代表占一个车位
                    System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                    //暂停三秒
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName()+"\t 停车3秒后离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release(); //走了一个车
                }
            },String.valueOf(i)).start();
        }
    }
}
