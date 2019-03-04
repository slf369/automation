package com.demo.util.backup;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * HTTPClient工具类
 * 
 *
 */
public class HttpClientUtil_backup {

	

	private static String responseStr = "";

	/**
	 * @param url
	 *            请求的URL
	 * @param header
	 *            请求头 (暂不使用)
	 * @param jsonBody
	 *            Post请求体
	 */
	public static String postMethodWithRawBody(String url, Map<String, String> header, String jsonBody)
			throws IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		CloseableHttpClient httpClient =HttpConnectionManager.getHttpClient();

		// 设置请求的实体
		StringEntity entity = new StringEntity(jsonBody);
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);

		// 这边暂不需报文头设置
		if (header != null && !header.isEmpty()) {
			for (Map.Entry<String, String> map : header.entrySet()) {
				httpPost.addHeader(map.getKey(), map.getValue());
			}
		}

		CloseableHttpResponse response = httpClient.execute(httpPost);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity resEntity = response.getEntity();
			String resStr = EntityUtils.toString(resEntity, "UTF-8");
			responseStr = Unicode2String.convertUnicode(resStr);
		}	
		response.close();
		httpClient.close();
		return responseStr;
	}

	/**
	 * @param url
	 *            Get请求的URL
	 * @param header
	 *            请求头 (暂不使用)
	 * @param pathParam
	 *            get请求的路径参数
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void getTypeRequestMethod(String url, Map<String, String> header, Map<String, String> pathParam)
			throws IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		StringBuffer sb = new StringBuffer();
		int size = pathParam.size();
		int num = 1;
		if (pathParam != null && !pathParam.isEmpty()) {
			for (Map.Entry<String, String> map : pathParam.entrySet()) {
				if (size == 1) {
					sb.append(map.getKey()).append("=").append(map.getValue());
					break;
				} else if (num < size) {
					sb.append(map.getKey()).append("=").append(map.getValue()).append("&");
					num++;
				} else if (num == size) {
					sb.append(map.getKey()).append("=").append(map.getValue());
				}
			}
		}
		String reqUrl = url + "?" + sb.toString();
		HttpGet httpGet = new HttpGet(reqUrl);

		if (header != null && !header.isEmpty()) {
			for (Map.Entry<String, String> map : header.entrySet()) {
				httpGet.addHeader(map.getKey(), map.getValue());
			}
		}

//		long startTime = System.currentTimeMillis();
		CloseableHttpResponse response = httpClient.execute(httpGet);
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity resEntity = response.getEntity();
			String resStr = EntityUtils.toString(resEntity, "UTF-8");
//			long endTime = System.currentTimeMillis();
//			long interval = endTime - startTime;
//			logger.info("执行请求：\"" + httpGet.getURI() + "\" 执行时间是" + interval + "豪秒");
//			if (interval > Long.valueOf(ProjectProperties.TIMEOUT)*1000) {
//				logger.error("请求:" + httpGet.getURI() + " 超过"+ProjectProperties.TIMEOUT+"秒");
//				Assertion.assertCustom(false);
//			}
			responseStr = Unicode2String.convertUnicode(resStr);
			System.out.println(responseStr);
		}
		response.close();
		httpClient.close();
	}

	public static String getResponse() {
		return responseStr;
	}
	
	
//	@SuppressWarnings("rawtypes")
//	public static String dealParamReplace(String jsonStr) throws Exception{
//		Set<String> paramSet = getReplaceParam(jsonStr);
//
//		for (String str2 : paramSet) {
//			
//			String value=DataTransfer.getData(str2);
//			jsonStr = jsonStr.replaceAll("\\$#" + str2 + "#", value);
//		}
//		return jsonStr;
//	}
//	
//	public static Set<String> getReplaceParam(String jsonStr){
//		Set<String> set = new HashSet<String>();
//		int index = 0;
//		int cur = 0;
//		int begin = 0;
//		String keyStr = "";
//
//		while ((cur = jsonStr.indexOf("$#", index)) != -1) {
//			index = cur + 2;// 跳过"${"两个字符
//			if ((cur = jsonStr.indexOf("#", index)) != -1) {
//				begin = index;
//				keyStr = jsonStr.substring(begin, cur);
//				set.add(keyStr.trim());
//				index = cur + 1;
//			}
//		}		
//		return set;
//	}


	public static void main(String[] args) throws IOException {
		String url="http://localhost:8085/user/getusers";
		Map<String,String> params=new HashMap<String,String>();
		params.put("role","Manager");
		Map<String,String> header=new HashMap<String,String>();
		header.put("Content-Type","application/json");
		getTypeRequestMethod(url,header,params);
		System.out.println(responseStr);
	}


}
