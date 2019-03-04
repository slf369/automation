package com.demo.util.backup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlDataProvider {
	public static  Object[][] getTestData(String tablename){
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/book";
		String user="root";
		String password="ROOT";
		List<Object[]> records=new ArrayList<Object[]>();

		try {
			Class.forName(driver);
			Connection conn= DriverManager.getConnection(url,user,password);
			if(!conn.isClosed()){
				System.out.println("数据库连接成功");
			}
			Statement statement=conn.createStatement();
			String sql="select * from "+tablename;
			ResultSet rs=statement.executeQuery(sql);
			ResultSetMetaData rsMetaData=rs.getMetaData();
			int cols=rsMetaData.getColumnCount();
			while(rs.next()){
				String fields[]=new String[cols];
				int col=0;
				for(int colIdx=0;colIdx<cols;colIdx++){
					fields[col]=rs.getString(colIdx+1);
					col ++;
				}
				records.add(fields);
				System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
			}
			rs.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e){
			e.printStackTrace();
		}

		Object[][] results=new Object[records.size()][];
		for(int i=0;i<records.size();i++){
			results[i]=records.get(i);
		}
		return results;
	}

	public static void main(String[] args) {
		Object[][] objects=SqlDataProvider.getTestData("book");
		for(int i=0;i<objects.length;i++){
			for(int j=0;j<objects[i].length-1;i++){
				System.out.print(objects[i][j]+" ");
			}
			System.out.println();
		}
	}
}
