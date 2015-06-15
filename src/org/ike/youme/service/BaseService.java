package org.ike.youme.service;

import java.io.Serializable;
import java.util.List;

/**
 * 基本业务处理接口
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
public interface BaseService<T> {
	/**
	 * 根据主键查找对象
	 * @param id
	 * @return
	 */
	public T get(Integer id);
	/**
	 * 保存对象，返回主键
	 * @param o
	 * @return
	 */
	public Serializable save(T o);
	/**
	 * 更新对象
	 * @param o
	 */
	public void update(T o);
	/**
	 * 根据主键删除对象
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 查找所有对象
	 * @return
	 */
	public List<T> findAll();
	
}
