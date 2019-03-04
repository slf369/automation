package com.demo.util;

import org.testng.Reporter;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  HttpFiexture
 *  截取前缀后缀之间的值
 */

public class StringUtils {

    static String   encode = "utf-8";
    /**
     * 通过左右边界抓出目标字符串返回
     */
    public static  String saveParamLeftstrRightstr(String searchStr,String leftstr, String rightstr) {
        byte[] search=searchStr.getBytes();
        byte[] left;
        byte[] right;
        byte[] content = null;
        int start;
        int end;
        try {

            left = isContainChinese(leftstr) ? leftstr.getBytes("GBK") : leftstr.getBytes(encode);
            right = isContainChinese(rightstr) ? rightstr.getBytes("GBK") : rightstr.getBytes(encode);

            for (int a = 0; a < (search.length - left.length
                    - right.length + 1); a++) {
                boolean result = true;
                for (int b = 0; b < left.length; b++) {
                    if (search[a + b] != left[b]) {
                        result = false;
                        break;
                    }
                }
                if (result) {
                    // 注意
                    start = a + left.length;
                    for (int a1 = start; a1 < (search.length
                            - right.length + 1); a1++) {
                        boolean result2 = true;
                        for (int b1 = 0; b1 < right.length; b1++) {
                            if (search[a1 + b1] != right[b1]) {
                                result2 = false;
                                break;
                            }
                        }
                        if (result2) {
                            end = a1 - 1;
                            if (start > end) {
                                // System.out.println("start is "+start);
                                // System.out.println("end is "+end);
                                // System.out.println("start>end");
                                Reporter.log("关联：在response中通过左边界：" + leftstr
                                        + "。右边界：" + rightstr + "关联到的内容为空", true);
                                return "";
                            } else {
                                content = new byte[end - start + 1];
                                int j = 0;
                                for (int a2 = start; a2 <= end; a2++) {
                                    content[j] = search[a2];
                                    j++;
                                }
                                String collstr = new String(content, encode);
                                Reporter.log("关联：在response中通过左边界：" + leftstr
                                        + "。右边界：" + rightstr + "关联到的内容为:"
                                        + collstr, true);
                                return collstr;
                            }
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Reporter.log(ex.getMessage(), true);
        }
        return "";
    }


    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 替换Postman返回后，格式化":"冒号前后的空格
     * @param str
     * @return
     */
    public static String   replaceSpace(String str){
        if(str.contains(":")){
            StringBuffer sb=new StringBuffer();
            String[] array=str.split(":");
            for(int i=0;i<array.length;i++){
                if(i==array.length-1){
                    sb.append(array[i].trim());
                }else{
                    sb.append(array[i].trim()).append(":");
                }
            }
            return sb.toString();
        }else
            return str;
    }

    public static void main(String[] args) {
        String  str="\"globalCode\": \"0000\",\n" +
                "    \"globalMsg\": \"\",\n" +
                "    \"retCode\": \"0000\",\n" +
                "    \"retMsg\": \"操作成功\"";
        String str1=StringUtils.replaceSpace(str);
        System.out.println(str1);
    }
}
