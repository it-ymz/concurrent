package com.baidu;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    /**
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        //countDownLatch计数
        CountDownLatch countDownLatch = new CountDownLatch(6); //count 个数 down向下 latch占有
        for (int i = 1; i <=6 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 国，被灭");
                countDownLatch.countDown();  //releaseShared底层是释放共享，默认是减一
            },CountryEnum.foreach_CountTryEnum(i).getRetMessage()).start();
        }

        //countDown()和await()方法结合使用   countDown是减1的意思，当countDown减到0的话，马上和await进行解除，然后往await下走
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t**********秦帝国，一统华夏");
    }
}