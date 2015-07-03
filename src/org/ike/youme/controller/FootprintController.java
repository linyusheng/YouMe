package org.ike.youme.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Footprint;
import org.ike.youme.entity.Photo;
import org.ike.youme.entity.User;
import org.ike.youme.model.EFootprint;
import org.ike.youme.service.FootprintService;
import org.ike.youme.service.PhotoService;
import org.ike.youme.util.Page;
import org.ike.youme.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 足迹访问控制类(类资源访问：/footprint/*)
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/footprint")
public class FootprintController {
	
	@Autowired
	private FootprintService footprintService;
	@Autowired
	private PhotoService photoService;
	
	/**
	 * 添加足迹
	 * @param footprint
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, String> add(String footprint, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		EFootprint e = JSON.parseObject(footprint, EFootprint.class);
		Integer footprintId = (Integer)footprintService.save(copyObject(e));
		if (footprintId == null) {
			map.put("status", "fail");
		}
		Footprint f = footprintService.get(footprintId);
		List<String> photos = savePhoto(request);
		for (String p : photos) {
			Photo photo = new Photo();
			photo.setFootprint(f);
			photo.setPhotoUrl(p);
			photoService.save(photo);
		}
		map.put("status", "success");
		return map;
	}
	/**
	 * 复制对象属性
	 * 
	 * @param e
	 * 
	 * @return
	 */
	public Footprint copyObject(EFootprint e){
		Footprint f = new Footprint();
		User user = new User();
		Activity activity = new Activity();
		user.setUserId(e.getUserId());
		activity.setActivityId(e.getActivityId());
		f.setUser(user);
		f.setActivity(activity);
		f.setMood(e.getMood());
		f.setCreateTime(Tool.getCurrentTime());
		return f;
	}
	/**
	 * 保存照片
	 * 
	 * @param photoStream
	 * 
	 * @return
	 */
	public List<String> savePhoto(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> multipartFiles = multipartRequest.getFiles("files");
		String realPath = request.getSession().getServletContext().getRealPath("/images/photo/");
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		List<String> photos = new ArrayList<String>();
		for (MultipartFile multipartFile : multipartFiles) {
			//获取文件的后缀
			String suffix = multipartFile.getOriginalFilename().substring(
					multipartFile.getOriginalFilename().lastIndexOf("."));
	        // 使用UUID生成文件名称
	        String fileName = UUID.randomUUID().toString() + suffix;
	        file = new File(realPath + File.separator + fileName);
	        try {
				multipartFile.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        photos.add("/images/photo/"+fileName);
		}
        return photos;
	}
	/**
	 * 查找本活动的足迹(分页查找)
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMore")
	public List<EFootprint> getMore(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer activityId = (Integer)object.get("activityId");
		Integer footprintId = (Integer)object.get("footprintId");
		List<Footprint> list = footprintService.findMore(activityId,footprintId,new Page());
		return footprintService.copyList(list);
	}
	/**
	 * 加载最新的足迹
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getNewest")
	public List<EFootprint> getNewest(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer activityId = (Integer)object.get("activityId");
		Integer footprintId = (Integer)object.get("footprintId");
		List<Footprint> list = footprintService.findNewest(activityId, footprintId);
		return footprintService.copyList(list);
	}
	/**
	 * 根据足迹ID删除足迹
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer footprintId = (Integer)object.get("footprintId");
		Footprint footprint = footprintService.get(footprintId);
		Set<Photo> photos = footprint.getPhotos();
		for (Photo photo : photos) {
			String path = System.getProperty("web.root") + photo.getPhotoUrl();
			Tool.deleteFile(path);
		}
		footprintService.delete(footprintId);
		return "删除成功！";
	}


}
