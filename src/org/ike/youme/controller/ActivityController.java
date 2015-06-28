package org.ike.youme.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.ike.youme.entity.Type;
import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Attend;
import org.ike.youme.entity.Footprint;
import org.ike.youme.entity.Photo;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	public Map<String, String> add(String activity, HttpServletRequest request) {
		EActivity e = JSON.parseObject(activity,EActivity.class);
		Activity a = copyEntity(e);
        a.setPoster(savePoster(request));
        Map<String, String> map = new HashMap<String, String>();
		Integer activityId = (Integer)activityService.save(a);
		if (activityId == null) {
			map.put("status", "fail");
		}else {
			map.put("status", "success");
		}
		return map;
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
		//忽略复制的属性
		String[] ignores = {"activityId","poster","createTime","startTime","endTime","attendNum","browseNum"};
		BeanUtils.copyProperties(e, activity,ignores);
		//设置系统默认属性
		type.setTypeId(e.getTypeId());
		activity.setUser(userService.getUser(e.getAccount()));
		activity.setType(type);
		activity.setCreateTime(Tool.getCurrentTime());
		activity.setStartTime(Timestamp.valueOf(e.getStartTime()));
		activity.setEndTime(Timestamp.valueOf(e.getEndTime()));
		activity.setAttendNum(0);
		activity.setBrowseNum(0);
		return activity;
	}
	/**
	 * 保存海报
	 * 
	 * @param poster
	 * 
	 * @return
	 */
	public String savePoster(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		String realPath = request.getSession().getServletContext().getRealPath("/images/poster/");
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		//获取文件的后缀
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
        // 使用UUID生成文件名称
        String fileName = UUID.randomUUID().toString() + suffix;
        //完整路径
        realPath += File.separator + fileName;
        file = new File(realPath);
        try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "/images/poster/"+fileName;
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
	public List<EActivity> listNewest(String typeId,String activityId,String city) {
		Integer _typeId = Integer.parseInt(typeId);
		Integer _activityId = Integer.parseInt(activityId);
		List<Activity> list = activityService.findNewest(_typeId, _activityId, city);
		List<EActivity> eList = activityService.copyList(list);
		return eList;
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
	 * 根据用户Id查找自己参与的活动（分页回传）
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listMyActivity")
	public List<EActivity> listMyActivity(String jsonString){
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
		JSONObject object = JSON.parseObject(jsonString);
		Integer activityId = (Integer)object.get("activityId");
		//删除所有的参与记录和足迹
		Activity activity = activityService.get(activityId);
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
	public List<EActivity> listAll() {
		List<Activity> list = activityService.findAll();
		return activityService.copyList(list);
	}
	/**
	 * 获取公益活动（推荐）
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPublic")
	public List<EActivity> getPublic(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		String city = (String)object.get("city");
		List<Activity> list = activityService.getPublic(city);
		return activityService.copyList(list);
	}
	/**
	 * 获取最新活动（推荐）
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getNewest")
	public List<EActivity> getNewest(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		String city = (String)object.get("city");
		List<Activity> list = activityService.getNewest(city);
		return activityService.copyList(list);
	}
	/**
	 * 获取最热活动（推荐）
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getHottest")
	public List<EActivity> getHottest(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		String city = (String)object.get("city");
		List<Activity> list = activityService.getHottest(city);
		return activityService.copyList(list);
	}

}
