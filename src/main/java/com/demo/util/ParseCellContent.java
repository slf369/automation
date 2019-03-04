package com.demo.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.LoggerFactory;

/**
 * 解析输入框的数据
 */
public class ParseCellContent {

	private static org.slf4j.Logger logger= LoggerFactory.getLogger(ParseCellContent.class);

	private static List<String[]> expectedResultList = new ArrayList<String[]>();
	private static List<String> expectedResultListSimple = new ArrayList<String>();
	private static List<String[]> tranferDateList = new ArrayList<String[]>();

	private static Map<String, String> header = new TreeMap<String, String>(new Comparator<String>() {
		public int compare(String key1, String key2) {
			return key1.compareTo(key2);
		}
	});
	
	
	private static Map<String, String> pathParam = new TreeMap<String, String>(new Comparator<String>() {
		public int compare(String key1, String key2) {
			return key1.compareTo(key2);
		}
	});

	/**
	 * 对单行如下等字符串进行分隔，以##分隔，再以=分隔 ?name=zhangsan ## password=vipjr
	 * 
	 * @param singleLineStr
	 * @return
	 */
	public static Map<String, String> parseSingleLine(String singleLineStr) {
		Map<String, String> singleLineMap = new TreeMap<String, String>(new Comparator<String>() {
			public int compare(String key1, String key2) {
				return key1.compareTo(key2);
			}
		});
		if (singleLineStr != null && !singleLineStr.equals("")) {
			String[] pairs = singleLineStr.split("##");
			for (String pair : pairs) {
				String[] str = pair.split("=");
				singleLineMap.put(str[0].trim(), str[1].trim());
			}
		}
		return singleLineMap;
	}

	/**
	 * @param multiLineStr
	 * @return 示例： equal ## $..id ## 101
	 * 				    contain ## $..name ## 数学
	 */
	public static List<String[]> parseMultipleLine(String multiLineStr) {
		List<String[]> multiLineList = new ArrayList<String[]>();
		if (multiLineStr.contains("##")) { // 判断是否有分隔符"##"
			if (multiLineStr.contains("\n")) { // 判断断言是否多行
				String[] lines = multiLineStr.split("\n");
				for (String line : lines) {
					if (!line.equals("") && line != null) {
						String cols[] = line.split("##");
						for (int i = 0; i < cols.length; i++) {
							cols[i] = cols[i].trim();
						}
						multiLineList.add(cols);
					}
				}
			} else {
				// 只有一行情况
				String cols[] = multiLineStr.split("##");
				for (int i = 0; i < cols.length; i++) {
					cols[i] = cols[i].trim();
				}
				multiLineList.add(cols);
			}
		} else {
			multiLineList.add(new String[] { multiLineStr });
		}
		return multiLineList;
	}


	/**
	 * 解析整行是断言内容的情况
	 * @param multiLineStr
	 * @return
	 */
	public static List<String> parseMultipleLineSimple(String multiLineStr) {
		List<String> multiLineList = new ArrayList<String>();
			if (multiLineStr.contains("\n")) { // 判断断言是否多行
				String[] lines = multiLineStr.split("\n");
				for (String line : lines) {
					if (!line.equals("") && line != null) {
						multiLineList.add(line.trim());
					}
				}
			} else {
				multiLineList.add(multiLineStr.trim());
			}
		return multiLineList;
	}


	public static Map<String, String> parseHeader(String message) {
		header = parseSingleLine(message);
		return header;
	}

	/**
	 * 解析Get请求？后面的参数字符串
	 * 
	 * @param message
	 * @return
	 */
	public static Map<String, String> parsePathParam(String message) {
		pathParam = parseSingleLine(message);
		return pathParam;
	}

	/**
	 * @param cellContent
	 *            ExpectedResult单元格内容
	 * @return
	 */
	public static List<String[]> parseExpectedResult(String cellContent) {
		expectedResultList = parseMultipleLine(cellContent);
		return expectedResultList;
	}

	/**
	 * 解析整行是断言内容的情况
	 * @param cellContent
	 * @return
	 */
	public static List<String[]> parseExpectedResultSimple(String cellContent) {
		expectedResultListSimple = parseMultipleLineSimple(cellContent);
		return expectedResultList;
	}

	/**
	 * @param cellContent
	 * @return 对如下等字符串进行分隔，以换行分隔，再以##分隔 "$.data.token ## tokenId
	 * 															$.data.user_id ## userId"
	 */
	public static List<String[]> parseParamTransfer(String cellContent) {
		tranferDateList = parseMultipleLine(cellContent);
		return tranferDateList;
	}

	/**
	 * @return Get请求的路径参数，以Map<String,String>显示
	 */
	public static Map<String, String> getPathParamsMap() {
		return pathParam;
	}

	/**
	 * @return 获取Header，以Map<String,String>显示
	 */
	public static Map<String, String> getHeadrMap() {
		return header;
	}

	/**
	 * @return 预期单元格的内容，以List<String[]>显示
	 */
	public static List<String[]> getExpectedResultList() {
		return expectedResultList;
	}

	/**
	 * @return 参数传递单元格的内容，以List<String[]>显示
	 */
	public static List<String[]> getParamTransferList() {
		return tranferDateList;
	}

	/**
	 * @param rowIndex
	 *            起始值为0
	 * @param columnIndex
	 *            起始值为0
	 * @return
	 */
	public static String getExpectedResultSubContent(int rowIndex, int columnIndex) {
		return expectedResultList.get(rowIndex)[columnIndex];
	}

	public static String getExpectedResultSubContentSimple(int rowIndex) {
		return expectedResultListSimple.get(rowIndex);
	}

	/**
	 * @param rowIndex
	 *            起始值为0
	 * @param columnIndex
	 *            起始值为0
	 * @return
	 */
	public static String getParamTransferSubContent(int rowIndex, int columnIndex) {
		return tranferDateList.get(rowIndex)[columnIndex];
	}

	public static List<String> parseExtractorCell(String lineStr){
		return parseSingleCellWithComma(lineStr);
	}


	/**
	 * 解析单行，以逗号分隔
	 * @param lineStr
	 * @return
	 */
	public static List<String> parseSingleCellWithComma(String lineStr){
		List<String> list=new ArrayList<String>();
		if(lineStr.contains(",")){
			String[] lines = lineStr.split(",");
			for (String line : lines) {
				if (!line.equals("") && line != null) {
					list.add(line.trim());
				}
			}
		}else{
			list.add(lineStr.trim());
		}
		return  list;
	}

	public static void main(String[] args)  {
		String str="str_aaa,bb";
		List<String> list=ParseCellContent.parseExtractorCell(str);
		System.out.println(list);
		String str1="\"globalCode\":\"0000\"\n" +
				"    \"globalMsg\":\"\"\n" +
				"    \"retCode\":\"0000\"\n" +
				"    \"retMsg\":\"操作成功\"";
		List<String> list1=ParseCellContent.parseMultipleLineSimple(str1);
		System.out.println(list1);
	}
}