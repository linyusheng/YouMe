package org.ike.youme.controller;

import java.util.List;

import org.ike.youme.model.EComment;
import org.ike.youme.service.CommentService;
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
		return commentService.find(activityId, commentId);
	}

}
