package com.yt.ic.search.service;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/11/5.
 */
public class ThreadTest implements Runnable {

    private int i;

    public ThreadTest(int _i) {
        i = _i;
    }

    @Override
    public void run() {
        A a = A.getA();
//        a.setDd(i);
        a.a();
        a.b();
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new ThreadTest(i));
        }
    }
}

class A {

    private static int count = 1;
    private static int countB = 1;
    private int dd = 1;

    public void setDd(int _dd) {
        this.dd = _dd;
    }

    private static A a;

    private A() {
    }

    public static A getA() {
        if (a == null) {
            a = new A();
        }
        return a;
    }

    public synchronized void a() {
        System.out.println("a:" + dd + ":" + count++);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public  void b() {
        System.out.println("b:" + dd + ":" + countB++);
//        a();/55
    }
}

