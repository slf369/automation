package com.demo.util;
import com.demo.util.common.JdbcUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shi.lingfeng on 2018/3/26.
 * 请求参数替换
 */

public class PreParameter {

    /**
     * 判断是否要替换
     * @param str
     * @return
     */
    public static Boolean needReplace(String str){
        ArrayList<String> list=new ArrayList<String>();
        String patternReg="\\$\\{[a-zA-Z0-9_]+\\}"; // 解析${param}这种正则，Param以字母数字下划线组成，前后不能有空格
        Pattern pattern=Pattern.compile(patternReg);
        Matcher matcher=pattern.matcher(str);
        while(matcher.find()){
            list.add(matcher.group().substring(2,matcher.group().length()-1));
        }
        if(list.size()>0){
            return true;
        }
        return false;
    }

    /**
     * 获取要替换的字段
     * @param str
     * @return
     */
    public static ArrayList<String> getReplaceFields(String str){
        ArrayList<String> list=new ArrayList<String>();
        String patternReg="\\$\\{[a-zA-Z0-9_]+\\}"; // 解析${param}这种正则，Param以字母数字下划线组成，前后不能有空格
        Pattern pattern=Pattern.compile(patternReg);
        Matcher matcher=pattern.matcher(str);
        while(matcher.find()){
            list.add(matcher.group().substring(2,matcher.group().length()-1));
        }
        return list;
    }

    /**
     * 替换参数
     * @param inputString
     * @return
     * @throws SQLException
     */
    public static String replaceParams(String inputString) throws SQLException {
        ArrayList<String> list=getReplaceFields(inputString);
        if(list.size()>0){
            JdbcUtils jdbcUtils = JdbcUtils.getInstance();
            String sql="select param_value from param_info where param_name=? ";
            String sql2="select extract_value from extract_info where extract_name=?";
            String sql3="select func_value,func_params from func_info where func_name =?";

            for(String each:list) {
                // 替换参数表的数据
                List<Object> params = new ArrayList<Object>();
                params.add(each);
                Map<String, Object> map=jdbcUtils.findSimpleResult(sql,params);
                if(map.get("param_value")!=null && !map.get("param_value").equals("")){
                    String value=String.valueOf(map.get("param_value"));
                    String toReplace="\\$\\{"+each+"\\}";
                    inputString=inputString.replaceAll(toReplace,value);
                }

                //替换提取器的值
                List<Object> params2 = new ArrayList<Object>();
                params2.add(each);
                Map<String, Object> map2=jdbcUtils.findSimpleResult(sql2,params2);
                if(map2.get("extract_value")!=null && !map2.get("extract_value").equals("")){
                    String value=String.valueOf(map2.get("extract_value"));
                    String toReplace="\\$\\{"+each+"\\}";
                    inputString=inputString.replaceAll(toReplace,value);
                }

                // 替换自定义函数的值
                List<Object> params3 = new ArrayList<Object>();
                params3.add(each);
                Map<String, Object> map3=jdbcUtils.findSimpleResult(sql3,params3);
                if(map3.get("func_value")!=null && !map3.get("func_value").equals("")){
                    String func_method=String.valueOf(map3.get("func_value"));
                    String func_params=String.valueOf(map3.get("func_params"));
                    List<String> list3=  ParseCellContent.parseSingleCellWithComma(func_params);
                    String[] array3 = list3.toArray(new String[0]);
                    String funcString=(String)InvokeCustomFuncUtil.invokeCustomMethod(func_method,array3);
                    String toReplace="\\$\\{"+each+"\\}";
                    inputString=inputString.replaceAll(toReplace,funcString);
                }
            }            
        }
        return inputString;
    }

    public static void main(String[] args) throws SQLException {
        String str="zhangsan shi ge hao hui ji \n" +
                "${MOBILEID} wo yesh zhem jue${MOBILEID}d \n" +
                "wo ${name}";
        ArrayList<String> list=PreParameter.getReplaceFields(str);
        System.out.println(list);
        System.out.println(PreParameter.replaceParams(str));
    }
}
