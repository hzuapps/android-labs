package edu.hzuapps.androidworks;

// java.lang.*

import android.widget.TextView;

import java.math.BigInteger;
import java.util.Date;
import java.io.File;
import java.util.StringTokenizer;

import edu.hzuapps.androidworks.demo.StaticMethods;

import static edu.hzuapps.androidworks.demo.StaticMethods.*;

/**
 * javadoc
 * Created by zengsn on 16/3/11.
 */
public class Clazz {

    protected int i = 0;
    private float f = 0.0f;
    boolean b = false;


    String str = "abc def ghi";
    Integer ii = null;

    StringBuffer strBuff = null;
    StringBuilder strBui = null;


    Clazz1 mClazz1 = new Clazz1();

    int[] arr = new int[10];

    public Clazz() {
    }

    public Clazz(int i) {
        this.i = i;
    }

    void func() {


        StringTokenizer strTok = new StringTokenizer(str);
        while (strTok.hasMoreTokens()) {
            String token = strTok.nextToken();
        }

        int bigInt = 1111111111;
        BigInteger bi = new BigInteger("111111111111111111111111111111");

        Integer iiii = 1;
        Float fffff = 0.0f;
        Boolean bbbbb = true;

        System.out.print("");
        System.currentTimeMillis();

        int iii = MAX;

        Date date = new Date();

        File file = new File("");

        if (b) {

        }

        for (int j = 0; j < 10; j++) {

        }

        while (b) {
            ;
        }

        Class oneClass = this.getClass();

    }

    public static void main(String[] args) throws ClassNotFoundException {
        int i = 1;
        int j = 2;
        j = 0;
        try {
            int k = i / j;
            //
            //
        } catch (RuntimeException e) {
            // unchecked exception
            throw new MyRuntimeException();
        }
        Clazz clazz = new Clazz();

        Class oneClass = Class.forName("edu.hzuapps.androidworks.Clazz");
        oneClass = Clazz.class;
        // Method, Field, Contructor, Package,
        try {
            Object object = oneClass.newInstance();
            if (object instanceof  Clazz) {

            }
            clazz = (Clazz) object; // ClassCastException
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        Object object1 = clazz;
        clazz.func();


        try {
            Thread.sleep(1000*3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread current = Thread.currentThread();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.print(">>>");
            }
        });
        thread.run();

    }
}

class Clazz1 {
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

class Clazz2 {

    void func() throws MyException {
        //this.j = 1;
        throw new MyException();
    }
}

class Clazz3 {

    void func() throws MyRuntimeException {
        //this.j = 1;
        throw new MyRuntimeException();
    }
}

class MyException extends Exception {
}

class MyRuntimeException extends RuntimeException {
}