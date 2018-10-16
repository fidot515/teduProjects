package com.jt.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired(required=false)
    private CloseableHttpClient httpClient;

    @Autowired(required=false)
    private RequestConfig requestConfig;

    
    /**
     * 1.实现HttpClientPost方法
     * 思考:
     * 		1.需要设定url参数
     * 		2.Map<String,String> 使用Map数据结构实现参数封装
     * 		3.设定字符集编码 utf-8
     * 		
     * 		难点:POST如何传递参数?????
     * 		Post请求将参数转化为二进制字节流信息进行数据传输.
     * 		一般form表单提交试用POST提交
     * 		回顾: get http://addUser?id=1&name=tom
     */
    public String doPost(String url,Map<String,String> params,
    		String charset){
    	
    	String result = null;
    	//1.判断字符集编码是否为null 如果参数为空则默认为UTF-8
    	if(StringUtils.isEmpty(charset)){
    		charset = "UTF-8";
    	}
    	
    	//2.获取请求对象的实体
    	HttpPost httpPost = new HttpPost(url);
    	httpPost.setConfig(requestConfig);
    	
    	try {
	    	//3.判断用户是否传递参数
	    	if(params != null){
	    		//将用户传入的数据Map封装到List集合中
	    		List<NameValuePair> parameters = new ArrayList<>();
	    		for (Map.Entry<String, String> entry: params.entrySet()) {
					BasicNameValuePair nameValuePair = 
					new BasicNameValuePair(entry.getKey(), entry.getValue());
					parameters.add(nameValuePair);
				}
	    		//实现参数封装
	    		UrlEncodedFormEntity formEntity = 
	    				new UrlEncodedFormEntity(parameters, charset);
	    		//将参数封装为formEntity进行数据传输
	    		httpPost.setEntity(formEntity);
	    	}
	    	
	    	//4.发起Post请求
	    	CloseableHttpResponse httpResponse = 
	    			httpClient.execute(httpPost);
	    	
	    	//5.判断请求结果是否正确
	    	if(httpResponse.getStatusLine().getStatusCode() == 200){
	    		//6.获取服务端回传数据
	    		result = EntityUtils.toString(httpResponse.getEntity(),charset);
	    	}
	    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    public String doPost(String url){
    	
    	return doPost(url, null, null);
    }
    
    public String doPost(String url,Map<String,String> params){
    	
    	return doPost(url, params, null);
    }
    
    
    /**
     * 实现get请求
     * 说明:
     * 	get请求中的参数是经过拼接形成的.
     * 	localhost:8091/addUser?id=1&name=tom
     * 1   www.jt.com?id=1&name=tom&
    		String basicUrl = url + "?";
    		for (Map.Entry<String, String> entry : params.entrySet()) {
    			basicUrl = basicUrl + entry.getKey() + "=" 
    			+ entry.getValue() + "&";
			}
    		url = basicUrl.substring(0, basicUrl.length()-1);*/
    public String doGet(String url,Map<String,String> params,
    		String charset){
    	String result = null;
    	
    	//1.判断字符集编码是否为空
    	if(StringUtils.isEmpty(charset)){
    		charset = "UTF-8";
    	}
    	
    	try {
	    	//2.判断是否有参数   url www.jt.com
	    	if(params !=null){
	    		//通过工具类的写法实现路径的自动的拼接
	    		URIBuilder builder = new URIBuilder(url);
	    		for (Map.Entry<String,String> entry : params.entrySet()) {
					
	    			builder.addParameter(entry.getKey(), entry.getValue());
				}
	    		//将路径进行拼接   addUser?id=1&name=tom
	    		url = builder.build().toString();
	    		System.out.println("获取请求路径:"+url);
	    	}
	 
	    	//定义请求类型
	    	HttpGet httpGet = new HttpGet(url);
	    	httpGet.setConfig(requestConfig);
	    	
	    	//发起请求
	    	CloseableHttpResponse httpResponse  = httpClient.execute(httpGet);
	    	
	    	if(httpResponse.getStatusLine().getStatusCode() == 200){
	    		
	    		result = EntityUtils.toString(httpResponse.getEntity(),charset);
	    	}
	    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    public String doGet(String url,Map<String,String> params){
    	
    	return doGet(url, params, null);
    }
    
    public String doGet(String url){
    	
    	return doGet(url,null,null);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
