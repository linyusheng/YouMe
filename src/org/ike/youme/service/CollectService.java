package org.ike.youme.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.dao.CollectDAO;
import org.ike.youme.entity.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 收藏活动业务处理类
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Service
public class CollectService implements BaseService<Collect> {
	
	@Autowired
	private CollectDAO collectDAO;

	@Override
	public Collect get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable save(Collect o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Collect o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Collect> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 根据用户Id和活动Id查找收藏记录
	 * 
	 * @param userId
	 * 
	 * @param activityId
	 * 
	 * @return
	 */
	public Collect get(Integer userId,Integer activityId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("activityId", activityId);
		String hql = "from Collect where user.userId = :userId and activity.activityId = :activityId";
		return collectDAO.get(hql, params);
	}

}
