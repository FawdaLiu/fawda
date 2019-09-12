//package com.lcc.liu.util;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletContext;
//
//import com.risen.hp.fastjson.JSON;
//import com.risen.hp.fastjson.JSONObject;
//import com.risen.hp.sfw.util.Assert;
//import com.risen.hp.sfw.util.CollectionUtils;
//import com.risen.hp.sfw.util.StringUtils;
//import com.risen.hp.test.TestGen;
//
//public class NewSeeTokenValidate {
//
//	public List retrievePrincipal(ServletContext envCtx) {
//		String token = (String)envCtx.getAttribute("token");
//		if(StringUtils.hasText(token)){
//			String postBody = buildPostBody(token);
//			//String url = "http://114.55.24.44:8099/ns-face-sys/rest/system";
//			//String url = "http://www6.zjlcwg.com:8099/ns-face-sys/service";
//			String url = "http://10.0.10.19:8099/ns-face-sys/service";
//
//			ConnRequest connRequest = new ConnRequest(url, ConnRequest.POST);
//			connRequest.requestBody(postBody);
//			connRequest.getRequestHeaders().put("Content-Type", "application/json");
//			connRequest.getRequestHeaders().put("Charset", "UTF-8");
//			connRequest.getRequestHeaders().put("Accept", "application/json");
//
//			JSONObject result = null;
//			String responseBody = null;
//			try {
//				connRequest.request();
//				responseBody = connRequest.getResponseBody();
//				//String responseBody = "{\"Response\":{\"Data\":{\"NWErrMsg\":\"验证成功\",\"NWRespCode\":\"100\" ,\"Record\":{\"account\":\"admin\",\"userName\":\"管理员LC\"}},\"Head\":{\"NWCode\":\"SYS_validateToken\",\"NWExID\":\"\",\"NWGUID\":\"2010072115220907818261\",\"NWVersion\":\"01\"}}}";
//				//{"Response":{"Data":{"NWErrMsg":"验证成功","NWRespCode":"100","Record":{"account":"1560","userName":"邱明峰"}},"Head":{"NWCode":"SYS_validateToken","NWExID":"","NWGUID":"2017052716435612736825","NWVersion":"01"}}}
//
//				Assert.isTrue(responseBody.indexOf("@type")<0, "当前的返回结果不安全");
//				result = JSON.parseObject(responseBody);
//			} catch (Exception e) {
//
//			}
//			JSONObject response = result!=null ? result.getJSONObject("Response") : null;
//			if(response!=null){
//				JSONObject data = response.getJSONObject("Data");
//				if(data!=null){
//					String code = data.getString("NWRespCode");
//					JSONObject record = data.getJSONObject("Record");
//					if("100".equals(code) && record!=null){
//						String account = record.getString("account");
//						if(StringUtils.hasText(account)){
//							List<String> fields = Arrays.asList("cractCode", "cractName");
//							String loginFields = "cractEmail";
//							if(StringUtils.hasText(loginFields)){
//								fields = MyUtils.CC(loginFields);
//							}
//							List<String> accountsFromDb = new ArrayList<String>();
//							if(CollectionUtils.isEmpty(accountsFromDb)){
//							    System.out.println("统一用户提供的账号[%s],在档案系统中不存在:" + responseBody + "" + account);
//							}
//							return accountsFromDb;
//						} else {
//						    System.out.println("从返回数据无法提取account属性:" + responseBody);
//						}
//					} else {
//					    System.out.println("从返回数据提取状态代码[%s]:" + responseBody + ";" + code);
//					}
//				} else {
//				    System.out.println("从返回数据无法提取data属性:" + responseBody);
//				}
//			}
//			return Collections.emptyList();
//		} else {
//			return new ArrayList<>();
//		}
//	}
//
//	protected String buildPostBody(String token) {
//		Map<String, Object> request = new LinkedHashMap<String, Object>(4);
//		Map<String, String> header = new LinkedHashMap<String, String>(8);
//		Map<String, String> data = new LinkedHashMap<String, String>(2);
//		header.put("NWVersion", "01");
//		header.put("NWCode", "SYS_validateToken");
//		header.put("NWGUID", DF.FMS.format(new Date())+TestGen.getNumberStr(5, 5));
//		//header.put("NWGUID", "2010072115220907818261");
//		header.put("NWExID", "");
//		data.put("token", token.replace(" ", "+"));
//		request.put("Head", header);
//		request.put("Data", data);
//		Map<String, Object> post = new LinkedHashMap<String, Object>(2);
//		post.put("Request", request);
//		String postBody = JSON.toJSONString(post);
//		return postBody;
//	}
//
//
//
//}
