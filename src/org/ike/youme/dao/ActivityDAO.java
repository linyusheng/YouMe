package org.ike.youme.dao;

import org.ike.youme.entity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityDAO extends BaseDAO<Activity>{
	
	private static final Logger log = LoggerFactory.getLogger(ActivityDAO.class);
	
}