package org.ike.youme.controller;

import java.util.ArrayList;
import java.util.List;

import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Attend;
import org.ike.youme.entity.User;
import org.ike.youme.model.EUser;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.AttendService;
import org.springframework.beans.BeanUtils;
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
	public String addOrdelete(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("userId");
		Integer activityId = (Integer)object.get("activityId");
		String result = "";
		//查找是否有参与记录
		Attend attend = attendService.isAttend(userId, activityId);
		if (attend == null) {
			User user = new User();
			Activity activity = new Activity();
			user.setUserId(userId);
			activity.setActivityId(activityId);
			attend = new Attend(activity,user);
			attendService.save(attend);
			result = "exit";
		}else {
			attendService.delete(attend.getAttendId());
			result = "attend";
		}
		return result;
	}
	/**
	 * 根据活动Id列出所有参与者
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listAttender")
	public List<EUser> listAttender(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer activityId = (Integer)object.get("activityId");
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
		Attend attend = attendService.isAttend(userId, activityId);
		if (attend == null) {
			return "0";
		}
		return "1";
	}
	
	
	
	
	
	
	
	
	
	
	
}
