package com.demo.util;

import java.util.ArrayList;
import java.util.List; 
import org.testng.Assert;

/**
 * 自定义封装断言，使得碰到断言失败后，继续执行
 */
public class Assertion {
     
    public static boolean flag = true;
     
    public static List<Error> errors = new ArrayList<Error>();
    
    public static boolean assertCustom(Boolean flag1){
    	flag=flag1;
    	return flag;
    }
     
    public static boolean verifyEquals(Object actual, Object expected){
        try{
            Assert.assertEquals(actual, expected);
            return true;
        }catch(Error e){
            errors.add(e);
            flag = false;
            return false;
        }
    }
     
    public static boolean verifyEquals(Object actual, Object expected, String message){
        try{
            Assert.assertEquals(actual, expected, message);
            return true;
        }catch(Error e){
            errors.add(e);
            flag = false;
            return false;
        }
    } 
    
    
    public static boolean verifyContains(Object actual, Object expected){
    	try{
    		if(String.valueOf(actual).contains(String.valueOf(expected))){    			
        		return true;
        	}else {        		
                flag = false;               
        		return false;
        	}
    	}catch(Error e){
    		 errors.add(e);
             flag = false;
             return false;
    	}    	
    } 
    
    public static boolean verifyRegex(Object actual, String expected){
    	try{
    		expected=dealBackSlant(expected);
    		if(String.valueOf(actual).matches(expected)){
        		return true;
        	}else {
        		flag = false;        		
        		return false;
        	}
    	}catch(Error e){
    		 errors.add(e);
             flag = false;
             return false;
    	}    	
    }
    
    /**
     * @param str 单元个，读取到"\\"时，会显示"\\\\"
     * @return
     */
    public static String dealBackSlant(String str){    
    	while(str.contains("\\\\")){
    		str=str.replace("\\\\", "\\");    		
    	}
    	return str;
    }
}