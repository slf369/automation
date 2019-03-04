package com.demo.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shi.lingfeng on 2018/3/30.
 */
public class RexMatchUtil {

    public static String regMatch(String str,String regularExpress){
        ArrayList<String> list=new ArrayList<String>();
        String patternReg="\\$\\{[a-zA-Z0-9_]+\\}"; // 解析${param}这种正则，Param以字母数字下划线组成，前后不能有空格
        Pattern pattern=Pattern.compile(patternReg);
        Matcher matcher=pattern.matcher(str);
        while(matcher.find()){
            list.add(matcher.group().substring(2,matcher.group().length()-1));
        }
        return "";
    }
}
