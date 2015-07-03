package org.ike.youme.controller;

import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Attend;
import org.ike.youme.entity.User;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.AttendService;
import org.ike.youme.service.BrowseService;
import org.ike.youme.service.CollectService;
import org.ike.youme.service.CommentService;
import org.ike.youme.service.FootprintService;
import org.ike.youme.service.PhotoService;
import org.ike.youme.service.UserService;
import org.ike.youme.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试访问控制类(类资源访问：/test/*)
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/test")
public class TestController {
	
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
	
	@ResponseBody
	@RequestMapping("/addUser")
	public void addUser() {
		for (int i = 0; i < 99; i++) {
			User user = new User();
			if (i< 10) {
				user.setAccount("1831602260"+i);
			}else {
				user.setAccount("183160226"+i);
			}
			user.setPwd("123456");
			user.setHead("/images/head/default.jpg");
			user.setAge(0);
			user.setSex("男");
			user.setNickname("呵呵");
			user.setSign("他很懒，什么也没留下~");
			user.setCity("韶关");
			user.setCreateTime(Tool.getCurrentTime());
			userService.save(user);
		}
	}
	@ResponseBody
	@RequestMapping("/addAttend")
	public void addAttend() {
		for (int i = 0; i < 50; i++) {
			Attend attend = new Attend();
			Activity activity = new Activity();
			activity.setActivityId(1);
			User user = new User();
			user.setUserId(i+1);
			attend.setActivity(activity);
			attend.setUser(user);
			attendService.save(attend);
		}
	}
}
