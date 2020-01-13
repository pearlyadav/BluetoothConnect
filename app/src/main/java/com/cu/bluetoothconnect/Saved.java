package com.cu.bluetoothconnect;

import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class Saved {

    private static Saved instance = null;
    static SparseIntArray s = new SparseIntArray();
    static Set<Integer> keys = new HashSet<>();

    String print(){
        ArrayList<Integer> a = new ArrayList<>();

        for (int i : keys)
            a.add(s.get(i));

        Collections.sort(a);

        String st = "";
        for(int i = 0; i<a.size(); i++){
            st = st + a.get(i) + " ";

        }


        return st;
    }

    static Saved getInstance() {
        if(instance == null) {
            instance = new Saved();
        }
        return instance;
    }

}
