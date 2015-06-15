package org.ike.youme.dao;

import org.ike.youme.entity.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoDAO extends BaseDAO<Photo> {
	
	private static final Logger log = LoggerFactory.getLogger(PhotoDAO.class);
	
}