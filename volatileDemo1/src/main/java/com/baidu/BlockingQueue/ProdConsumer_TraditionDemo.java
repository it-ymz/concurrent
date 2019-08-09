package com.baidu.BlockingQueue;


import sun.misc.BASE64Encoder;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData{  //第一步  资源类   线程操作资源类

    //java 一切皆对象，先操作对象，然后在操作对象中的变量
    private int number = 0;
    private Lock lock = new ReentrantLock();   //多个线程操作资源类，必然会有争抢，必然需要加锁

    private Condition condition = lock.newCondition();

    //两个线程同时操作，资源类这么知道该那个干活，
    //例子：现在我要让空调制冷，某某需要让空调制热，那么空调肯定不能一直这么一个上一个下的来回调

    public void increment() throws InterruptedException {
        //判断   例子：现在就好比一个蛋糕房，没有蛋糕那么就可以生产
        //当前判断判断的是：当前线程给我一个标志位，轮到我做，由0变到1，马上生产好了，可以过来取了
        //而当现在有一个线程又过来，将状态从1变都到0，

        lock.lock();//lock加锁
        try{
            //判断
            while(number!=0){   //0 !=  0那么可以认为，现在生产者不需要等待，直接干活
                //等待，不能生产
                condition.await();  //lock锁提供了condition实体类，里面包含了await()等待线程,singal()唤醒线程
            }
            //干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            //通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();//lock解锁
        }

    }

    public void decrement() throws InterruptedException {
        //判断   例子：现在就好比一个蛋糕房，没有蛋糕那么就可以生产
        //当前判断判断的是：当前线程给我一个标志位，轮到我做，由0变到1，马上生产好了，可以过来取了
        //而当现在有一个线程又过来，将状态从1变都到0，

        lock.lock();//lock加锁
        try{
            //判断
            while(number==0){   //0 ==  0那么可以认为，现在消费者需要等待，当有值的话，那么就开始执行
                //等待，不能生产
                condition.await();  //lock锁提供了condition实体类，里面包含了await()等待线程,singal()唤醒线程
            }

            //干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            //通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();//lock解锁
        }

    }
}

/**
 * 一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1.轮5次
 *
 *   多线程的企业版模板口诀：高并发的情况下是：
 *   1. 高内聚、低耦合的情况下，线程  操作(方法)   资源类。
 *   2. 判断  干活，   唤醒通知。
 *   3. 严防  多线程状况下的    虚假唤醒。(多线程的判断使用while来判断，不使用if判断)
 *
 *
 *   一个初始温度为26度的空调，一个同学要让空调上升一度，一个同学要让空调降低一度
 *   升温一度，降温一度，这两个方法是资源类自身携带的
 *
 *   描述：线程  操作  资源类
 *   线程是在main方法中进行创建，然后操作  资源类(资源类自身高内聚携带对我要操作的方法)
 *
 *   判断  干活  唤醒通知
 *   对操作的公共资源属性进行判断(使用while循环进行判断)，条件不满足进行等待，条件满足跳出循环  干核心业务活，然后在唤醒线程
 *
 *   wait和natily属于object类的，和线程没关系
 *   await和signalAll是属于lock提供的
 *   多线程的判断不能使用if判断需要使用while()循环判断
 */
public class ProdConsumer_TraditionDemo {

    public static void main(String[] args) {

        new BASE64Encoder();
        ShareData shareData = new ShareData();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AAA").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BBB").start();
    }
}
