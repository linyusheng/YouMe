package org.ike.youme.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Collect;
import org.ike.youme.entity.User;
import org.ike.youme.model.EActivity;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.CollectService;
import org.ike.youme.util.Page;
import org.ike.youme.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 活动访问控制类(类资源访问：/collect/*)
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/collect")
public class CollectController {
	
	@Autowired
	private CollectService collectService;
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 用户收藏或取消收藏活动
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
		//查找是否有收藏记录
		Collect collect = collectService.get(userId, activityId);
		if (collect == null) {
			User user = new User();
			Activity activity = new Activity();
			user.setUserId(userId);
			activity.setActivityId(activityId);
			collect = new Collect(activity,user,Tool.getCurrentTime());
			collectService.save(collect);
			map.put("status", "cancel");
		}else {
			collectService.delete(collect.getCollectId());
			map.put("status", "collect");
		}
		return map;
	}
	/**
	 * 根据用户Id查找自己收藏的活动
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCollect")
	public List<EActivity> getCollect(String jsonString){
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("userId");
		Integer activityId = (Integer)object.get("activityId");
		List<Activity> list = new ArrayList<Activity>();
		List<Collect> collects = collectService.getCollect(userId, activityId, new Page());
		for (Collect collect : collects) {
			list.add(collect.getActivity());
		}
		return activityService.copyList(list);
	}
	

}
