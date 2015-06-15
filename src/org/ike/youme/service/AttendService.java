package org.ike.youme.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.entity.Attend;
import org.ike.youme.dao.AttendDAO;
import org.ike.youme.util.Page;
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
	public Attend isAttend(Integer userId,Integer activityId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("activityId", activityId);
		String hql = "from Attend where user.userId = :userId and activity.activityId = :activityId";
		return attendDAO.get(hql, params);
	}
	/**
	 * 根据userId查找自己参与的活动(一次性查询)
	 * 
	 * @param userId
	 * 
	 * @return
	 */
	public List<Attend> findByUserId(Integer userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String hql = "from Attend where user.userId = :userId order by activity.activityId desc";
		return attendDAO.find(hql, params);
	}
	/**
	 * 根据用户Id查找参与记录(根据活动Id降序排序)
	 * 
	 * @param userId
	 * 
	 * @return
	 */
	public List<Attend> findByUserId(Integer userId,Integer activityId,Page page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String hql = "";
		//判断是否为第一次浏览活动
		if (activityId == 0) {
			hql = "from Attend where user.userId = :userId order by activity.activityId desc";
		}else {
			hql = "from Attend where user.userId = :userId and activity.activityId < :activityId order by activity.activityId desc";
			params.put("activityId", activityId);
		}
		//System.out.println("参与："+selectHql);
		return attendDAO.find(hql, params, page.getCurrentPage(), page.getPageSize());
	}
	/**
	 * 根据活动Id查找参与记录
	 * 
	 * @param activityId
	 * 
	 * @return
	 */
	public List<Attend> findByActivityId(Integer activityId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		String hql = "from Attend where activity.activityId = :activityId";
		return attendDAO.find(hql, params);
	}
	
	

}
