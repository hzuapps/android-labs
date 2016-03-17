package edu.hzuapps.androidworks;

import java.util.Date;
import java.io.File;

import static edu.hzuapps.androidworks.demo.StaticMethods.*;

/**
 * javadoc
 * Created by zengsn on 16/3/11.
 */
public class Clazz {

    protected  int i = 0;
    float f = 0.0f;
    boolean b = false;


    String str = null;
    Integer ii = null;

    Clazz1 mClazz1 = new Clazz1();


    public Clazz() {
    }

    public Clazz(int i) {
        this.i = i;
    }

    void func() {

        int iii = MAX;

        Date date = new Date();

        File file = new File("");

        if (b) {

        }

        for (int j=0; j<10; j++) {

        }

        while (b) {
            ;
        }

    }

    public static void main(String[] args) {
        Clazz clazz = new Clazz();
        clazz.b = true;

        Clazz clazz1 = new Clazz();
        clazz1.b = false;

        try {
            float f = (float) 11;
        } catch (Exception e) {

        }

        Clazz2 clazz2 = new Clazz2();
        try {
            clazz2.func();
        } catch (MyException e) {
            e.printStackTrace();
        }

        Clazz3 clazz3 = new Clazz3();
        clazz3.func();
    }
}

class  Clazz1 {
    //private int i;
    protected int j;
    int k;
    public int l;


    void func() {
        Clazz clazz = new Clazz();
    }

    public int getI() {
        return 0;
    }

    public int getJ() {
        return j;
    }

    public int getK() {
        return k;
    }

    public int getL() {
        return l;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setL(int l) {
        this.l = l;
    }
}

class  Clazz2  {

    void func() throws MyException {
        //this.j = 1;
        throw  new MyException();
    }
}

class  Clazz3  {

    void func() throws MyRuntimeException {
        //this.j = 1;
        throw  new MyRuntimeException();
    }
}

class  MyException extends  Exception {}

class MyRuntimeException extends  RuntimeException {}

