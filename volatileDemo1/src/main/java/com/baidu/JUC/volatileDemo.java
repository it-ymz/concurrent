package com.baidu.JUC;

import java.util.concurrent.atomic.AtomicInteger;

public class volatileDemo {  //new volatileDemo那么就是将该对象放入了主内存中

    volatile int number = 0;  //这个变量就是主内存中的一个变量

    public void addT060(){   //当一个线程调用这个方法的话，那么就会把这个变量修改为60 ，
        this.number = 60;
    }

    AtomicInteger atomicInteger = new AtomicInteger();   //带有原子性的包装类  参数默认为0

    public void addatomic(){
        atomicInteger.getAndIncrement();  //i++  获取值的时候+1
    }
    //此时number前面是加了volatile关键字，不保证原子性
    public void addPlusPlus(){
        number++;
    }

    public static void main(String[] args) {  //main是一切方法的运行入口
        volatileDemo volatileDemo = new volatileDemo();
        for (int i= 1; i<=20; i++){
            new Thread(()->{
                for (int j = 1; j <= 1000; j++) {
                    volatileDemo.addPlusPlus();  //不保证原子性
                    volatileDemo.addatomic(); //保证原子性
                }
            },String.valueOf(i)).start();
        }

        //需要等待上面20个线程都计算完成后，再用线程取得最终的结果值看是多少？

        //为什么说thread.activeCount()>2呢，因为后台默认会有一个主线程和一个gc线程 所以是大于2
        while (Thread.activeCount()>2){
            Thread.yield();  //如果当前的线程数量大于2的时候，那么证明还没有线程执行完毕，那么就需要线程让步 yeild()
        }
        System.out.println(Thread.currentThread().getName()+"\t int finally number value:"+volatileDemo.number);

        System.out.println(Thread.currentThread().getName()+"\t atomicInteger type  finally number value:"+volatileDemo.atomicInteger);
    }

    //volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改。
    public static void seeOkByVolatile(){
        volatileDemo volatileDemo = new volatileDemo(); //资源类
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            //暂停一会线程
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            volatileDemo.addT060();

            System.out.println(Thread.currentThread().getName()+"\t update number value:"+volatileDemo.number);
        },"AAA").start();


        //第二个线程
        while (volatileDemo.number ==0){
            //main线程就一直在这里等待循环，直到number值不在等于零
        }
        System.out.println(Thread.currentThread().getName()+"\t mission is over,main get number value :"+volatileDemo.number);
    }

    /**
     * 验证volatile的可见性
     * 1.1 假如int number = 0,number变量之前根本没有添加volatile关键字修饰   没有可见性
     * 1.2 添加了volatile，可以解决可见性问题
     *
     * 2.验证volatile不保证原子性
     *   2.1 原子性指的是什么意思？
     *       不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，需要整体
     *       要么同时成功，要么同时失败。
     *       例如：张三正在拿笔签名，而李四现在此刻也来签名，李四就抢了张三的笔进行签名，然后在还给张三，那么这种就是 不保证原子性
     *
     *   2.2 volatile不保证原子性
     *
     *   2.3
     *
     *   2.4如何保证原子性？
     *     使用sync
     *     使用 juc下的atomic java.util.concurrent.atomic原子性  下的AtomicInteger 带有包装类的原子性
     *     juc底层原理 cas。
     * @param args
     */

}
