
package org.ike.youme.controller;

import java.util.List;

import org.ike.youme.entity.User;
import org.ike.youme.model.EUser;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.AttendService;
import org.ike.youme.service.UserService;
import org.ike.youme.util.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 用户访问控制类(类资源访问：/user/*)
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private AttendService attendService;
	
	/**
	 * 用户注册
	 * 
	 * @param jsonString
	 */
	@ResponseBody
	@RequestMapping("/add")
	public EUser add(String jsonString) {
		System.out.println("请求/user/add：" + jsonString);
		EUser eUser = JSON.parseObject(jsonString, EUser.class);
		User user = new User();
		BeanUtils.copyProperties(eUser, user);
		user.setSign("他很懒，什么也没留下~");
		user.setCreateTime(Tool.getCurrentTime());
		String head = eUser.getHead();
		if (head != null) {
			user.setHead(Tool.downImages(eUser.getHead()));
		}
		Integer userId = (Integer)userService.save(user);
		if (userId != null) {
			user = userService.get(userId);
		}
		BeanUtils.copyProperties(user, eUser);
		return eUser;
	}
	/**
	 * 根据userId查找用户
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUser")
	public EUser getUser(String jsonString) {
		System.out.println("请求/user/getUser：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		Integer userId = (Integer)object.get("userId");
		User user = userService.get(userId);
		EUser eUser = new EUser();
		BeanUtils.copyProperties(user, eUser);
		return eUser;
	}
	/**
	 * 根据userId数组查找多个用户信息
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUsers")
	public List<EUser> getUsers(String jsonString) {
		System.out.println("请求/user/getUsers：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		List<Integer> ids = JSONArray.parseArray(object.getString("ids"),Integer.class);
		List<User> list = userService.getUsers(ids);
		return userService.copyList(list);
	}
	/**
	 * 查找所有用户
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listAll")
	public List<EUser> listAll(String jsonString) {
		//System.out.println("请求/user/listAll：" + jsonString);
		List<User> list = userService.findAll();
		return userService.copyList(list);
	}
	/**
	 * 根据账号、昵称模糊查找用户
	 * 
	 * @param jsonString
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/searchUser")
	public List<EUser> searchUser(String jsonString) {
		//System.out.println("请求/user/searchUser：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		String q = (String)object.get("q");
		List<User> list = userService.searchUser(q);
		return userService.copyList(list);
	}
	
	

}
