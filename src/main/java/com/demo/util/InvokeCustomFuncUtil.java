package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 *
 * Created by shi.lingfeng on 2018/3/24.
 */
public class InvokeCustomFuncUtil {

    private  static Logger logger= LoggerFactory.getLogger(InvokeCustomFuncUtil.class);

    /**
     *  自定义参数：都以String为参数
     * @param methodFullPath 例子：com.demo.util.custom.DateUtil.getDay
     * @param params   new Object[]{'yyyyMMdd','0'}
     * @return
     */
    public static  Object invokeCustomMethod(String methodFullPath,Object[] params){
        String classPath=getClassName(methodFullPath);
        String methodName=getMethodName(methodFullPath);
        return  invokeCustomMethod(classPath,methodName,params);
    }


    public static Object  invokeCustomMethod(String clazzName,String MethodName, Object[] params)  {
        try{
            Class<?> clazz=Class.forName(clazzName);
            Object obj=clazz.newInstance();
            Method[] methods=clazz.getDeclaredMethods();
            for(Method method:methods){
                if(method.getName().equalsIgnoreCase(MethodName)){
                    // 获取方法的所有参数类型
                    Class<?>[] pts=method.getParameterTypes();
                    if(params==null || params.equals("")){
                        Object result=method.invoke(obj);
                        logger.info("方法返回值是：{}",String.valueOf(result));
                        return  result;
                    }else if (params.length==pts.length ){
                        Object result=method.invoke(obj,params);
                        logger.info("方法返回值是：{}",String.valueOf(result));
                        return  result;
                    } else {
                        logger.error("传递参数跟方法参数个数不匹配");
                        return  "Parameter Not Match";
                    }
                }
            }
        }catch (Exception e){
            logger.error("invokeMethod 错误，请检查输入函数方法名或参数");
            e.printStackTrace();
        }
        return  "InvokeMethodError";
    }


    public static String getClassName(String methodFullPath){
        if(methodFullPath.contains(".")){
            int position=methodFullPath.lastIndexOf(".");
            return methodFullPath.substring(0,position);
        }
        return methodFullPath;
    }

    public static String getMethodName(String methodFullPath){
        if(methodFullPath.contains(".")){
            int position=methodFullPath.lastIndexOf(".");
            return methodFullPath.substring(position+1);
        }
        return methodFullPath;
    }


    public static void main(String[] args) throws Exception {
        String clazzName1="com.demo.util.custom.DateUtil";
        String methodName1="getDay";
        invokeCustomMethod(clazzName1,methodName1,new Object[]{"yyyy-MM-dd","0"});
        String clazzName="com.demo.util.custom.UUIDUtil";
        String methodName="getUUID";
        invokeCustomMethod(clazzName,methodName,null);
    }
}
