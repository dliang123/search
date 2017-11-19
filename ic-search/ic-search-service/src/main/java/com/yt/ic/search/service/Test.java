package com.yt.ic.search.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Created by Administrator on 2017/11/5.
 */
public class Test {

    public static void main(String[] args) {
        HashMap hashMap=new HashMap();
        hashMap.put(null,1);
        hashMap.put(null,2);
        hashMap.put(1,2);
        hashMap.put("a",2);
        Hashtable ht = new Hashtable();
        ht.put(1,1);
        ht.put(1,2);
        ht.put("a",3);
        HashSet hashSet = new HashSet();
        hashSet.add(1);
        hashSet.add(1);

        System.out.println(ht.get(1));
        System.out.println(ht.get("a"));
    }
}
