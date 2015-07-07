package org.ike.youme.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.dao.CommentDAO;
import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Comment;
import org.ike.youme.model.EActivity;
import org.ike.youme.model.EComment;
import org.ike.youme.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 评论活动业务处理类
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Service
public class CommentService implements BaseService<Comment>{
	
	@Autowired
	private CommentDAO commentDAO;

	@Override
	public Comment get(Integer id) {
		return commentDAO.get(Comment.class, id);
	}

	@Override
	public Serializable save(Comment o) {
		return commentDAO.save(o);
	}

	@Override
	public void update(Comment o) {
		commentDAO.update(o);
	}

	@Override
	public void delete(Integer id) {
		commentDAO.delete(get(id));
	}

	@Override
	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 复制对象
	 * 
	 * @param comment
	 * 
	 * @return
	 */
	public EComment copyObject(Comment comment) {
		EComment e = new EComment();
		e.setCommentId(comment.getCommentId());
		e.setActivityId(comment.getActivity().getActivityId());
		e.setUserId(comment.getUser().getUserId());
		e.setContent(comment.getContent());
		e.setCreateTime(Tool.timestampToString(comment.getCreateTime()));
		e.setHead(comment.getUser().getHead());
		e.setNickname(comment.getUser().getNickname());
		return e;
	}
	/**
	 * 复制list集合
	 * 
	 * @param list
	 * 
	 * @return
	 */
	public List<EComment> copyList(List<Comment> list) {
		List<EComment> eList = new ArrayList<EComment>();
		for (Comment comment : list) {
			eList.add(copyObject(comment));
		}
		return eList;
	}
	/**
	 * 根据活动Id查找评论，刷新评论
	 * 
	 * @param activityId
	 * @param commentId
	 * 
	 * @return
	 */
	public List<EComment> getNewest(Integer activityId,Integer commentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		params.put("commentId", commentId);
		String hql = "from Comment where activity.activityId = :activityId and commentId > :commentId order by commentId desc";
		List<Comment> list = commentDAO.find(hql, params);
		return copyList(list);
	}
	/**
	 * 根据活动Id查找评论，可根据commentId是否为空分页查找
	 * 
	 * @param activityId
	 * @param commentId
	 * 
	 * @return
	 */
	public List<EComment> getMore(Integer activityId,Integer commentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		String hql;
		if (commentId == null) {
			hql = "from Comment where activity.activityId = :activityId order by commentId desc";
		}else {
			params.put("commentId", commentId);
			hql = "from Comment where activity.activityId = :activityId and commentId < :commentId order by commentId desc";
		}
		List<Comment> list = commentDAO.find(hql, params, 1, 10);
		return copyList(list);
	}
}
