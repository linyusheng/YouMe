package org.ike.youme.controller;

import java.util.ArrayList;
import java.util.List;

import org.ike.youme.entity.Type;
import org.ike.youme.model.EType;
import org.ike.youme.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 活动类型访问控制类(类资源访问：/type/*)
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/type")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	/**
	 * 查找出所有活动类型名
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listAll")
	public List<EType> listAll() {
		//System.out.println("请求/activitiesType/listAll");
		List<Type> list = typeService.findAll();
		List<EType> eList = new ArrayList<EType>();
		for (Type Type : list) {
			EType eType = new EType();
			BeanUtils.copyProperties(Type, eType);
			eList.add(eType);
		}
		return eList;
	}

}
