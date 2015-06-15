package org.ike.youme.dao;

import org.ike.youme.entity.Footprint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FootprintDAO extends BaseDAO<Footprint> {
	
	private static final Logger log = LoggerFactory.getLogger(FootprintDAO.class);
	
}