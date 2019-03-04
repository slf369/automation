package com.demo;

import org.testng.TestNG;

/**
 * Created by shi.lingfeng on 2018/1/24.
 */
public class InvokeTestNG {
    public static void main(String[] args) throws ClassNotFoundException {
        String str="com.demo.testcase.Loging_check" ;
        TestNG testNG=new TestNG();
        testNG.setTestClasses(new Class[]{Class.forName(str)});
        testNG.run();
    }
}
