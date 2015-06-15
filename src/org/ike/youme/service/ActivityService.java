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
		BeanUtils.copyProperties(activity, e);
		e.setCreateTime(Tool.timestampToString(activity.getCreateTime()));//
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
	public List<Activity> findMore(Integer activityId,String city,List<EType> typeList,Page page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city", city);
		String hql = "";
		//如果为第一次浏览活动
		if (activityId == 0) {
			if (typeList.size() == 0) {
				hql = "from Activity where city = :city order by activityId desc";
			}else {
				hql = "from Activity where and city = :city and type.typeId in(" + typeSql(typeList) + ") order by activityId desc";
			}
		}else {
			if (typeList.size() == 0) {
				hql = "from Activity where city = :city and activityId < :activityId order by activityId desc";
			}else {
				hql = "from Activity where  city = :city and activityId < :activityId and type.typeId in(" + typeSql(typeList) + ") order by activityId desc";
			}
			params.put("activityId", activityId);
		}
		//System.out.println("浏览活动："+selectHql);
		return activityDAO.find(hql, params, page.getCurrentPage(), page.getPageSize());
	}
	/**
	 * 刷新活动
	 * @param activityId
	 * @param city
	 * @param typeList
	 * @return
	 */
	public List<Activity> findNewest(Integer activityId,String city,List<EType> typeList) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city", city);
		params.put("activityId", activityId);
		String hql = "";
		if (typeList.size() == 0) {
			hql = "from Activity where city = :city and activityId > :activityId order by activityId asc";
		}else {
			hql = "from Activity where city = :city and activityId > :activityId and type.typeId in(" + typeSql(typeList) + ") order by activityId asc";
		}
		//System.out.println("刷新活动："+selectHql);
		return activityDAO.find(hql, params);
	}
	/**
	 * 拼装活动类型条件sql
	 * @param typeList
	 * @return
	 */
	public String typeSql(List<EType> typeList) {
		StringBuilder number = new StringBuilder();
		for (int i = 0; i < typeList.size(); i++) {
			if (0 == i) {
				number.append(typeList.get(i).getTypeId());
				continue;
			}
			number.append("," + typeList.get(i).getTypeId());
		}
		return number.toString();
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
	
	
	
	

}
