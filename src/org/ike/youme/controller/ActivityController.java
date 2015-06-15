package org.ike.youme.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.ike.youme.entity.Type;
import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Attend;
import org.ike.youme.entity.Footprint;
import org.ike.youme.entity.Photo;
import org.ike.youme.entity.User;
import org.ike.youme.model.EActivity;
import org.ike.youme.model.EType;
import org.ike.youme.model.EUser;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.AttendService;
import org.ike.youme.service.FootprintService;
import org.ike.youme.service.PhotoService;
import org.ike.youme.service.UserService;
import org.ike.youme.util.Page;
import org.ike.youme.util.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 活动访问控制类(类资源访问：/activity/*)
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;
	@Autowired
	private AttendService attendService;
	@Autowired
	private UserService userService;
	@Autowired
	private FootprintService footprintService;
	@Autowired
	private PhotoService photoService;
	
	/**
	 * 发布活动
	 * 
	 * @param json
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public String add(String jsonString) {
		System.out.println("请求/activity/add：" + jsonString);
		EActivity e = JSON.parseObject(jsonString,EActivity.class);
		Activity activity = copyEntity(e);
		//保存活动对象
		Integer activityId = (Integer)activityService.save(activity);
		return "发布成功！";
	}
	/**
	 * 将实体EActivity还原回实体Activity
	 * 
	 * @param e
	 * 
	 * @return
	 */
	public Activity copyEntity(EActivity e) {
		Activity activity = new Activity();
		Type type = new Type();
		User user = new User();
		//忽略复制的属性
		String[] ignores = {"poster"};
		BeanUtils.copyProperties(e, activity,ignores);
		//设置系统默认属性
		user.setUserId(e.getUserId());
		type.setTypeId(e.getTypeId());
		activity.setUser(user);
		activity.setType(type);
		activity.setCreateTime(Tool.getCurrentTime());
		activity.setStartTime(Timestamp.valueOf(e.getStartTime()));
		activity.setEndTime(Timestamp.valueOf(e.getEndTime()));
		activity.setAttendNum(0);
		activity.setBrowseNum(0);
		//将海报字节数组输出到服务器指定目录下
		String poster = e.getPoster();
		//判断海报字节数组是否为空
		if (poster != null) {
			Map<String, Object> map = savePoster(poster);
			activity.setPoster((String)map.get("savePath"));
		}
		return activity;
	}
	/**
	 * 保存海报，将图片字节数组输出到服务器端存储
	 * 返回值	1、图片宽高比	2、保存路径 （/images/poster/当天日期/文件名）
	 * 
	 * @param poster
	 * 
	 * @return
	 */
	public Map<String, Object> savePoster(String poster) {
		String currentDate = Tool.getCurrentDate();
		String webRoot = System.getProperty("web.root");
		String saveDir = File.separatorChar + "images" + File.separatorChar + "poster" + File.separatorChar + currentDate + File.separatorChar;
		File file = new File(webRoot + saveDir);
		//如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		String fileName = UUID.randomUUID().toString() + ".jpg";
		float aspectRatio = Tool.stringToImage(poster, webRoot + saveDir + fileName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aspectRatio", aspectRatio);
		map.put("savePath", "/images/poster/" + currentDate + "/" + fileName);
		return map;
	}
	/**
	 * 加载更多活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listMore")
	public List<EActivity> listMore(String jsonString) {
		System.out.println("请求/activities/listMore：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		Integer activitiesId = (Integer)object.get("eActivitiesId");
		String city = (String)object.get("city");
		List<EType> typeList = JSONArray.parseArray(object.getString("typeList"),EType.class);
		Page page = new Page();
		List<Activity> list = activityService.findMore(activitiesId,city,typeList,page);
		List<EActivity> eList = activityService.copyList(list);
		return eList;
	}
	/**
	 * 加载最新的活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listNewest")
	public List<EActivity> listNewest(String jsonString) {
		System.out.println("请求/activities/listNewest：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		Integer activitiesId = (Integer)object.get("eActivitiesId");
		String city = (String)object.get("city");
		List<EType> typeList = JSONArray.parseArray(object.getString("typeList"),EType.class);
		List<Activity> list = activityService.findNewest(activitiesId,city,typeList);
		List<EActivity> eList = activityService.copyList(list);
		return eList;
	}
	/**
	 * 根据用户Id查找自己参与的活动（分页回传）
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listMyActivity")
	public List<EActivity> listMyActivity(String jsonString){
		System.out.println("请求/activity/listMyActivity：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("userId");
		Integer activityId = (Integer)object.get("activityId");
		List<Activity> list = new ArrayList<Activity>();
		Page page = new Page();
		List<Attend> attends = attendService.findByUserId(userId,activityId,page);
		for (Attend attend : attends) {
			list.add(attend.getActivity());
		}
		return activityService.copyList(list);
	}
	/**
	 * 根据userId或者openId查找自己参与的活动(一次性回传)
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findMyActivity")
	public List<EActivity> findMyActivity(String jsonString){
		System.out.println("请求/activity/findMyActivity：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("userId");
		List<Activity> list = new ArrayList<Activity>();
		List<Attend> attends = attendService.findByUserId(userId);
		for (Attend attend : attends) {
			list.add(attend.getActivity());
		}
		return activityService.copyList(list);
	}
	/**
	 * 根据活动Id判断是否存在活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isExistActivity")
	public Map<String, String> isExistActivity(String jsonString) {
		System.out.println("请求/activity/isExistActivity：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		Integer activityId = (Integer)object.get("activityId");
		Activity activity = activityService.get(activityId);
		Map<String, String> map = new HashMap<String, String>();
		if (activity == null) {
			map.put("status", "该活动已经取消！");
		}
		return map;
	}
	/**
	 * 根据活动Id查找活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/activityInfo")
	public Map<String, Object> activityInfo(String jsonString) {
		System.out.println("请求/activity/activityInfo：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("userId");
		Integer activityId = (Integer)object.get("activityId");
		Activity activity = activityService.get(activityId);
		//回传两个值，1：活动实体 2、活动参与人集合
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activity", activityService.copyObject(activity));
		map.put("userList", listAttender(activityId));
		map.put("isAttend", attendService.isAttend(userId, activityId)==null?false:true);
		return map;
	}
	/**
	 * 根据活动Id列出所有参与者(包括创建者)
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	public List<EUser> listAttender(Integer activityId) {
		List<Attend> list = attendService.findByActivityId(activityId);
		List<EUser> eList = new ArrayList<EUser>();
		for (Attend attend : list) {
			EUser eUser = new EUser();
			BeanUtils.copyProperties(attend.getUser(), eUser);
			eList.add(eUser);
		}
		return eList;
	}
	/**
	 * 根据活动Id删除活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, String> delete(String jsonString) {
		System.out.println("请求/activity/delete：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		Integer activityId = (Integer)object.get("activityId");
		//删除所有的参与记录和足迹
		Activity activity = activityService.get(activityId);
		Set<Attend> attends = activity.getAttends();
		Set<Footprint> footprints = activity.getFootprints();
		for (Footprint footprint : footprints) {
			//删除足迹的照片
			Set<Photo> photos = footprint.getPhotos();
			for (Photo photo : photos) {
				String url = System.getProperty("web.root") + photo.getPhotoUrl();
				Tool.deleteFile(url);
			}
		}
		//删除活动海报
		String path = System.getProperty("web.root") + activity.getPoster();
		Tool.deleteFile(path);
		activityService.delete(activityId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("status", "取消成功！");
		return map;
	}
	/**
	 * 查找所有活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listAll")
	public List<EActivity> listAll(String jsonString) {
		System.out.println("请求/activity/listAll：" + jsonString);
		List<Activity> list = activityService.findAll();
		return activityService.copyList(list);
	}

}
