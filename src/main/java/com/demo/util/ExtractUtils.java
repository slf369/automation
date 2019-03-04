package com.demo.util;

import com.demo.util.common.JdbcUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by shi.lingfeng on 2018/3/29 0029.
 * 提取器
 */
public class ExtractUtils {


    public static Boolean needExtract(String str){
        Boolean flag=false;
        if(!str.trim().equals("") && str!=null){
            flag=true;
        }
        return flag;
    }

    /**
     * 根据提取名取得该行记录
     * @param name
     * @return
     */
    public static  Map<String,Object> getExtract(String name)  {
        Map<String, Object> map=null;
        try{
            JdbcUtils jdbcUtils = JdbcUtils.getInstance();
            String sql="select id, extract_name,extract_type,jsonpath_express,regex_express,left_express,right_express,sql_express,database_url,database_username,database_password from extract_info where extract_name=? ";
            List<Object> params = new ArrayList<Object>();
            params.add(name);
            map=jdbcUtils.findSimpleResult(sql,params);
            return map;
        }catch (Exception e){
            System.out.println("查询为空");
            e.printStackTrace();
        }
        return  map;
    }


    /**
     * 根据名称提取值，支持JsonPath,前后缀，正则提取
     * @param responseStr
     * @param name
     * @return
     */
   public static String extract(String responseStr,String name){
       String result="Not Found";
       try{
           Map<String,Object> map=ExtractUtils.getExtract(name);
           if(map.get("extract_type")!=null && !map.get("extract_type").equals("")){
               String extractType=String.valueOf(map.get("extract_type"));
               int type=Integer.parseInt(extractType);

               String jsonPath=String.valueOf(map.get("jsonpath_express"));
               String regex=String.valueOf(map.get("regex_express"));
               String prefix=String.valueOf(map.get("left_express"));
               String suffix=String.valueOf(map.get("right_express"));
               String sql=String.valueOf(map.get("sql_express"));
               String databaseUrl=String.valueOf(map.get("database_url"));
               String databaseUsername=String.valueOf(map.get("database_username"));
               String databasePassword=String.valueOf(map.get("database_password"));
               List<Object> params=new ArrayList<Object>();

               if(type==0){ //jsonpath提取
                   result= JsonPathUtil.parseJsonPath(responseStr,jsonPath,1);
               }else if (type==1){ // regex提取
                    // Todo
               }else if(type==2){ // 前后缀
                   result=StringUtils.saveParamLeftstrRightstr(responseStr,prefix,suffix);
               }else if(type==3){ // 执行sql
                   if(sql.trim().startsWith("select") || sql.trim().startsWith("SELECT")){ // 查询
                       if(PreParameter.needReplace(sql)){ // 判断Sql是否需要参数替换
                           sql=PreParameter.replaceParams(sql);
                       }
                       result=JdbcUtils.getInstance().executeSingelFieldQuerySql(databaseUrl,databaseUsername,databasePassword,sql);
                   }else { // 更新，删除
                       if(PreParameter.needReplace(sql)){ // 判断Sql是否需要参数替换
                           sql=PreParameter.replaceParams(sql);
                       }
                       result=JdbcUtils.getInstance().executeStatement(databaseUrl,databaseUsername,databasePassword,sql);
                   }
               }
           }
       }catch (Exception e){
           System.out.println("对报文返回解析错误");
           e.printStackTrace();
       }
       return  result;
   }




    /**
     * 将提取到的值写入数据库字段-extract_value
     * @param name
     * @param extractInfo
     * @return
     */
   public static Boolean writeResult(String name,String extractInfo){

       boolean flag=false;
       try{
           Map<String, Object> map=ExtractUtils.getExtract(name);
           String myid=String.valueOf(map.get("id"));
           System.out.println("id is:"+id);

           if(!extractInfo.equals("Not Found:")){
               JdbcUtils jdbcUtils = JdbcUtils.getInstance();
               String sql= "update  extract_info set extract_value=? where id=?";
               List<Object> params = new ArrayList<Object>();
               params.add(extractInfo);
               params.add(myid);
               flag = jdbcUtils.updateByPreparedStatement(sql,params);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
       return flag;
   }

    public static void main(String[] args) throws Exception {
        String responseStr="{\n" +
                "    \"globalCode\": \"0000\",\n" +
                "    \"globalMsg\": \"\",\n" +
                "    \"retCode\": \"0000\",\n" +
                "    \"retMsg\": \"操作成功\"\n" +
                "}";
        String returnInfo=ExtractUtils.extract(responseStr,"retCode");
        ExtractUtils.writeResult("retCode",returnInfo);
    }
}
