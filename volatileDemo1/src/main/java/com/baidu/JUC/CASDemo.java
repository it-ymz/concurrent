package com.baidu.JUC;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS是什么？ ====>compareAndSet
 * 比较并交换
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.getAndIncrement();

        boolean b = atomicInteger.compareAndSet(5, 2019);//compareAndSet就是CAS，它有两个参数( int expect,int update) 期望值，更新值
        System.out.println(b+"\t current data:"+atomicInteger.get());

        //atomicInteger.compareAndSet(期望值,修改值)  执行顺序：首先线程从主内存中拷贝值，然后线程修改完以后，将值写入主内存中的时候先根据自己的期望值与主内存中的值进行比较，比较通过然后在写入

        boolean c = atomicInteger.compareAndSet(5, 1024);//compareAndSet就是CAS，它有两个参数( int expect,int update) 期望值，更新值
        System.out.println(c+"\t current data:"+atomicInteger.get());



        //CAS的意思是说：这个线程在多线程的环境下牵扯到多个线程去操作主内存中的变量，变量副本拷贝，考到线程的私有内存中
        //然后修改值后在写到主内存中，会发现线程写丢失，线程值覆盖等问题

        //例子：现在一个教室中一个讲台就是一个主内存，而讲台上的一瓶水就是一个初始值，而前一排的三位同学三个线程从主内存中拷贝变量回到自己的主内存中
        //此时，这三个线程都得到了这三瓶矿泉水，第一个线程需要根据自己的劳动结果将矿泉水改为鼠标，然后写回到主内存中，此时主内存中应该是鼠标，但是
        //现在不要意思，第一个线程现在碰到的问题是，它不期望别人在他之前动过改过主内存中的值，要不然第一个线程还需要将主内存中的值拷贝到自己的内存中，针对这个值在进行修改为鼠标，在次写到主内存中

        //unsafe:它是在jdk/jre/bin底下就有这个jar包


    }
}
