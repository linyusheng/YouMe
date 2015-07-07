package org.ike.youme.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ike.youme.dao.ActivityDAO;
import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Attend;
import org.ike.youme.entity.Footprint;
import org.ike.youme.model.EActivity;
import org.ike.youme.model.EType;
import org.ike.youme.util.Page;
import org.ike.youme.util.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 活动业务处理类
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Service
public class ActivityService implements BaseService<Activity> {
	
	@Autowired
	private ActivityDAO activityDAO;
	
	@Override
	public Activity get(Integer id) {
		return activityDAO.get(Activity.class, id);
	}
	@Override
	public Serializable save(Activity o) {
		return activityDAO.save(o);
	}
	@Override
	public void update(Activity o) {
		activityDAO.update(o);
	}
	@Override
	public void delete(Integer id) {
		activityDAO.delete(get(id));
	}
	@Override
	public List<Activity> findAll() {
		return activityDAO.find("from Activity");
	}
	/**
	 * 复制对象属性值
	 * 
	 * @param activity
	 * 
	 * @return
	 */
	public EActivity copyObject(Activity activity) {
		EActivity e = new EActivity();
		String[] ignore = {"createTime","startTime","endTime"};
		BeanUtils.copyProperties(activity, e, ignore);
		e.setCreateTime(Tool.timestampToString(activity.getCreateTime()));
		e.setStartTime(Tool.timestampToString(activity.getStartTime()));
		e.setEndTime(Tool.timestampToString(activity.getEndTime()));
		//获得活动的男女人数
		int[] sex = getSexNumber(activity);
		e.setMaleNum(sex[0]);
		e.setFemaleNum(sex[1]);
		//初始化活动创建者信息
		e.setUserId(activity.getUser().getUserId());
		e.setAccount(activity.getUser().getAccount());
		e.setNickname(activity.getUser().getNickname());
		e.setHead(activity.getUser().getHead());
		return e;
	}
	/**
	 * 复制list集合
	 * 
	 * @param list
	 * 
	 * @return
	 */
	public List<EActivity> copyList(List<Activity> list) {
		List<EActivity> eList = new ArrayList<EActivity>();
		for (Activity activities : list) {
			eList.add(copyObject(activities));
		}
		return eList;
	}
	/**
	 * 加载更多活动
	 * @param activityId
	 * @param city
	 * @param typeList
	 * @param page
	 * @return
	 */
	public List<Activity> findMore(Integer activityId, String typeName, String city, String time) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city", city);
		String hql = "from Activity where city = :city";
		if (activityId != 0) {
			params.put("activityId", activityId);
			hql += " and activityId < :activityId";
		}
		if (!typeName.equals("全部类型")) {
			params.put("typeName", typeName);
			hql += " and type.typeName = :typeName";
		}
		if (!time.equals("全部时段")) {
			hql += typeSql(time);
		}
		hql += " order by activityId desc";
		return activityDAO.find(hql, params, 1, 10);
	}
	/**
	 * 刷新活动
	 * @param activityId
	 * @param city
	 * @param typeList
	 * @return
	 */
	public List<Activity> findNewest(Integer activityId, String typeName, String city, String time) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city", city);
		params.put("activityId", activityId);
		String hql = "from Activity where city = :city and activityId > :activityId";
		if (!typeName.equals("全部类型")) {
			params.put("typeName", typeName);
			hql += " and type.typeName = :typeName";
		}
		if (!time.equals("全部时段")) {
			hql += typeSql(time);
		}
		hql += " order by activityId asc";
		return activityDAO.find(hql, params);
	}
	/**
	 * 搜索活动
	 * @param q
	 * @param activityId
	 * @param city
	 * @param typeList
	 * @param page
	 * @return
	 */
	public List<Activity> find(String q,Integer activityId, String typeName, String city, String time) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city", city);
		params.put("q", "%%"+q.trim()+"%%");
		String hql = "from Activity where city = :city and title like :q";
		if (activityId != 0) {
			params.put("activityId", activityId);
			hql += " and activityId < :activityId";
		}
		if (!typeName.equals("全部类型")) {
			params.put("typeName", typeName);
			hql += " and type.typeName = :typeName";
		}
		if (!time.equals("全部时段")) {
			hql += typeSql(time);
		}
		hql += " order by activityId desc";
		return activityDAO.find(hql, params, 1, 10);
	}
	/**
	 * 拼装时间类型条件sql
	 * @param typeList
	 * @return
	 */
	public String typeSql(String time) {
		String hql = "";
		if (time.equals("今天")) {
			hql = " and DATE_FORMAT(startTime,'%Y-%m-%d') = CURDATE()";
		}
		if (time.equals("明天")) {
			hql = " and DATE_FORMAT(startTime,'%Y-%m-%d') = '"+Tool.getTomorrowDate()+"'";
		}
		if (time.equals("本周")) {
			hql = " and DATE_FORMAT(startTime,'%Y-%m-%d') BETWEEN '"+Tool.getMondayOFWeek()+"' AND '"+Tool.getSundayOFWeek()+"'";
		}
		if (time.equals("本周末")) {
			hql = " and DATE_FORMAT(startTime,'%Y-%m-%d') BETWEEN '"+Tool.getSaturdayOFWeek()+"' AND '"+Tool.getSundayOFWeek()+"'";
		}
		if (time.equals("本月")) {
			hql = " and DATE_FORMAT(startTime,'%Y-%m-%d') BETWEEN '"+Tool.getFirstOFMonth()+"' AND '"+Tool.getLastOFMonth()+"'";
		}
		return hql;
	}
	/**
	 * 获得活动总人数
	 * @param activity
	 * @return
	 */
	public Integer getTotalNumber(Activity activity) {
		return activity.getAttends().size();
	}
	/**
	 * 获得活动男女人数
	 * @param activity
	 * @return
	 */
	public int[] getSexNumber(Activity activity) {
		int[] sex = new int[2];
		Set<Attend> attends = activity.getAttends();
		for (Attend attend : attends) {
			if (attend.getUser().getSex().equals("男")) {
				sex[0]++;
			}else {
				sex[1]++;
			}
		}
		return sex;
	}
	/**
	 * 获取10条公益活动
	 * 
	 * @return
	 */
	public List<Activity> getPublic() {
		String hql = "from Activity where type.typeName = '公益' order by activityId asc";
		return activityDAO.find(hql,1,10);
	}
	/**
	 * 获取10条最新活动
	 * 
	 * @return
	 */
	public List<Activity> getNewest() {
		String hql = "from Activity order by activityId asc";
		return activityDAO.find(hql,1,10);
	}
	/**
	 * 获取10条最热活动
	 * 
	 * @return
	 */
	public List<Activity> getHottest() {
		String hql = "from Activity order by browseNum desc";
		return activityDAO.find(hql, 1, 10);
	}
	/**
	 * 查找用户发布的活动
	 * @param userId
	 * @param footprintId
	 * @param page
	 * @return
	 */
	public List<Activity> getPublish(Integer userId,Integer activityId,Page page) {
		String hql;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		if (activityId == null) {
			hql = "from Activity where user.userId = :userId order by activityId desc";
		}else {
			hql = "from Activity where user.userId = :userId and activityId < :activityId order by activityId desc";
			params.put("activityId", activityId);
		}
		return activityDAO.find(hql, params, page.getCurrentPage(), page.getPageSize());
	}
	
}

