package org.ike.youme.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.entity.Comment;
import org.ike.youme.model.EComment;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.CommentService;
import org.ike.youme.service.UserService;
import org.ike.youme.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 活动评论访问控制类(类资源访问：/comment/*)
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 发表评论
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(String jsonString){
		EComment e = JSON.parseObject(jsonString,EComment.class);
		Comment comment = new Comment();
		comment.setUser(userService.get(e.getUserId()));
		comment.setActivity(activityService.get(e.getActivityId()));
		comment.setContent(e.getContent());
		comment.setCreateTime(Tool.getCurrentTime());
		Integer commentId = (Integer)commentService.save(comment);
		Map<String, Object> map = new HashMap<String, Object>();
		if (commentId != null) {
			map.put("status", "success");
			comment = commentService.get(commentId);
			map.put("comment", commentService.copyObject(comment));
		}else {
			map.put("status", "fail");
		}
		return map;
	}
	/**
	 * 刷新评论
	 * @param jsonString
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getNewest")
	public List<EComment> getNewest(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer activityId = (Integer)object.get("activityId");
		Integer commentId = (Integer)object.get("commentId");
		return commentService.getNewest(activityId, commentId);
	}
	/**
	 * 加载更多评论
	 * @param jsonString
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMore")
	public List<EComment> getMore(String jsonString) {
		JSONObject object = JSON.parseObject(jsonString);
		Integer activityId = (Integer)object.get("activityId");
		Integer commentId = (Integer)object.get("commentId");
		return commentService.getMore(activityId, commentId);
	}

}
