package org.ike.youme.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.dao.CollectDAO;
import org.ike.youme.entity.Collect;
import org.ike.youme.util.Page;
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
		return collectDAO.get(Collect.class, id);
	}

	@Override
	public Serializable save(Collect o) {
		return collectDAO.save(o);
	}

	@Override
	public void update(Collect o) {
		collectDAO.update(o);
	}

	@Override
	public void delete(Integer id) {
		collectDAO.delete(get(id));
	}

	@Override
	public List<Collect> findAll() {
		return collectDAO.find("from Collect");
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
	/**
	 * 查看我的收藏活动
	 * @param userId
	 * @param activityId
	 * @param page
	 * @return
	 */
	public List<Collect> getCollect(Integer userId,Integer activityId,Page page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String hql;
		if (activityId == null) {
			hql = "from Collect where user.userId = :userId order by activity.activityId desc";
		}else {
			hql = "from Collect where user.userId = :userId and activity.activityId < :activityId order by activity.activityId desc";
			params.put("activityId", activityId);
		}
		return collectDAO.find(hql, params, page.getCurrentPage(), page.getPageSize());
	}

}
