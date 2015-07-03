package org.ike.youme.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.dao.BrowseDAO;
import org.ike.youme.entity.Browse;
import org.ike.youme.entity.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 浏览活动业务处理类
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Service
public class BrowseService implements BaseService<Browse>{
	
	@Autowired
	private BrowseDAO browseDAO;

	@Override
	public Browse get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable save(Browse o) {
		return browseDAO.save(o);
	}

	@Override
	public void update(Browse o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Browse> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 根据用户Id和活动Id查找浏览记录
	 * 
	 * @param userId
	 * 
	 * @param activityId
	 * 
	 * @return
	 */
	public Browse get(Integer userId,Integer activityId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("activityId", activityId);
		String hql = "from Browse where user.userId = :userId and activity.activityId = :activityId";
		return browseDAO.get(hql, params);
	}

}
