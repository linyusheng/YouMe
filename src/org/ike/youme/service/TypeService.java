package org.ike.youme.service;

import java.io.Serializable;
import java.util.List;

import org.ike.youme.entity.Type;
import org.ike.youme.dao.TypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 活动类型业务处理类
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Service
public class TypeService implements BaseService<Type> {
	
	@Autowired
	private TypeDAO typeDAO;
	
	@Override
	public Type get(Integer id) {
		return typeDAO.get(Type.class, id);
	}
	
	@Override
	public Serializable save(Type o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Type o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Type> findAll() {
		return typeDAO.find("from Type");
	}

	

}
