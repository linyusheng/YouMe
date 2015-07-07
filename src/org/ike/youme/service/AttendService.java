package org.ike.youme.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.entity.Attend;
import org.ike.youme.model.EUser;
import org.ike.youme.dao.AttendDAO;
import org.ike.youme.util.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户参与活动业务处理类
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Service
public class AttendService implements BaseService<Attend> {
	
	@Autowired
	private AttendDAO attendDAO;
	
	@Override
	public Attend get(Integer id) {
		return attendDAO.get(Attend.class, id);
	}
	@Override
	public Serializable save(Attend o) {
		return attendDAO.save(o);
	}
	@Override
	public void update(Attend o) {
		attendDAO.update(o);
	}
	@Override
	public void delete(Integer id) {
		attendDAO.delete(get(id));
		
	}
	@Override
	public List<Attend> findAll() {
		return attendDAO.find("from Attend");
	}
	/**
	 * 根据用户Id和活动Id查找参与记录
	 * 
	 * @param userId
	 * 
	 * @param activityId
	 * 
	 * @return
	 */
	public Attend get(Integer userId,Integer activityId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("activityId", activityId);
		String hql = "from Attend where user.userId = :userId and activity.activityId = :activityId";
		return attendDAO.get(hql, params);
	}
	/**
	 * 根据用户Id查找参与记录
	 * 
	 * @param userId
	 * 
	 * @return
	 */
	public List<Attend> getAttend(Integer userId,Integer activityId,Page page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String hql;
		if (activityId == null) {
			hql = "from Attend where user.userId = :userId order by activity.activityId desc";
		}else {
			hql = "from Attend where user.userId = :userId and activity.activityId < :activityId order by activity.activityId desc";
			params.put("activityId", activityId);
		}
		return attendDAO.find(hql, params, page.getCurrentPage(), page.getPageSize());
	}
	/**
	 * 根据活动Id查找参与记录,可根据userId是否为空分页查找
	 * @param activityId
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<EUser> find(Integer activityId,Integer userId,Page page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		String hql;
		if (userId == null) {
			hql = "from Attend where activity.activityId = :activityId order by attendId desc";
		}else {
			params.put("attendId", get(userId, activityId).getAttendId());
			hql = "from Attend where activity.activityId = :activityId and attendId < :attendId order by attendId desc";
		}
		List<Attend> list = attendDAO.find(hql, params,page.getCurrentPage(),page.getPageSize()); 
		return getEUser(list);
	}
	/**
	 * 从参与集合中抽取参与者 
	 * @param list
	 * @return
	 */
	public List<EUser> getEUser(List<Attend> list) {
		List<EUser> eList = new ArrayList<EUser>();
		for (Attend attend : list) {
			EUser eUser = new EUser();
			BeanUtils.copyProperties(attend.getUser(), eUser);
			eList.add(eUser);
		}
		return eList;
	}
	
	

}
