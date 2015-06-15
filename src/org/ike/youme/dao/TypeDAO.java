package org.ike.youme.dao;

import org.ike.youme.entity.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TypeDAO extends BaseDAO<Type> {
	
	private static final Logger log = LoggerFactory.getLogger(TypeDAO.class);
	
}