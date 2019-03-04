package com.demo.util;

import com.demo.util.common.JdbcUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数替换时，替换自定义参数
 * Created by shi.lingfeng on 2018/3/1.
 */
public class CustomMethodUtil {

    public String invokeMethod(String methodName) throws SQLException {
        JdbcUtils jdbcUtils = JdbcUtils.getInstance();
        List<Object> params = new ArrayList<Object>();
        params.add(methodName);
        String sql = "select func_value,func_formal_params,func_actual_params from func_info where func_name=?";
        Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
        String invokeName = String.valueOf(map.get("func_value"));
        StringBuffer buffer=new StringBuffer();
        try {
            if (map.get("func_actual_params") != null && !map.get("func_actual_params").equals("")) {
                List<String> methodTypeList = ParseCellContent.parseSingleCellWithComma(String.valueOf(map.get("func_formal_params")));
                List<String> methodParamList = ParseCellContent.parseSingleCellWithComma(String.valueOf(map.get("func_actual_params")));
                Map<String, String> map1 = new HashMap<String, String>();
                int num = methodParamList.size();
                for (int i = 0; i < num; i++) {
                    map1.put(methodParamList.get(i), methodTypeList.get(i));
                }
                for(String key:map1.keySet()){
                    if(map1.get(key).equalsIgnoreCase("int") || map1.get(key).equalsIgnoreCase("Integer")){
                        buffer.append(Integer.valueOf(key)).append(",");
                    }else{
                        buffer.append(String.valueOf(key)).append(",");
                    }
                }
                System.out.println(map1.toString());
                System.out.println(buffer.toString().substring(0,buffer.toString().length()-1));
            }
        } catch (Exception e) {
            System.out.println("读取自定义函数异常");
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws SQLException {
        new CustomMethodUtil().invokeMethod("currentDay");
    }

}
