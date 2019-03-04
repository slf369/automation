//package com.demo.util.custom;
//
//import org.apache.commons.codec.digest.DigestUtils;
//
//import java.util.*;
//
///**
// * Created by shi.lingfeng on 2018/2/28.
// */
//public class Signature {
//    //加密所需KEY, 添加在待加密字符串的末尾.
//    final static String KEY = "${KEY}";
//    //计算SIGNATURE
//    public static String  getSign(HashMap map){
//        Collection keyset= map.keySet();
//        List list=new ArrayList(keyset);
//        Collections.sort(list);
//        StringBuffer sb = new StringBuffer();
//        for(int i=0; i<list.size(); i++){
//            sb.append(list.get(i)).append(map.get(list.get(i)));
//        }
//        String str = sb.toString() + KEY;
//        String sign = DigestUtils.md5Hex(str);
//        return sign;
//    }
//
//    public static void main(String[] args) {
//        String rsa_pwd = com.jifen.service.util.RSAUtils.EncodeStr("${PASSWORD}");
//
//        HashMap map = new HashMap();
//        map.put("authType", "MD5");
//        map.put("msgVersion", "${APP_VERSION}");
//        map.put("custString", "app");
//        map.put("platform", "android");
//        map.put("appId", "app");
//        map.put("coordinate", "0.0,0.0");
//        map.put("machineNo", "E3508E56FF5C44A296EA8BC5FB021CC2");
//        map.put("reqTime", "${REQ_TIME}");
//        map.put("mobileNo", "${MOBILE}");
//        map.put("type", "1");
//        map.put("code", rsa_pwd);
//        map.put("ip", "${MOBILE_IP}");
//        map.put("channel_source", "${CHANNEL_SOURCE}");
//
//
//    }
//
//
//}