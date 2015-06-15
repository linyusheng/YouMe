package org.ike.youme.dao;

import org.ike.youme.entity.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAO extends BaseDAO<Comment> {
	
	private static final Logger log = LoggerFactory.getLogger(CommentDAO.class);
	
}