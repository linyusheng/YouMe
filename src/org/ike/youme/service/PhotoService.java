package org.ike.youme.service;

import java.io.Serializable;
import java.util.List;

import org.ike.youme.entity.Photo;
import org.ike.youme.dao.PhotoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 照片业务处理类
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Service
public class PhotoService implements BaseService<Photo> {
	
	@Autowired
	private PhotoDAO photoDAO;

	@Override
	public Photo get(Integer id) {
		return photoDAO.get(Photo.class, id);
	}

	@Override
	public Serializable save(Photo o) {
		return photoDAO.save(o);
	}

	@Override
	public void update(Photo o) {
		photoDAO.update(o);
	}

	@Override
	public void delete(Integer id) {
		photoDAO.delete(get(id));
	}
	@Override
	public List<Photo> findAll() {
		return photoDAO.find("from Photo");
	}

	
	
	
	
	
	
	
}
