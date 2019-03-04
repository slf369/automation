package com.demo.util;

import org.testng.Reporter;

public class ShowMessage {
	 public static  String  showAssertMessage(int index,String className,String assertType,String jsonPathExpress,String expectedResultDetail,String actualResultDetail,Boolean flag) {
		    Reporter.log("*******Case: "+className+" 成功/失败的断言描述如下****************************************");
		    Reporter.log("断言类型是：" + assertType);
		    Reporter.log("断言表达式/JsonPath是：" + jsonPathExpress);
		    Reporter.log("预期断言结果是：" + expectedResultDetail);
		    Reporter.log("实际断言结果是：" + actualResultDetail);
		    Reporter.log("****************************************************************************************\n");

		 StringBuffer sb = new StringBuffer();
		 if(flag){
		 	sb.append("第").append(index).append("个断言Pass\n")
					.append("*******Case: " + className + " 成功的断言描述如下*****************************************\n");

			 sb.append("断言类型是：").append(assertType).append("\n")
					 .append("断言表达式/JsonPath是：").append(jsonPathExpress).append("\n")
					 .append("预期断言结果是：").append(expectedResultDetail).append("\n")
					 .append("实际断言结果是：").append(actualResultDetail).append("\n")
					 .append("*****************************************************************************************\n");

		 }else{
			 sb.append("<font color=\"red\">").append("第").append(index).append("个断言Failed").append("</font>").append("\n")
					 .append("*******Case: " + className + " 失败的断言描述如下****************************************\n");

			 sb.append("断言类型是：").append(assertType).append("\n")
					 .append("断言表达式/JsonPath是：").append(jsonPathExpress).append("\n")
					 .append("预期断言结果是：").append("<font color=\"red\">").append(expectedResultDetail).append("</font>").append("\n")
					 .append("实际断言结果是：").append("<font color=\"red\">").append(actualResultDetail).append("</font>").append("\n")
					 .append("*****************************************************************************************\n");
		 }
		 return sb.toString();
	 }
}
