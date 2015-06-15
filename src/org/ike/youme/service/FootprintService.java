package org.ike.youme.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ike.youme.entity.Footprint;
import org.ike.youme.dao.FootprintDAO;
import org.ike.youme.entity.Photo;
import org.ike.youme.model.EFootprint;
import org.ike.youme.util.Page;
import org.ike.youme.util.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 足迹业务处理类
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Service
public class FootprintService implements BaseService<Footprint> {
	
	@Autowired
	private FootprintDAO footprintDAO;
	
	@Override
	public Footprint get(Integer id) {
		return footprintDAO.get(Footprint.class, id);
	}
	@Override
	public Serializable save(Footprint o) {
		return footprintDAO.save(o);
	}
	@Override
	public void update(Footprint o) {
		footprintDAO.update(o);
		
	}
	@Override
	public void delete(Integer id) {
		footprintDAO.delete(get(id));
	}
	@Override
	public List<Footprint> findAll() {
		return footprintDAO.find("from Footprint");
	}
	
	/**
	 * 复制list集合
	 * 
	 * @param list
	 * 
	 * @return
	 */
	public List<EFootprint> copyList(List<Footprint> list) {
		List<EFootprint> eList = new ArrayList<EFootprint>();
		for (Footprint f : list) {
			EFootprint e = new EFootprint();
			String[] ignore = {"createTime"};
			BeanUtils.copyProperties(f, e,ignore);
			e.setUserId(f.getUser().getUserId());
			e.setNickname(f.getUser().getNickname());
			e.setHead(f.getUser().getHead());
			e.setActivityId(f.getActivity().getActivityId());
			e.setCreateTime(Tool.timestampToString(f.getCreateTime()));
			//复制照片url集合到EFootPrint对象的photoStream属性
			List<String> photoUrls = new ArrayList<String>();
			Set<Photo> photos = f.getPhotos();
			for (Photo photo : photos) {
				photoUrls.add(photo.getPhotoUrl());
			}
			e.setPhotos(photoUrls);
			eList.add(e);
		}
		return eList;
	}
	/**
	 * 加载更多足迹
	 * 
	 * @param activityId
	 * 
	 * @return
	 */
	public List<Footprint> findMore(Integer activityId,Integer footprintId,Page page) {
		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		//判断是否为第一次浏览足迹
		if (footprintId == 0) {
			hql = "from Footprint where activity.activityId = :activityId order by footprintId desc";
		}else {
			hql = "from Footprint where activity.activityId = :activityId and footprintId < :footprintId order by footprintId desc";
			params.put("footprintId", footprintId);
		}
		//System.out.println("浏览足迹："+selectHql);
		return footprintDAO.find(hql, params, page.getCurrentPage(), page.getPageSize());
		
	}
	/**
	 * 刷新足迹
	 * @param activityId
	 * @param footprintId
	 * @return
	 */
	public List<Footprint> findNewest(Integer activityId,Integer footprintId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		params.put("footprintId", footprintId);
		String hql = "from Footprint where activity.activityId = :activityId and footprintId > :footprintId order by footprintId desc";
		//System.out.println("刷新足迹："+selectHql);
		return footprintDAO.find(hql, params);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
