package org.ike.youme.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Attend;
import org.ike.youme.entity.User;
import org.ike.youme.model.EUser;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.AttendService;
import org.ike.youme.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 参与活动访问控制类(类资源访问：/attend/*)
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/attend")
public class AttendController {
	
	@Autowired
	private AttendService attendService;
	
	@Autowired
	private ActivityService activitiesService;
	
	/**
	 * 用户参与或退出活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrdelete")
	public Map<String, String> addOrdelete(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("userId");
		Integer activityId = (Integer)object.get("activityId");
		Map<String, String> map = new HashMap<String, String>();
		//查找是否有参与记录
		Attend attend = attendService.get(userId, activityId);
		if (attend == null) {
			User user = new User();
			Activity activity = new Activity();
			user.setUserId(userId);
			activity.setActivityId(activityId);
			attend = new Attend(activity,user);
			attendService.save(attend);
			map.put("status", "exit");
		}else {
			System.out.println(attend);
			attendService.delete(attend.getAttendId());
			map.put("status", "attend");
		}
		return map;
	}
	/**
	 * 获取活动参与者，分页查询
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
	public List<EUser> get(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer activityId = (Integer)object.get("activityId");
		Integer userId = (Integer)object.get("userId");
		return attendService.find(activityId,userId,new Page(1, 12));
	}
	/**
	 * 判断某用户是否已参加某活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isAttend")
	public String isAttend(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("eUserId");
		Integer activityId = (Integer)object.get("activityId");
		Attend attend = attendService.get(userId, activityId);
		if (attend == null) {
			return "0";
		}
		return "1";
	}
	
	
	
	
	
	
	
	
	
	
	
}
