package com.demo.util.backup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VarArgsMethodInvoke  {

    public void printVarArgs(String... varArgs) {
        System.out.format("printVarArgs：\n");
        for (String arg : varArgs) {
            System.out.format("%20s\n", arg);
        }
    }

    public static void main(String[] args) {
        VarArgsMethodInvoke object = new VarArgsMethodInvoke();
        Class<? extends VarArgsMethodInvoke> cls = object.getClass();
        try {
//            Class[] argTypes = new Class[]{String[].class};
            Method declaredMethod = cls.getDeclaredMethod("printVarArgs", String[].class);
            String[] varArgs = {"shixin", "zhang"};
            declaredMethod.invoke(object, (Object) varArgs);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}