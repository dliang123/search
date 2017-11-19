package com.yuntu.rpc;

import org.omg.CORBA.ObjectHelper;

import java.util.PriorityQueue;

/**
 * Created by Administrator on 2017/11/8.
 */
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
//        return "Hello " + name;
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
        synchronized (queue){
            queue.notify();
        }
        //
    }
}
