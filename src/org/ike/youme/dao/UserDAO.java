package org.ike.youme.dao;

import org.ike.youme.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends BaseDAO<User> {
	
	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
	
}