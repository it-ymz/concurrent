package com.baidu.JUC;

public class SinglatonDemo {

    /**
     * 单例模式分为六中：
     *   单例模式：懒汉、恶汉
     */

    private static volatile SinglatonDemo instance = null;

    private SinglatonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法singletonDemo()");
    }

    //Dcl (Doouble Check Lock双端检锁机制)  在多线程的情况下可以
    public static SinglatonDemo getInstance(){
        if(instance == null){  //进来和判断前都进行检索，相当于走了两遍安检。
            synchronized (SinglatonDemo.class){
                if(instance == null){
                    instance = new SinglatonDemo();
                }
            }
        }
        return instance;
    }

    /**
     * 像原来的单例模式下，只能是在单线程的环境下才可以保证一个类只创建一个对象
     * 而如果使用多线程的情况下，去访问单例模式的话，那么就会出现不一样的状况，它会创建多个对象
     *
     *
     * 如何解决？
     * 可以使用sync来进行同步方法，不过对于高并发的情况下，如果直接同步方法，那么这个方法中的代码都会被锁上，其他的线程只能等待
     *   public static synchronized SinglatonDemo getInstance(){
            if(instance == null){
               instance = new SinglatonDemo();
              }
               return instance;
             }


       可以使用 DCL(双端检索)机制不一定线程安全，原因是有指令重排的存在，加入volatile可以禁止指令重排
       原因在于某一个线程执行到第一次检索，读取到的instance不为null。instance的引用对象可能没有完成初始化、
     * @param args
     */
    //懒汉
    public static void main(String[] args){
        //单线程  (main 线程的操作动作)
       /* System.out.println(SinglatonDemo.getInstance() == SinglatonDemo.getInstance());
        System.out.println(SinglatonDemo.getInstance() == SinglatonDemo.getInstance());
        System.out.println(SinglatonDemo.getInstance() == SinglatonDemo.getInstance());*/

        //并发多线程后，情况发生了很大的变化
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                SinglatonDemo.getInstance();
            }).start();
        }
    }
}
