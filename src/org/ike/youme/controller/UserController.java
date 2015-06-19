
package org.ike.youme.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.ike.youme.entity.User;
import org.ike.youme.model.EUser;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.AttendService;
import org.ike.youme.service.UserService;
import org.ike.youme.util.SMSUtil;
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
	 * 请求注册验证码
	 * 
	 * @param jsonString
	 */
	@ResponseBody
	@RequestMapping("/requestCode")
	public Map<String, String> requestCode(String jsonString) {
		System.out.println("请求/user/requestCode：" + jsonString);
		JSONObject object = JSON.parseObject(jsonString);
		String number = (String)object.get("number");
		//生成4位验证码
		Random random = new Random();
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			int n = random.nextInt(10);
			code.append(n);
		}
		Map<String, String> map = new HashMap<String, String>();
		//判断是否已注册
		User user = userService.getUser(number);
		if (user != null) {
			map.put("status", "fail");
			map.put("msg", "该账号已被注册！");
			return map;
		}
		boolean r = true;//SMSUtil.sendCode(number, "YouMe," + code.toString() + ",2");
		if (r) {
			map.put("status", "success");
			map.put("code", code.toString());
			return map;
		}
		map.put("status", "fail");
		map.put("msg", "验证码发送失败！");
		return map;
	}
	/**
	 * 用户注册
	 * 
	 * @param jsonString
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(String jsonString) {
		System.out.println("请求/user/add：" + jsonString);
		EUser eUser = JSON.parseObject(jsonString, EUser.class);
		User user = new User();
		BeanUtils.copyProperties(eUser, user);
		user.setNickname(eUser.getAccount());
		user.setSex("男");
		user.setSign("他很懒，什么也没留下~");
		user.setCreateTime(Tool.getCurrentTime());
		Integer userId =(Integer)userService.save(user);
		Map<String, Object> map = new HashMap<String, Object>();
		if (userId == null) {
			map.put("status", "fail");
		}else {
			user = userService.get(userId);
			BeanUtils.copyProperties(user, eUser,new String[]{"pwd"});
			map.put("status", "success");
			map.put("user", eUser);
		}
		return map;
	}
	/**
	 * 用户登陆
	 * 
	 * @param jsonString
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Map<String, Object> login(String jsonString) {
		System.out.println("请求/user/login：" + jsonString);
		EUser eUser = JSON.parseObject(jsonString, EUser.class);
		User user = userService.getUser(eUser.getAccount(), eUser.getPwd());
		Map<String, Object> map = new HashMap<String, Object>();
		if (user == null) {
			map.put("status", "fail");
		}else {
			map.put("status", "success");
			BeanUtils.copyProperties(user, eUser,new String[]{"pwd"});
			map.put("user", eUser);
		}
		return map;
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
