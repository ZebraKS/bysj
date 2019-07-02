package com.bysj.common.service;

import java.io.IOException;
import java.net.URISyntaxException;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

@Service
public class HttpClientService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

	@Autowired(required = false)
	private CloseableHttpClient httpClient;

	@Autowired(required = false)
	private RequestConfig requestConfig;
	
	/**
	 * 工具类开发的步骤： 
	 * 需求:根据url发起请求，最终获取响应的结果 
	 * 设计 
	 * 		参数设计:1.String url请求地址  
	 * 				 2.Map<String,String>数据类型 发送参数
	 * 				3.String charset 字符集编码
	 */
	
	/**
	 * 工具类开发的步骤： 
	 * 需求:根据url发起请求，最终获取响应的结果 
	 * 设计 
	 * 		参数设计:1.String url请求地址  
	 * 				 2.Map<String,String>数据类型 发送参数
	 * 				3.String charset 字符集编码
	 * @throws URISyntaxException 
	 */
	
	public String doGet(String url,Map<String,String> params,String charset){
		//1.判断字符集编码是否为空，空的话给默认值
		if(StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		//2.判断用户是否需要传递参数
		if(params != null) {
			try {
				URIBuilder uriBuilder = new URIBuilder(url);
				//遍历map集合获取参数
				for(Map.Entry<String, String> mp : params.entrySet()) {
					
					uriBuilder.addParameter(mp.getKey(), mp.getValue());
				}
				url = uriBuilder.build().toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		//3.定义参数提交对象
		HttpGet get = new HttpGet(url);
		//4.为请求设定超时时间
		get.setConfig(requestConfig);
		//5.通过httpClienet发送请求
		String result = null;
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			if(response.getStatusLine().getStatusCode() == 200) {
				//掉用成功 返回字符串
				result = EntityUtils.toString(response.getEntity(),charset);
				return result;
			}else {
				System.out.println("获取状态信息:掉用异常"+ response.getStatusLine().getStatusCode());
				throw new RuntimeException();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//添加异常处理类
		}
		return result;
	}
	
	public String doGet(String url) {
		return doGet(url,null,null);
	}
	
	public String doGet(String url,Map<String,String> params) {
		return doGet(url,params,null);
	}
	
	public String doGet(String url,String charset) {
		return doGet(url,null,charset);
	}
	
    /**
     * 执行POST请求
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map<String, String> params,String charset) throws Exception {
		//1.判断字符集编码是否为空，空的话给默认值
		if(StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
    	// 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        if (null != params) {
            // 设置2个post参数，一个是scope、一个是q
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = null;
            formEntity = new UrlEncodedFormEntity(parameters,charset);

            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
            	String aa = EntityUtils.toString(response.getEntity(), "UTF-8");
                return aa;
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
    
	public String doPost(String url) throws Exception {
		return doPost(url,null,null);
	}
	
	public String doPost(String url,Map<String,String> params) throws Exception {
		return doPost(url,params,null);
	}
	
	public String doPost(String url,String charset) throws Exception {
		return doPost(url,null,charset);
	}
}




