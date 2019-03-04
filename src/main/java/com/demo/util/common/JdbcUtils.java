package com.demo.util.common;

import com.demo.util.StringUtils;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * jbdc功能类-增删改查
 */
  
public class JdbcUtils {  
	private Connection connection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;

    // 单例
    private volatile static JdbcUtils instance;

    private JdbcUtils() {
    }

    public static JdbcUtils getInstance(){
        if(instance==null){
            synchronized (JdbcUtils.class){
                if(instance==null){
                    instance=new JdbcUtils();
                }
            }
        }
        return instance;
    }
    
      
    /** 
     * 获得数据库的连接 -- 连接本地
     * @return
     * @throws SQLException 
     */  
    public Connection getConnection() throws SQLException{  
        connection = DBUtils.getConnection();
        //如果数据库支持utf8mb4 建立连接后需要使用下面的代码
        //connection.prepareStatement("set names utf8mb4").executeQuery();
        return connection;  
    }


    /**
     * 获得数据库的连接-- 连接其他库
     * @return
     * @throws SQLException
     */
    public Connection getConnection(String url,String username,String password) throws SQLException{
        connection = DBUtils.getConnection(url,username,password);
        //如果数据库支持utf8mb4 建立连接后需要使用下面的代码
        //connection.prepareStatement("set names utf8mb4").executeQuery();
        return connection;
    }
      
    /** 
     * 增加、删除、改 -- 本地
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public boolean updateByPreparedStatement(String sql, List<Object> params)throws SQLException{
        boolean flag = false;
        int result = -1;
        this.getConnection();
        pstmt = connection.prepareStatement(sql);
        int index = 1;
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;
        this.releaseConn();
        return flag;  
    }


    /**
     * 增加、删除、改 -- 其他库
     * @param sql
     * @return
     * @throws SQLException
     */
    public String executeStatement(String url,String username,String password,String sql)throws SQLException{
        String msg="未执行";
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement st = conn.createStatement();
        int resultNum = st.executeUpdate(sql);
        //处理结果
        if(resultNum>0){
            msg= "操作成功";
        }else{
            msg= "操作失败";
        }
        st.close();
        conn.close();
        return msg;
    }


