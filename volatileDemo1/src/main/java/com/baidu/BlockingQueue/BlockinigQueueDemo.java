package com.baidu.BlockingQueue;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockinigQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        //List list = new ArrayList();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);//创建数组类型的阻塞队列，阻塞队列默认是三个

        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b",2L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c",2L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("d",5L,TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
    }
}
