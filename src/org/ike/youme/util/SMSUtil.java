package org.ike.youme.util;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 发送信息工具类（使用云之讯开放平台）
 * 
 * @author lys
 *
 */
public class SMSUtil {
	
	//主账号Id
	private static String SID = "f5dd168b3159aa965768947ad3e40c64";
	//账户授权令牌
	private static String TOKEN = "62fd641f97a47086abcd896371fc4da6";
	//应用Id
	private static String APPID = "7d5b724139c24e73a517809caa37edb5";
	//短信模板Id
	private static String TEMPLATEID = "7439";
	
	/**
	 * 发送短信验证码
	 * @param to
	 * @param param
	 * @return
	 */
	public static boolean sendCode(String to, String param) {
		//时间戳yyyyMMddHHmmssSSS，有效时间为30分钟
		Format f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String time = f.format(date);
		//验证信息，使用MD5加密（账户id+时间戳+账户授权令牌），共32位（小写）
		String sign = MD5.string2MD5(SID + time + TOKEN);
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://www.ucpaas.com/maap/sms/code");
		// 填入各个表单域的值
		NameValuePair[] data = {
				new NameValuePair("sid",SID),
				new NameValuePair("appId", APPID),
				new NameValuePair("templateId", TEMPLATEID),
				new NameValuePair("time", time),
				new NameValuePair("sign", sign),
				new NameValuePair("to", to),
				new NameValuePair("param", param)
		};
		post.setRequestBody(data);
		try {
			// 执行postMethod,返回状态码
			int statusCode = client.executeMethod(post);
			//读取内容
			if (statusCode == HttpStatus.SC_OK) {
				String responseBody = post.getResponseBodyAsString();
				JSONObject object = JSON.parseObject(responseBody);
				String temp = object.get("resp").toString();
				object  = JSON.parseObject(temp);
				temp = object.get("respCode").toString();
				if (temp.equals("000000")) {
					return true;
				}
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			//释放连接
			post.releaseConnection();
		}
		return false;
	}
	
	public static void main(String[] args) {
		//SMSUtil.sendCode("18316022547", "YouMe,1234,2");
		
	}
}
