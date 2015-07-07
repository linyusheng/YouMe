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

import org.ike.youme.entity.Browse;
import org.ike.youme.entity.Type;
import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Footprint;
import org.ike.youme.entity.Photo;
import org.ike.youme.entity.User;
import org.ike.youme.model.EActivity;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.AttendService;
import org.ike.youme.service.BrowseService;
import org.ike.youme.service.CollectService;
import org.ike.youme.service.CommentService;
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
	@Autowired
	private CollectService collectService;
	@Autowired
	private BrowseService browseService;
	@Autowired
	private CommentService commentService;
	
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
		Activity a = copyObject(e);
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
	 * 复制对象属性
	 * 
	 * @param e
	 * 
	 * @return
	 */
	public Activity copyObject(EActivity e) {
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
	 * 查找用户发布的活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPublish")
	public List<EActivity> getPublish(String jsonString){
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("userId");
		Integer activityId = (Integer)object.get("activityId");
		List<Activity> list = activityService.getPublish(userId, activityId, new Page());
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
	@RequestMapping("/getInfo")
	public Map<String, Object> getInfo(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("userId");
		Integer activityId = (Integer)object.get("activityId");
		Activity activity = activityService.get(activityId);
		//用户是否浏览了该活动
		if (browseService.get(userId, activityId)==null) {
			Browse browse = new Browse();
			User user = new User();
			user.setUserId(userId);
			browse.setActivity(activity);
			browse.setUser(user);
			browseService.save(browse);
		}
		//返回值：1、活动实体 2、活动参与人集合 3、活动评论 4、是否已参加5、是否已收藏
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activity", activityService.copyObject(activity));
		map.put("users", attendService.find(activityId,null,new Page(1, 6)));
		map.put("comments", commentService.getMore(activityId,null));
		map.put("isAttend", attendService.get(userId, activityId)==null?false:true);
		map.put("isCollect", collectService.get(userId, activityId)==null?false:true);
		return map;
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
	public List<EActivity> getPublic() {
		List<Activity> list = activityService.getPublic();
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
	public List<EActivity> getNewest() {
		List<Activity> list = activityService.getNewest();
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
	public List<EActivity> getHottest() {
		List<Activity> list = activityService.getHottest();
		return activityService.copyList(list);
	}
	/**
	 * 加载最新的活动
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listNewest")
	public List<EActivity> listNewest(String typeName,String activityId,String city,String time) {
		System.out.println(typeName+activityId+city+time);
		List<Activity> list = activityService.findNewest(Integer.parseInt(activityId), typeName, city, time);
		return activityService.copyList(list);
	}
	/**
	 * 加载更多活动
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listMore")
	public List<EActivity> listMore(String typeName,String activityId,String city,String time) {
		System.out.println(typeName+activityId+city+time);
		List<Activity> list = activityService.findMore(Integer.parseInt(activityId), typeName, city, time);
		return activityService.copyList(list);
	}
	/**
	 * 搜索活动
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/find")
	public List<EActivity> find(String q,String typeName,String activityId,String city,String time) {
		System.out.println(q+typeName+activityId+city+time);
		List<Activity> list = activityService.find(q,Integer.parseInt(activityId), typeName, city, time);
		return activityService.copyList(list);
	}

}
