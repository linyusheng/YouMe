package org.ike.youme.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ike.youme.entity.User;
import org.ike.youme.dao.UserDAO;
import org.ike.youme.model.EUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务处理类
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Service
public class UserService implements BaseService<User> {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User get(Integer id) {
		return userDAO.get(User.class, id);
	}
	@Override
	public Serializable save(User o) {
		return userDAO.save(o);
	}
	@Override
	public void update(User o) {
		userDAO.update(o);
	}
	@Override
	public void delete(Integer id) {
		userDAO.delete(get(id));
	}
	@Override
	public List<User> findAll() {
		return userDAO.find("from User");
	}
	/**
	 * 根据账号查找用户
	 * 
	 * @param account
	 * 
	 * @return
	 */
	public User findByAccount(String account) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		String hql = "from User where account = :account";
		return userDAO.get(hql, params);
	}
	/**
	 * 复制list
	 * 
	 * @param list
	 * 
	 * @return
	 */
	public List<EUser> copyList(List<User> list) {
		List<EUser> eList = new ArrayList<EUser>();
		for (User user : list) {
			EUser eUser = new EUser();
			BeanUtils.copyProperties(user, eUser);
			eList.add(eUser);
		}
		return eList;
	}
	/**
	 * 根据userId数组查找多个用户
	 * 
	 * @param ids
	 * 
	 * @return
	 */
	public List<User> getUsers(List<Integer> ids) {
		//拼接参数
		StringBuilder number = new StringBuilder();
		for (int i = 0; i < ids.size(); i++) {
			if (0 == i) {
				number.append(ids.get(i));
				continue;
			}
			number.append("," + ids.get(i));
		}
		String hql = "from User where userId in(" + number.toString() + ")";
		return userDAO.find(hql);
	}
	/**
	 * 根据账号、昵称模糊查找用户
	 * 
	 * @param q
	 * 
	 * @return
	 */
	public List<User> searchUser(String q) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("q", "'%" + q + "%'");
		String hql = "from User where account like :q or nickname like :q";
		return userDAO.find(hql, params);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
