package com.yt.ic.search.service;

import sun.security.krb5.internal.TGSRep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/11/5.
 */
public class Test2  extends Thread{

    TestReentrantLock lock;
    private  int id;
    public Test2(int i,TestReentrantLock test){
        this.id=i;
        this.lock=test;
    }

    public void run(){
        lock.print(id);
    }

    public static void main(String[] args) {
        ExecutorService
                service= Executors.newCachedThreadPool();
        TestReentrantLock
                lock=new TestReentrantLock();
        for(int i=0;i<10;i++){
            service.submit(new Test2(i,lock));
        }
        service.shutdown();
    }


}
class TestReentrantLock{
    private ReentrantLock
            lock=new ReentrantLock();


    public void print(int str){
        try {
            lock.lock();
            System.out.println(str+"获得");
            Thread.sleep((int)(1000));
//            Thread.sleep((int)(Math.random()*1000));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.println(str+"释放");
            lock.unlock();
        }
    }
}
