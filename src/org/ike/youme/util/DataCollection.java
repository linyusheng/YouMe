package org.ike.youme.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataCollection {

	/**
	 * 根据图片的外网地址下载图片到本地的filePath
	 * 
	 * @param filePath
	 *            存放地址
	 * @param imgUrl
	 *            图片的外网地址
	 */
	public static void downImages(String filePath, String imgUrl) {
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/"));
		try {
			// 创建文件目录
			File files = new File(filePath);
			if (!files.exists()) {
				files.mkdirs();
			}
			// 获取下载地址
			URL url = new URL(imgUrl);
			// 链接网络地址
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 获取链接的输出流
			InputStream is = connection.getInputStream();
			// 创建文件
			File file = new File(filePath + fileName);
			// 根据输入流写入文件
			FileOutputStream out = new FileOutputStream(file);
			int i = 0;
			while ((i = is.read()) != -1) {
				out.write(i);
			}
			out.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