    /** 
     * 查询单条记录 -- 其他库
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public Map<String, Object> findSimpleResult(String url,String username,String password,String sql, List<Object> params) throws SQLException{
        Map<String, Object> map = new HashMap<String, Object>();  
        int index  = 1;
        this.getConnection(url,username,password);
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i=0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();//返回查询结果  
        ResultSetMetaData metaData = resultSet.getMetaData();  
        int col_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            for(int i=0; i<col_len; i++ ){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                map.put(cols_name, cols_value);  
            }  
        }  
        this.releaseConn();
        return map;  
    }



    /**
     * 查询单条记录--本地
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{
        Map<String, Object> map = new HashMap<String, Object>();
        int index  = 1;
        this.getConnection();
        pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();//返回查询结果
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();
        while(resultSet.next()){
            for(int i=0; i<col_len; i++ ){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
        }
        this.releaseConn();
        return map;
    }
  
    /**
     * 查询多条记录 -- 本地
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public List<Map<String, Object>> findMoreResult(String sql, List<Object> params) throws SQLException{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        int index = 1; 
        this.getConnection();
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            Map<String, Object> map = new HashMap<String, Object>();  
            for(int i=0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                map.put(cols_name, cols_value);  
            }  
            list.add(map);  
        }
        this.releaseConn();
        return list;  
    }  
  
    /**
     * 通过反射机制查询单条记录 -- 本地
     * @param sql 
     * @param params 
     * @param cls 
     * @return 
     * @throws Exception 
     */  
    public <T> T findSimpleRefResult(String sql, List<Object> params,  
            Class<T> cls )throws Exception{  
        T resultObject = null;  
        int index = 1;
        this.getConnection();
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData  = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            //通过反射机制创建一个实例  
            resultObject = cls.newInstance();  
            for(int i = 0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                Field field = cls.getDeclaredField(cols_name);  
                field.setAccessible(true); //打开javabean的访问权限  
                field.set(resultObject, cols_value);  
            }  
        } 
        this.releaseConn();
        return resultObject;  
    }  
  
    /**
     * 通过反射机制查询多条记录 -- 本地
     * @param sql  
     * @param params 
     * @param cls 
     * @return 
     * @throws Exception 
     */  
    public <T> List<T> findMoreRefResult(String sql, List<Object> params,  
            Class<T> cls )throws Exception {  
        List<T> list = new ArrayList<T>();  
        int index = 1; 
        this.getConnection();
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData  = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            //通过反射机制创建一个实例  
            T resultObject = cls.newInstance();  
            for(int i = 0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                Field field = cls.getDeclaredField(cols_name);  
                field.setAccessible(true); //打开javabean的访问权限  
                field.set(resultObject, cols_value);  
            }  
            list.add(resultObject);  
        } 
        this.releaseConn();
        return list;  
    }  
  
    /** 
     * 返回单个结果值，如count\min\max等 
     *  
     * @param sql 
     *            sql语句 
     * @param paramters 
     *            参数列表 
     * @return 结果 
     * @throws SQLException 
     */  
    public  Integer queryForInt(String sql, Object... paramters)  
            throws SQLException {  
    	Integer result = null;  
        
        try {  
        	this.getConnection();
        	pstmt = connection.prepareStatement(sql);  
   
            for (int i = 0; i < paramters.length; i++) {  
                pstmt.setObject(i + 1, paramters[i]); 
            }  
            resultSet = pstmt.executeQuery(); 
            
            ResultSetMetaData metaData = resultSet.getMetaData();  
            while(resultSet.next()){  
                String cols_name = metaData.getColumnName(0+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                result = Integer.valueOf(cols_value.toString());
            } 
            return result;  
        } catch (SQLException e) {  
            throw new SQLException(e);  
        } finally {  
        	releaseConn(); 
        }  
    }

    /**
     * 查询单语句单字段-本地
     * @param sql
     * @return
     * @throws SQLException
     */
    public String executeSingelFieldQuerySql(String sql) throws SQLException {
        Map<String,Object> map= new JdbcUtils().findSimpleResult(sql,null);
        String field=StringUtils.saveParamLeftstrRightstr(sql,"select","from").trim();
        return (String) map.get(field);
    }

    /**
     * 查询单语句单字段-其他
     * @param url
     * @param username
     * @param password
     * @param sql
     * @return
     * @throws SQLException
     */
    public String executeSingelFieldQuerySql(String url,String username,String password,String sql) throws SQLException {
        Map<String,Object> map= new JdbcUtils().findSimpleResult(url,username,password,sql,null);
        String field=StringUtils.saveParamLeftstrRightstr(sql,"select","from").trim();
        return (String) map.get(field);
    }


    /** 
     * 释放数据库连接 
     */  
    public void releaseConn(){  
    	DBUtils.close(resultSet, pstmt, connection);  
    }

    public static void main(String[] args) throws Exception {
//        String sql="select param_value from param_info where param_name='MOBILEID'";
////        Map<String,Object> map= new JdbcUtils().findSimpleResult(sql,null);
////        System.out.println(map.get("param_value"));
//        new JdbcUtils().executeSingelFieldQuerySql(sql);
//        System.out.println(new JdbcUtils().executeSingelFieldQuerySql(sql));

//        String sql ="select * from case_info where id in('1','2','3')";
//        List<Object> params=new ArrayList<Object>();
//        params.add("'1','2','3'");
//        List<Map<String,Object>> list=JdbcUtils.getInstance().findMoreResult(sql,null);
//        System.out.println("list.size():"+list.size());
//        for(Map<String,Object> map:list){
//            for(Map.Entry entry: map.entrySet()){
//                System.out.println(entry.getKey()+"->"+entry.getValue());
//            }
//        }

        String url="jdbc:mysql://172.16.16.20:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username="root";
        String password="123456";
        String sql="UPDATE x2_exams set exam='演示试卷yi' where examid=1";
        JdbcUtils.getInstance().executeStatement(url,username,password,sql);
    }
}  